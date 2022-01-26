package ua.com.alevel.web.controller.open;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.alevel.web.dto.request.contact.ContactRequestDto;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/open/dashboard")
public class OpenController {

    @GetMapping
    public String dashboard(HttpServletRequest request, Model model) {
        model.addAttribute("contact", new ContactRequestDto());
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
}
