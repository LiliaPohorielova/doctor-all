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
import ua.com.alevel.facade.doctor.DoctorFacade;
import ua.com.alevel.facade.doctor.DoctorRegistrationFacade;
import ua.com.alevel.facade.doctor.impl.DoctorRegistrationFacadeImpl;
import ua.com.alevel.persistence.entity.user.DoctorUser;
import ua.com.alevel.persistence.type.RoleType;
import ua.com.alevel.util.SecurityUtil;
import ua.com.alevel.web.controller.AbstractController;
import ua.com.alevel.web.dto.request.doctor.DoctorRequestDto;
import ua.com.alevel.web.dto.response.PageData;
import ua.com.alevel.web.dto.response.doctor.DoctorResponseDto;
import ua.com.alevel.web.dto.response.patient.PatientResponseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static ua.com.alevel.util.WebUtil.DEFAULT_ORDER_PARAM_VALUE;

@Controller
@RequestMapping("/admin/doctors")
public class AdminDoctorController extends AbstractController {

    private final DoctorRegistrationFacadeImpl registrationFacade;
    private final AuthValidatorFacade authValidatorFacade;
    private final SecurityService securityService;
    private final DoctorFacade doctorFacade;
    private final DoctorRegistrationFacade doctorUserFacade;

    private HeaderName[] getColumnTitles() {
        return new HeaderName[]{
                new HeaderName("#", null, null),
                new HeaderName("lastname", "lastname", "lastname"),
                new HeaderName("firstname", "firstname", "firstname"),
                new HeaderName("middle name", "middleName", "middleName"),
                new HeaderName("email", "doctor_user_id", "doctorUser"),
                new HeaderName("details", null, null),
                new HeaderName("edit", null, null),
                new HeaderName("delete", null, null)
        };
    }

    public AdminDoctorController(
            DoctorRegistrationFacadeImpl registrationFacade,
            AuthValidatorFacade authValidatorFacade,
            SecurityService securityService, DoctorFacade doctorFacade, DoctorRegistrationFacade doctorUserFacade) {
        this.securityService = securityService;
        this.registrationFacade = registrationFacade;
        this.authValidatorFacade = authValidatorFacade;
        this.doctorFacade = doctorFacade;
        this.doctorUserFacade = doctorUserFacade;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("authForm", new DoctorRequestDto());
        return "/pages/admin/doctors/doctor_new";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("authForm") DoctorRequestDto authForm, BindingResult bindingResult, Model model) {
        showMessage(model, false);
        authValidatorFacade.validate(authForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/pages/admin/doctors/doctor_new";
        }
        registrationFacade.registration(authForm);
        return redirectProcess(model);
    }

    private String redirectProcess(Model model) {
        showMessage(model, false);
        if (SecurityUtil.hasRole(RoleType.ROLE_ADMIN.name())) {
            return "redirect:/admin/doctors";
        }
        return "redirect:/login";

    }

    @GetMapping
    public String findAll(Model model, WebRequest webRequest) {
        HeaderName[] columnTitles = getColumnTitles();
        PageData<DoctorResponseDto> response = doctorFacade.findAll(webRequest);
        response.initPaginationState(response.getCurrentPage());
        List<HeaderData> headerDataList = getHeaderDataList(columnTitles, response);

        model.addAttribute("headerDataList", headerDataList);
        model.addAttribute("createUrl", "/admin/doctors/all");
        model.addAttribute("pageData", response);
        model.addAttribute("cardHeader", "All Doctors");
        return "pages/admin/doctors/doctors";

    }

    @PostMapping("/all")
    public ModelAndView findAllRedirect(WebRequest request, ModelMap model) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        if (MapUtils.isNotEmpty(parameterMap)) {
            parameterMap.forEach(model::addAttribute);
        }
        return new ModelAndView("redirect:/admin/doctors", model);
    }

    @GetMapping("/details/{id}")
    public String detailsByDoctorId(@PathVariable Long id, Model model) {
        Set<PatientResponseDto> patients = doctorFacade.getPatients(id);
        model.addAttribute("doctor", doctorFacade.findById(id));
        model.addAttribute("patients", patients);
        return "pages/admin/doctors/doctor_details";
    }

    @GetMapping("/update/{id}")
    public String redirectToUpdateDoctorPage(@PathVariable Long id, Model model) {
        DoctorResponseDto doctorResponseDto = doctorFacade.findById(id);
        model.addAttribute("doctor", doctorResponseDto);
        return "pages/admin/doctors/doctor_update";
    }

    @PostMapping("/update/{id}")
    public String updateDoctor(@ModelAttribute("doctor") DoctorRequestDto doctorRequestDto, @PathVariable Long id) {
        doctorFacade.update(doctorRequestDto, id);
        return "redirect:/admin/doctors";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        DoctorUser user = doctorFacade.getDoctorUser(id);
        /*for (PatientResponseDto patient: users) {
            doctorFacade.removePatient(id, patient.getId());
        }*/
        doctorFacade.delete(id);
        doctorUserFacade.delete(user.getId());
        return "redirect:/admin/doctors";
    }


    private List<HeaderData> getHeaderDataList(HeaderName[] columnTitles, PageData<DoctorResponseDto> response) {
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
