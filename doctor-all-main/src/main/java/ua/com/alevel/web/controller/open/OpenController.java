package ua.com.alevel.web.controller.open;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.alevel.facade.doctor.DoctorFacade;
import ua.com.alevel.persistence.repository.department.DepartmentRepository;
import ua.com.alevel.persistence.repository.doctor.DoctorRepository;
import ua.com.alevel.web.dto.request.contact.ContactRequestDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/open")
public class OpenController {

    private final DepartmentRepository departmentRepository;
    private final DoctorFacade doctorFacade;

    public OpenController(DepartmentRepository departmentRepository, DoctorFacade doctorFacade) {
        this.departmentRepository = departmentRepository;
        this.doctorFacade = doctorFacade;
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpServletRequest request, Model model, HttpSession httpSession) {
        if (httpSession.isNew()) {
            System.out.println("OpenController: New httpSession");
        }
        model.addAttribute("contact", new ContactRequestDto());
        model.addAttribute("doctors", doctorFacade.findFirst());
        if (request.isUserInRole("ROLE_ADMIN")) {
            return "redirect:/admin/dashboard";
        }
        if (request.isUserInRole("ROLE_PATIENT")) {
            return "redirect:/patient/dashboard";
        }
        if (request.isUserInRole("ROLE_DOCTOR")) {
            return "redirect:/doctor/dashboard";
        }
        return "pages/open/dashboard";
    }

    @GetMapping("/about")
    public String about() {
        return "pages/open/about";
    }
}
