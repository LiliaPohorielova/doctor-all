package ua.com.alevel.web.controller.auth.doctor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ua.com.alevel.config.security.SecurityService;
import ua.com.alevel.facade.auth.AuthValidatorFacade;
import ua.com.alevel.facade.department.DepartmentFacade;
import ua.com.alevel.facade.doctor.impl.DoctorRegistrationFacadeImpl;
import ua.com.alevel.persistence.type.RoleType;
import ua.com.alevel.util.SecurityUtil;
import ua.com.alevel.web.controller.AbstractController;
import ua.com.alevel.web.dto.request.doctor.DoctorRequestDto;

@Controller
public class DoctorRegistrationController extends AbstractController {
    
    private final DoctorRegistrationFacadeImpl registrationFacade;
    private final AuthValidatorFacade authValidatorFacade;
    private final SecurityService securityService;
    private final DepartmentFacade departmentFacade;

    public DoctorRegistrationController(
            DoctorRegistrationFacadeImpl registrationFacade,
            AuthValidatorFacade authValidatorFacade,
            SecurityService securityService,
            DepartmentFacade departmentFacade) {
        this.securityService = securityService;
        this.registrationFacade = registrationFacade;
        this.authValidatorFacade = authValidatorFacade;
        this.departmentFacade = departmentFacade;
    }
    @GetMapping("/doctor/registration")
    public String registration(Model model) {
        if (securityService.isAuthenticated()) {
            return redirectProcess(model);
        }
        model.addAttribute("authForm", new DoctorRequestDto());
        model.addAttribute("departments", departmentFacade.findAll());
        return "/pages/doctor/registration";
    }

    @PostMapping("/doctor/registration")
    public String registration(@ModelAttribute("authForm") DoctorRequestDto authForm, BindingResult bindingResult, Model model) {
        showMessage(model, false);
        authValidatorFacade.validate(authForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/pages/doctor/registration";
        }
        registrationFacade.registration(authForm);
        securityService.autoLogin(authForm.getEmail(), authForm.getPasswordConfirm());
        return redirectProcess(model);
    }

    private String redirectProcess(Model model) {
        showMessage(model, false);
        if (SecurityUtil.hasRole(RoleType.ROLE_DOCTOR.name())) {
            return "redirect:/doctor/dashboard";
        }
        return "redirect:/login";
    }
}
