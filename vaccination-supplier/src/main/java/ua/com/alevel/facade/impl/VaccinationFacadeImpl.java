package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.entity.Vaccination;
import ua.com.alevel.facade.VaccinationFacade;
import ua.com.alevel.service.VaccinationService;
import ua.com.alevel.web.dto.VaccinationRequestDto;
import ua.com.alevel.web.dto.VaccinationResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VaccinationFacadeImpl implements VaccinationFacade {

    private final VaccinationService vaccinationService;

    public VaccinationFacadeImpl(VaccinationService vaccinationService) {
        this.vaccinationService = vaccinationService;
    }

    @Override
    public void create(VaccinationRequestDto vaccinationRequestDto) {
        Vaccination vaccination = new Vaccination();
        vaccination.setName(vaccinationRequestDto.getName());
        vaccination.setImageUrl(vaccinationRequestDto.getImageUrl());
        vaccination.setManufacturer(vaccinationRequestDto.getManufacturer());
        vaccination.setMethodOfAdministration(vaccinationRequestDto.getMethodOfAdministration());
        vaccination.setQuantity(vaccinationRequestDto.getQuantity());
        vaccinationService.create(vaccination);
    }

    @Override
    public void update(VaccinationRequestDto vaccinationRequestDto, Long id) {
        Vaccination vaccination = vaccinationService.findById(id).get();
        vaccination.setName(vaccinationRequestDto.getName());
        vaccination.setImageUrl(vaccinationRequestDto.getImageUrl());
        vaccination.setManufacturer(vaccinationRequestDto.getManufacturer());
        vaccination.setMethodOfAdministration(vaccinationRequestDto.getMethodOfAdministration());
        vaccination.setQuantity(vaccinationRequestDto.getQuantity());
        vaccinationService.update(vaccination);
    }

    @Override
    public void delete(Long id) {
        vaccinationService.delete(id);
    }

    @Override
    public VaccinationResponseDto findById(Long id) {
        Vaccination vaccination = vaccinationService.findById(id).get();
        return new VaccinationResponseDto(vaccination);
    }

    @Override
    public List<VaccinationResponseDto> findAll() {
        List<Vaccination> all = vaccinationService.findAll();
        List<VaccinationResponseDto> items = all.stream().map(VaccinationResponseDto::new).collect(Collectors.toList());
        return items;
    }
}
