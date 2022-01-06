package ua.com.alevel.web.controller.patient;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/patient/dashboard")
public class PatientController {

    @GetMapping
    public String dashboard() {
        return "pages/patient/dashboard";
    }
}
