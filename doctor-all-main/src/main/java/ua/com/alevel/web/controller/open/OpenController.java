package ua.com.alevel.web.controller.open;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.alevel.facade.department.DepartmentFacade;
import ua.com.alevel.facade.doctor.DoctorFacade;
import ua.com.alevel.persistence.repository.department.DepartmentRepository;
import ua.com.alevel.web.dto.request.contact.ContactRequestDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/open")
public class OpenController {

    private final DepartmentFacade departmentFacade;
    private final DoctorFacade doctorFacade;

    public OpenController(DepartmentFacade departmentFacade, DoctorFacade doctorFacade) {
        this.departmentFacade = departmentFacade;
        this.doctorFacade = doctorFacade;
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpServletRequest request, Model model, HttpSession httpSession) {
        if (httpSession.isNew()) {
            System.out.println("OpenController: New httpSession");
        }
        model.addAttribute("contact", new ContactRequestDto());
        model.addAttribute("doctors", doctorFacade.findFirst());
        model.addAttribute("departments", departmentFacade.findAll());
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

    @GetMapping("/doctors")
    public String redirectToAllDepartmentsPage(Model model) {
        model.addAttribute("doctors", doctorFacade.findFirst());
        return "pages/open/first_doctors";
    }
}
