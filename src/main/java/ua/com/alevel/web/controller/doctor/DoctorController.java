package ua.com.alevel.web.controller.doctor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.doctor.DoctorFacade;
import ua.com.alevel.facade.doctor.DoctorRegistrationFacade;
import ua.com.alevel.facade.patient.PatientFacade;
import ua.com.alevel.persistence.entity.doctor.Doctor;
import ua.com.alevel.persistence.entity.patient.Patient;
import ua.com.alevel.persistence.entity.user.DoctorUser;
import ua.com.alevel.util.SecurityUtil;
import ua.com.alevel.web.controller.AbstractController;
import ua.com.alevel.web.dto.response.doctor.DoctorResponseDto;
import ua.com.alevel.web.dto.response.patient.PatientResponseDto;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/doctor")
public class DoctorController extends AbstractController {
    
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
    public String myPatients(WebRequest webRequest, Model model) {

        String user = SecurityUtil.getUsername();
        DoctorUser doctorUserData = doctorUserFacade.findByEmail(user);
        Doctor doctor = doctorUserData.getDoctor();
        Set<PatientResponseDto> patients = doctorFacade.getPatients(doctor.getId());
        model.addAttribute("patients", patients);
        //TODO: Table patients WebRequest
        /*HeaderName[] columnTitles = getPatientsTitles();
        PageData<PatientResponseDto> response = doctorFacade.getPatients(webRequest);
        response.initPaginationState(response.getCurrentPage());
        List<HeaderData> headerDataList = getHeaderDataList(columnTitles, response);*/

        /*model.addAttribute("headerDataList", headerDataList);
        model.addAttribute("createUrl", "/doctor/my_patients");
        model.addAttribute("pageData", response);
        model.addAttribute("cardHeader", "My Patients");*/
        return "pages/doctor/my_patients";
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
}
