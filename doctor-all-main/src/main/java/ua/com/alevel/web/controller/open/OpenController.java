package ua.com.alevel.web.controller.open;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.alevel.persistence.repository.department.DepartmentRepository;
import ua.com.alevel.web.dto.request.contact.ContactRequestDto;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/open")
public class OpenController {

    private final DepartmentRepository departmentRepository;

    public OpenController(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpServletRequest request, Model model) {
        model.addAttribute("contact", new ContactRequestDto());
        model.addAttribute("departments", departmentRepository.findAll());
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
