package ua.com.alevel.web.controller.patient;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.alevel.persistence.entity.patient.Patient;
import ua.com.alevel.persistence.entity.user.PatientUser;
import ua.com.alevel.service.impl.PatientServiceImpl;
import ua.com.alevel.service.impl.PatientUserServiceImpl;
import ua.com.alevel.util.SecurityUtil;

import java.util.List;

@Controller
@RequestMapping("/patient/dashboard")
public class PatientController {
    
    private final PatientUserServiceImpl patientUserService;

    public PatientController(PatientUserServiceImpl patientUserService) {
        this.patientUserService = patientUserService;
    }

    //TODO: CONTROLLERS WORKS WITH FACADES (NOT SERVICES!!!

    @GetMapping
    public String dashboard(Model model) {
        String user = SecurityUtil.getUsername();
        PatientUser patientUserData = patientUserService.findByEmail(user);
        Patient patient = patientUserData.getPatient();
        model.addAttribute("patient",patient);
        return "pages/patient/dashboard";
    }
}
