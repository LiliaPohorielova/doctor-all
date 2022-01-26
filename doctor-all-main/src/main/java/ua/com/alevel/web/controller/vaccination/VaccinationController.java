package ua.com.alevel.web.controller.vaccination;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.alevel.persistence.entity.vaccination.Vaccination;
import ua.com.alevel.persistence.repository.vaccination.VaccinationRepository;

import java.util.List;

@Controller
@RequestMapping("/vaccinations")
public class VaccinationController {

    private final VaccinationRepository vaccinationRepository;

    public VaccinationController(VaccinationRepository vaccinationRepository) {
        this.vaccinationRepository = vaccinationRepository;
    }

    @GetMapping("/all")
    public String redirectToAllVaccinationsPage(Model model) {
        model.addAttribute("vaccinations", vaccinationRepository.findAll());
        return "pages/vaccination/all_vaccination";
    }
}
