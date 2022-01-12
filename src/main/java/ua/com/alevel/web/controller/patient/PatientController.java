package ua.com.alevel.web.controller.patient;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.alevel.facade.patient.PatientRegistrationFacade;
import ua.com.alevel.persistence.entity.patient.Patient;
import ua.com.alevel.persistence.entity.user.PatientUser;
import ua.com.alevel.service.patient.PatientUserService;
import ua.com.alevel.util.SecurityUtil;

@Controller
@RequestMapping("/patient/dashboard")
public class PatientController {
    
    private final PatientRegistrationFacade patientUserFacade;

    public PatientController(PatientRegistrationFacade patientUserFacade) {
        this.patientUserFacade = patientUserFacade;
    }

    @GetMapping
    public String dashboard(Model model) {
        String user = SecurityUtil.getUsername();
        PatientUser patientUserData = patientUserFacade.findByEmail(user);
        Patient patient = patientUserData.getPatient();
        model.addAttribute("patient",patient);
        return "pages/patient/dashboard";
    }
}
