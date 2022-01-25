package ua.com.alevel.web.controller.vaccination;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vaccinations")
public class VaccinationController {

    @GetMapping("/all")
    public String redirectToAllVaccinationsPage(Model model) {
        return "pages/vaccination/all_vaccination";
    }
}
