package ua.com.alevel.web.controller.auth;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.com.alevel.config.security.SecurityService;
import ua.com.alevel.persistence.type.RoleType;
import ua.com.alevel.util.SecurityUtil;
import ua.com.alevel.web.controller.AbstractController;

@Controller
public class AuthController extends AbstractController {

    private final SecurityService securityService;

    public AuthController(
            SecurityService securityService) {
        this.securityService = securityService;
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        showMessage(model, false);
        boolean authenticated = securityService.isAuthenticated();
        if (authenticated) {
            if (SecurityUtil.hasRole(RoleType.ROLE_ADMIN.name())) {
                return "redirect:/admin/dashboard";
            }
            if (SecurityUtil.hasRole(RoleType.ROLE_PATIENT.name())) {
                return "redirect:/patient/dashboard";
            }
            if (SecurityUtil.hasRole(RoleType.ROLE_DOCTOR.name())) {
                return "redirect:/doctor/dashboard";
            }
        }
        if (error != null) {
            showError(model, "Your email and password is invalid.");
        }
        if (logout != null) {
            showInfo(model, "You have been logged out successfully.");
        }
        return "login";
    }

    public SecurityService getSecurityService() {
        return securityService;
    }
}

