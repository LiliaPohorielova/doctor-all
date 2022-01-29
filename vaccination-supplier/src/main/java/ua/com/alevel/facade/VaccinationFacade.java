package ua.com.alevel.facade;

import ua.com.alevel.web.dto.VaccinationRequestDto;
import ua.com.alevel.web.dto.VaccinationResponseDto;

import java.util.List;

public interface VaccinationFacade {

    void create(VaccinationRequestDto vaccinationRequestDto);
    void update(VaccinationRequestDto vaccinationRequestDto, Long id);
    void delete(Long id);
    VaccinationResponseDto findById(Long id);
    List<VaccinationResponseDto> findAll();
}
