package ua.com.alevel.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.com.alevel.entity.Vaccination;
import ua.com.alevel.repository.VaccinationRepository;

import java.util.List;

@RestController
@RequestMapping("/api/vaccinations")
public class VaccinationController {

    private final VaccinationRepository vaccinationRepository;

    public VaccinationController(VaccinationRepository vaccinationRepository) {
        this.vaccinationRepository = vaccinationRepository;
    }
    @GetMapping
    public List<Vaccination> findAll() {
        return (List<Vaccination>) vaccinationRepository.findAll();
    }
}
