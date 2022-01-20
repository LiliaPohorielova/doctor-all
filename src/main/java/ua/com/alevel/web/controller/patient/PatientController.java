package ua.com.alevel.web.controller.patient;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.doctor.DoctorFacade;
import ua.com.alevel.facade.patient.PatientFacade;
import ua.com.alevel.facade.patient.PatientRegistrationFacade;
import ua.com.alevel.facade.slot.SlotFacade;
import ua.com.alevel.persistence.entity.appointment.PatientAppointment;
import ua.com.alevel.persistence.entity.patient.Patient;
import ua.com.alevel.persistence.entity.slot.Slot;
import ua.com.alevel.persistence.entity.user.PatientUser;
import ua.com.alevel.service.slot.SlotService;
import ua.com.alevel.util.SecurityUtil;
import ua.com.alevel.web.dto.request.appointment.AppointmentRequestDto;
import ua.com.alevel.web.dto.response.doctor.DoctorResponseDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/patient")
public class PatientController {
    
    private final PatientRegistrationFacade patientUserFacade;
    private final DoctorFacade doctorFacade;
    private final PatientFacade patientFacade;

    public PatientController(PatientRegistrationFacade patientUserFacade, DoctorFacade doctorFacade, PatientFacade patientFacade) {
        this.patientUserFacade = patientUserFacade;
        this.doctorFacade = doctorFacade;
        this.patientFacade = patientFacade;
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
        return "pages/patient/my_doctors";
    }

    @GetMapping("/all_doctors")
    public String redirectToAddDoctorPage(Model model) {
        List<DoctorResponseDto> doctors = doctorFacade.findAll();
        model.addAttribute("doctors", doctors);
        return "pages/patient/patient_doctor_add";
    }

    @GetMapping("/add_doctor/{doctorId}")
    public String addDoctor(@PathVariable Long doctorId, Model model) {
        String user = SecurityUtil.getUsername();
        PatientUser patientUserData = patientUserFacade.findByEmail(user);
        Patient patient = patientUserData.getPatient();
        doctorFacade.addPatient(doctorId, patient.getId());
        Set<DoctorResponseDto> doctors = patientFacade.getDoctors(patient.getId());
        model.addAttribute("doctors", doctors);
        return "pages/patient/my_doctors";
    }

    @GetMapping("/delete_doctor/{doctorId}")
    public String deleteDoctorFromPatient(@PathVariable Long doctorId, Model model) {
        String user = SecurityUtil.getUsername();
        PatientUser patientUserData = patientUserFacade.findByEmail(user);
        Patient patient = patientUserData.getPatient();
        doctorFacade.removePatient(doctorId, patient.getId());
        Set<DoctorResponseDto> doctors = patientFacade.getDoctors(patient.getId());
        model.addAttribute("doctors", doctors);
        return "pages/patient/my_doctors";
    }

    @GetMapping("/about_doctor/{doctorId}")
    public String detailsByDoctorId(@PathVariable Long doctorId, Model model) {
        model.addAttribute("doctor", doctorFacade.findById(doctorId));
        return "pages/patient/about_doctor";
    }
}
