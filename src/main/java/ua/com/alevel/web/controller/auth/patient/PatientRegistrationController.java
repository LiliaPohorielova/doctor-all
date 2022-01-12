package ua.com.alevel.web.controller.auth.patient;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ua.com.alevel.config.security.SecurityService;
import ua.com.alevel.facade.auth.AuthValidatorFacade;
import ua.com.alevel.facade.patient.impl.PatientRegistrationFacadeImpl;
import ua.com.alevel.persistence.type.RoleType;
import ua.com.alevel.util.SecurityUtil;
import ua.com.alevel.web.controller.AbstractController;
import ua.com.alevel.web.dto.request.patient.PatientRequestDto;

@Controller
public class PatientRegistrationController extends AbstractController {
    
    private final PatientRegistrationFacadeImpl registrationFacade;
    private final AuthValidatorFacade authValidatorFacade;
    private final SecurityService securityService;

    public PatientRegistrationController(
            PatientRegistrationFacadeImpl registrationFacade,
            AuthValidatorFacade authValidatorFacade,
            SecurityService securityService) {
        this.securityService = securityService;
        this.registrationFacade = registrationFacade;
        this.authValidatorFacade = authValidatorFacade;
    }
    @GetMapping("/patient/registration")
    public String registration(Model model) {
        if (securityService.isAuthenticated()) {
            return redirectProcess(model);
        }
        model.addAttribute("authForm", new PatientRequestDto());
        return "/pages/patient/registration";
    }

    @PostMapping("/patient/registration")
    public String registration(@ModelAttribute("authForm") PatientRequestDto authForm, BindingResult bindingResult, Model model) {
        showMessage(model, false);
        authValidatorFacade.validate(authForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/pages/patient/registration";
        }
        registrationFacade.registration(authForm);
        securityService.autoLogin(authForm.getEmail(), authForm.getPasswordConfirm());
        return redirectProcess(model);
    }

    private String redirectProcess(Model model) {
        showMessage(model, false);
        if (SecurityUtil.hasRole(RoleType.ROLE_PATIENT.name())) {
            return "redirect:/patient/dashboard";
        }
        return "redirect:/login";

    }
}
