package ua.com.alevel.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.com.alevel.entity.Vaccination;
import ua.com.alevel.facade.VaccinationFacade;
import ua.com.alevel.repository.VaccinationRepository;
import ua.com.alevel.web.dto.VaccinationResponseDto;

import java.util.List;

@RestController
@RequestMapping("/api/vaccinations")
public class VaccinationController {

    private final VaccinationFacade vaccinationFacade;

    public VaccinationController(VaccinationFacade vaccinationFacade) {
        this.vaccinationFacade = vaccinationFacade;
    }
    @GetMapping
    public List<VaccinationResponseDto> findAll() {
        return vaccinationFacade.findAll();
    }
}
