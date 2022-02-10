package ua.com.alevel.web.controller.patient;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import ua.com.alevel.facade.doctor.DoctorFacade;
import ua.com.alevel.facade.patient.PatientFacade;
import ua.com.alevel.facade.patient.PatientRegistrationFacade;
import ua.com.alevel.facade.vaccination.VaccinationFacade;
import ua.com.alevel.persistence.entity.doctor.Doctor;
import ua.com.alevel.persistence.entity.patient.Patient;
import ua.com.alevel.persistence.entity.user.DoctorUser;
import ua.com.alevel.persistence.entity.user.PatientUser;
import ua.com.alevel.util.SecurityUtil;
import ua.com.alevel.web.controller.AbstractController;
import ua.com.alevel.web.dto.request.doctor.DoctorRequestDto;
import ua.com.alevel.web.dto.request.patient.PatientRequestDto;
import ua.com.alevel.web.dto.response.PageData;
import ua.com.alevel.web.dto.response.doctor.DoctorResponseDto;
import ua.com.alevel.web.dto.response.patient.PatientResponseDto;
import ua.com.alevel.web.dto.response.vaccination.VaccinationResponseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static ua.com.alevel.util.WebUtil.DEFAULT_ORDER_PARAM_VALUE;

@Controller
@RequestMapping("/patient")
public class PatientController extends AbstractController {

    private AbstractController.HeaderName[] getColumnTitles() {
        return new AbstractController.HeaderName[]{
                new AbstractController.HeaderName("#", null, null),
                new AbstractController.HeaderName("lastname", "lastname", "lastname"),
                new AbstractController.HeaderName("firstname", "firstname", "firstname"),
                new AbstractController.HeaderName("middle name", "middle_name", "middleName"),
                new AbstractController.HeaderName("specialization", "specialization", "specialization"),
                new AbstractController.HeaderName("email", "doctor_user_id", "doctorUser"),
                new AbstractController.HeaderName("details", null, null),
                new AbstractController.HeaderName("delete", null, null)
        };
    }
    
    private final PatientRegistrationFacade patientUserFacade;
    private final DoctorFacade doctorFacade;
    private final PatientFacade patientFacade;
    private final VaccinationFacade vaccinationFacade;

    public PatientController(PatientRegistrationFacade patientUserFacade, DoctorFacade doctorFacade, PatientFacade patientFacade, VaccinationFacade vaccinationFacade) {
        this.patientUserFacade = patientUserFacade;
        this.doctorFacade = doctorFacade;
        this.patientFacade = patientFacade;
        this.vaccinationFacade = vaccinationFacade;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        String user = SecurityUtil.getUsername();
        PatientUser patientUserData = patientUserFacade.findByEmail(user);
        Patient patient = patientUserData.getPatient();
        model.addAttribute("patient",patient);
        return "pages/patient/dashboard";
    }

    @GetMapping("/my_doctors")
    public String myPatients(WebRequest webRequest, Model model) {
        String user = SecurityUtil.getUsername();
        PatientUser patientUserData = patientUserFacade.findByEmail(user);
        Patient patient = patientUserData.getPatient();
        Set<DoctorResponseDto> doctors = patientFacade.getDoctors(patient.getId());
        model.addAttribute("doctors", doctors);
        return "pages/patient/my_doctors_table";
    }

    @GetMapping("/all_doctors")
    public String redirectToAddDoctorPage(Model model) {
        List<DoctorResponseDto> doctors = doctorFacade.findAll();
        model.addAttribute("doctors", doctors);
        return "pages/patient/patient_doctor_add";
    }

    @GetMapping("/my_doctors_table")
    public String myDoctors(WebRequest webRequest, Model model) {
        String user = SecurityUtil.getUsername();
        PatientUser patientUserData = patientUserFacade.findByEmail(user);
        Patient patient = patientUserData.getPatient();
        AbstractController.HeaderName[] columnTitles = getColumnTitles();
        PageData<DoctorResponseDto> response = patientFacade.getDoctorsTable(patient.getId(),webRequest);
        response.initPaginationState(response.getCurrentPage());
        List<AbstractController.HeaderData> headerDataList = getHeaderDataList(columnTitles, response);

        model.addAttribute("headerDataList", headerDataList);
        model.addAttribute("createUrl", "/patient/my_doctors_table/all_doctors_table");
        model.addAttribute("pageData", response);
        model.addAttribute("cardHeader", "My Doctors");
        return "pages/patient/my_doctors_table";
    }

    @PostMapping("/my_doctors_table/all_doctors_table")
    public ModelAndView findAllRedirect(WebRequest request, ModelMap model) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        if (MapUtils.isNotEmpty(parameterMap)) {
            parameterMap.forEach(model::addAttribute);
        }
        return new ModelAndView("redirect:/patient/my_doctors_table", model);
    }

    @GetMapping("/add_doctor/{doctorId}")
    public String addDoctor(@PathVariable Long doctorId, Model model) {
        String user = SecurityUtil.getUsername();
        PatientUser patientUserData = patientUserFacade.findByEmail(user);
        Patient patient = patientUserData.getPatient();
        doctorFacade.addPatient(doctorId, patient.getId());
        return "redirect:/patient/my_doctors_table";
    }

    @GetMapping("/my_vaccinations")
    public String myVaccinations(WebRequest webRequest, Model model) {
        String user = SecurityUtil.getUsername();
        PatientUser patientUserData = patientUserFacade.findByEmail(user);
        Patient patient = patientUserData.getPatient();
        Set<VaccinationResponseDto> vaccinations = patientFacade.getVaccinations(patient.getId());
        model.addAttribute("vaccinations", vaccinations);
        return "pages/patient/my_vaccinations";
    }

    @GetMapping("/all_vaccinations")
    public String redirectToAddVaccinationPage(Model model) {
        List<VaccinationResponseDto> vaccinations = vaccinationFacade.findAll();
        model.addAttribute("vaccinations", vaccinations);
        return "pages/patient/patient_vaccination_add";
    }

    @GetMapping("/add_vaccination/{vaccinationId}")
    public String addVaccination(@PathVariable Long vaccinationId, Model model) {
        String user = SecurityUtil.getUsername();
        PatientUser patientUserData = patientUserFacade.findByEmail(user);
        Patient patient = patientUserData.getPatient();
        patientFacade.addVaccination(vaccinationId, patient.getId());
        Set<VaccinationResponseDto> vaccinations = patientFacade.getVaccinations(patient.getId());
        model.addAttribute("vaccinations", vaccinations);
        return "pages/patient/my_vaccinations";
    }

    @GetMapping("/delete_doctor/{doctorId}")
    public String deleteDoctorFromPatient(@PathVariable Long doctorId, Model model) {
        String user = SecurityUtil.getUsername();
        PatientUser patientUserData = patientUserFacade.findByEmail(user);
        Patient patient = patientUserData.getPatient();
        doctorFacade.removePatient(doctorId, patient.getId());
        return "redirect:/patient/my_doctors_table";
    }

    @GetMapping("/about_doctor/{doctorId}")
    public String detailsByDoctorId(@PathVariable Long doctorId, Model model) {
        model.addAttribute("doctor", doctorFacade.findById(doctorId));
        return "pages/patient/about_doctor";
    }

    @GetMapping("/edit_profile")
    public String redirectToUpdateDoctorPage(Model model) {
        String user = SecurityUtil.getUsername();
        PatientUser patientUserData = patientUserFacade.findByEmail(user);
        Patient patient = patientUserData.getPatient();
        PatientResponseDto patientResponseDto = patientFacade.findById(patient.getId());
        model.addAttribute("patient",patient);
        model.addAttribute("patientForm", patientResponseDto);
        return "pages/patient/edit_profile";
    }

    @PostMapping("/edit_profile")
    public String updateDoctor(@ModelAttribute("doctorForm") PatientRequestDto patientRequestDto) {
        String user = SecurityUtil.getUsername();
        PatientUser patientUserData = patientUserFacade.findByEmail(user);
        Patient patient = patientUserData.getPatient();
        patientFacade.update(patientRequestDto, patient.getId());
        return "redirect:/patient/dashboard";
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
