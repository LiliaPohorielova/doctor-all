package ua.com.alevel.web.controller.doctor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.alevel.facade.doctor.DoctorRegistrationFacade;
import ua.com.alevel.persistence.entity.doctor.Doctor;
import ua.com.alevel.persistence.entity.user.DoctorUser;
import ua.com.alevel.util.SecurityUtil;

@Controller
@RequestMapping("/doctor/dashboard")
public class DoctorController {
    
    private final DoctorRegistrationFacade doctorUserFacade;

    public DoctorController(DoctorRegistrationFacade doctorUserFacade) {
        this.doctorUserFacade = doctorUserFacade;
    }

    @GetMapping
    public String dashboard(Model model) {
        String user = SecurityUtil.getUsername();
        DoctorUser doctorUserData = doctorUserFacade.findByEmail(user);
        Doctor doctor = doctorUserData.getDoctor();
        model.addAttribute("doctor",doctor);
        return "pages/doctor/dashboard";
    }
}
