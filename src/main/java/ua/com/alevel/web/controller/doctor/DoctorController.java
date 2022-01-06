package ua.com.alevel.web.controller.doctor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/doctor/dashboard")
public class DoctorController {

    @GetMapping
    public String dashboard() {
        return "pages/doctor/dashboard";
    }
}
