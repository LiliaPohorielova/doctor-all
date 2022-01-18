package ua.com.alevel.web.controller.doctor;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import ua.com.alevel.facade.doctor.DoctorFacade;
import ua.com.alevel.facade.doctor.DoctorRegistrationFacade;
import ua.com.alevel.facade.patient.PatientFacade;
import ua.com.alevel.persistence.entity.doctor.Doctor;
import ua.com.alevel.persistence.entity.patient.Patient;
import ua.com.alevel.persistence.entity.user.DoctorUser;
import ua.com.alevel.util.SecurityUtil;
import ua.com.alevel.web.controller.AbstractController;
import ua.com.alevel.web.dto.response.PageData;
import ua.com.alevel.web.dto.response.doctor.DoctorResponseDto;
import ua.com.alevel.web.dto.response.patient.PatientResponseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static ua.com.alevel.util.WebUtil.DEFAULT_ORDER_PARAM_VALUE;

@Controller
@RequestMapping("/doctor")
public class DoctorController extends AbstractController {

    private HeaderName[] getColumnTitles() {
        return new HeaderName[]{
                new HeaderName("#", null, null),
                new HeaderName("lastname", "lastname", "lastname"),
                new HeaderName("firstname", "firstname", "firstname"),
                new HeaderName("gender", "gender", "gender"),
                new HeaderName("email", "patient_user_id", "patientUser"),
                new HeaderName("details", null, null),
                new HeaderName("delete", null, null)
        };
    }
    
    private final DoctorRegistrationFacade doctorUserFacade;
    private final DoctorFacade doctorFacade;
    private final PatientFacade patientFacade;

    public DoctorController(DoctorRegistrationFacade doctorUserFacade, DoctorFacade doctorFacade, PatientFacade patientFacade) {
        this.doctorUserFacade = doctorUserFacade;
        this.doctorFacade = doctorFacade;
        this.patientFacade = patientFacade;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        String user = SecurityUtil.getUsername();
        DoctorUser doctorUserData = doctorUserFacade.findByEmail(user);
        Doctor doctor = doctorUserData.getDoctor();
        model.addAttribute("doctor",doctor);
        return "pages/doctor/dashboard";
    }

    @GetMapping("/my_patients")
    public String myPatients(Model model) {

        String user = SecurityUtil.getUsername();
        DoctorUser doctorUserData = doctorUserFacade.findByEmail(user);
        Doctor doctor = doctorUserData.getDoctor();
        Set<PatientResponseDto> patients = doctorFacade.getPatients(doctor.getId());
        model.addAttribute("patients", patients);
        //TODO: Table patients WebRequest
        /*HeaderName[] columnTitles = getColumnTitles();
        PageData<PatientResponseDto> response = doctorFacade.getPatients(webRequest);
        response.initPaginationState(response.getCurrentPage());
        List<HeaderData> headerDataList = getHeaderDataList(columnTitles, response);

        model.addAttribute("headerDataList", headerDataList);
        model.addAttribute("createUrl", "/doctor/my_patients");
        model.addAttribute("pageData", response);
        model.addAttribute("cardHeader", "My Patients");*/
        return "pages/doctor/my_patients";
    }

    @GetMapping("/my_patients_table")
    public String myPatients(WebRequest webRequest, Model model) {
        String user = SecurityUtil.getUsername();
        DoctorUser doctorUserData = doctorUserFacade.findByEmail(user);
        Doctor doctor = doctorUserData.getDoctor();
        HeaderName[] columnTitles = getColumnTitles();
        PageData<PatientResponseDto> response = doctorFacade.getPatientsTable(doctor.getId(),webRequest);
        response.initPaginationState(response.getCurrentPage());
        List<HeaderData> headerDataList = getHeaderDataList(columnTitles, response);

        model.addAttribute("headerDataList", headerDataList);
        model.addAttribute("createUrl", "/doctor/my_patients_table/all_patients_table");
        model.addAttribute("pageData", response);
        model.addAttribute("cardHeader", "My Patients");
        return "pages/doctor/my_patients_table";
    }

    @PostMapping("/my_patients_table/all_patients_table")
    public ModelAndView findAllRedirect(WebRequest request, ModelMap model) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        if (MapUtils.isNotEmpty(parameterMap)) {
            parameterMap.forEach(model::addAttribute);
        }
        return new ModelAndView("redirect:/doctor/my_patients_table", model);
    }

    @GetMapping("/all_patients")
    public String redirectToAddPatientPage(Model model) {
        List<PatientResponseDto> patients = patientFacade.findAll();
        model.addAttribute("patients", patients);
        return "pages/doctor/doctor_patient_add";
    }

    @GetMapping("/add_patient/{patientId}")
    public String addPatient(@PathVariable Long patientId, Model model) {
        String user = SecurityUtil.getUsername();
        DoctorUser doctorUserData = doctorUserFacade.findByEmail(user);
        Doctor doctor = doctorUserData.getDoctor();
        doctorFacade.addPatient(doctor.getId(), patientId);
        Set<PatientResponseDto> patients = doctorFacade.getPatients(doctor.getId());
        model.addAttribute("patients", patients);
        return "pages/doctor/my_patients";
    }

    @GetMapping("/delete_patient/{patientId}")
    public String deletePatientFromDoctor(@PathVariable Long patientId, Model model) {
        String user = SecurityUtil.getUsername();
        DoctorUser doctorUserData = doctorUserFacade.findByEmail(user);
        Doctor doctor = doctorUserData.getDoctor();
        doctorFacade.removePatient(doctor.getId(), patientId);
        Set<PatientResponseDto> patients = doctorFacade.getPatients(doctor.getId());
        model.addAttribute("patients", patients);
        return "pages/doctor/my_patients";
    }

    @GetMapping("/about_patient/{patientId}")
    public String detailsByDoctorId(@PathVariable Long patientId, Model model) {
        model.addAttribute("patient", patientFacade.findById(patientId));
        return "pages/doctor/about_patient";
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
