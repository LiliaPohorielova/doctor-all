package ua.com.alevel.web.controller.admin;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import ua.com.alevel.config.security.SecurityService;
import ua.com.alevel.facade.auth.AuthValidatorFacade;
import ua.com.alevel.facade.patient.PatientFacade;
import ua.com.alevel.facade.patient.PatientRegistrationFacade;
import ua.com.alevel.facade.patient.impl.PatientRegistrationFacadeImpl;
import ua.com.alevel.persistence.entity.user.PatientUser;
import ua.com.alevel.persistence.type.RoleType;
import ua.com.alevel.util.SecurityUtil;
import ua.com.alevel.web.controller.AbstractController;
import ua.com.alevel.web.dto.request.patient.PatientRequestDto;
import ua.com.alevel.web.dto.response.PageData;
import ua.com.alevel.web.dto.response.doctor.DoctorResponseDto;
import ua.com.alevel.web.dto.response.patient.PatientResponseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static ua.com.alevel.util.WebUtil.DEFAULT_ORDER_PARAM_VALUE;

@Controller
@RequestMapping("/admin/patients")
public class AdminPatientController extends AbstractController {

    private final PatientRegistrationFacadeImpl registrationFacade;
    private final AuthValidatorFacade authValidatorFacade;
    private final SecurityService securityService;
    private final PatientFacade patientFacade;
    private final PatientRegistrationFacade patientUserFacade;

    private HeaderName[] getColumnTitles() {
        return new HeaderName[]{
                new HeaderName("#", null, null),
                new HeaderName("lastname", "lastname", "lastname"),
                new HeaderName("firstname", "firstname", "firstname"),
                new HeaderName("gender", "gender", "gender"),
                new HeaderName("email", "patient_user_id", "patientUser"),
                new HeaderName("details", null, null),
                new HeaderName("edit", null, null),
                new HeaderName("delete", null, null)
        };
    }

    public AdminPatientController(
            PatientRegistrationFacadeImpl registrationFacade,
            AuthValidatorFacade authValidatorFacade,
            SecurityService securityService, PatientFacade patientFacade, PatientRegistrationFacade patientUserFacade) {
        this.securityService = securityService;
        this.registrationFacade = registrationFacade;
        this.authValidatorFacade = authValidatorFacade;
        this.patientFacade = patientFacade;
        this.patientUserFacade = patientUserFacade;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("authForm", new PatientRequestDto());
        return "/pages/admin/patients/patient_new";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("authForm") PatientRequestDto authForm, BindingResult bindingResult, Model model) {
        showMessage(model, false);
        authValidatorFacade.validate(authForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/pages/admin/patients/patient_new";
        }
        registrationFacade.registration(authForm);
        return redirectProcess(model);
    }

    private String redirectProcess(Model model) {
        showMessage(model, false);
        if (SecurityUtil.hasRole(RoleType.ROLE_ADMIN.name())) {
            return "redirect:/admin/patients";
        }
        return "redirect:/login";

    }

    @GetMapping
    public String findAll(Model model, WebRequest webRequest) {
        HeaderName[] columnTitles = getColumnTitles();
        PageData<PatientResponseDto> response = patientFacade.findAll(webRequest);
        response.initPaginationState(response.getCurrentPage());
        List<HeaderData> headerDataList = getHeaderDataList(columnTitles, response);

        model.addAttribute("headerDataList", headerDataList);
        model.addAttribute("createUrl", "/admin/patients/all");
        model.addAttribute("pageData", response);
        model.addAttribute("cardHeader", "All Patients");
        return "pages/admin/patients/patients";

    }

    @PostMapping("/all")
    public ModelAndView findAllRedirect(WebRequest request, ModelMap model) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        if (MapUtils.isNotEmpty(parameterMap)) {
            parameterMap.forEach(model::addAttribute);
        }
        return new ModelAndView("redirect:/admin/patients", model);
    }

    @GetMapping("/details/{id}")
    public String findById(@PathVariable Long id, Model model) {
        Set<DoctorResponseDto> doctors = patientFacade.getDoctors(id);
        model.addAttribute("patient", patientFacade.findById(id));
        model.addAttribute("doctors", doctors);
        return "pages/admin/patients/patient_details";
    }

    @GetMapping("/update/{id}")
    public String redirectToUpdatePatientPage(@PathVariable Long id, Model model) {
        PatientResponseDto patientResponseDto = patientFacade.findById(id);
        model.addAttribute("patient", patientResponseDto);
        return "pages/admin/patients/patient_update";
    }

    @PostMapping("/update/{id}")
    public String updatePatient(@ModelAttribute("patient") PatientRequestDto patientRequestDto, @PathVariable Long id) {
        patientFacade.update(patientRequestDto, id);
        return "redirect:/admin/patients";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        PatientUser user = patientFacade.getPatientUser(id);
        patientFacade.delete(id);
        patientUserFacade.delete(user.getId());
        return "redirect:/admin/patients";
    }

    private List<HeaderData> getHeaderDataList(HeaderName[] columnTitles, PageData<PatientResponseDto> response) {
        List<HeaderData> headerDataList = new ArrayList<>();
        for (HeaderName headerName : columnTitles) {
            HeaderData data = new HeaderData();
            data.setHeaderName(headerName.getColumnName());
            if (StringUtils.isBlank(headerName.getTableName())) {
                data.setSortable(false);
            } else {
                data.setSortable(true);
                data.setSort(headerName.getDbName());
                if (response.getSort().equals(headerName.getDbName())) {
                    data.setActive(true);
                    data.setOrder(response.getOrder());
                } else {
                    data.setActive(false);
                    data.setOrder(DEFAULT_ORDER_PARAM_VALUE);
                }
            }
            headerDataList.add(data);
        }
        return headerDataList;
    }
}
