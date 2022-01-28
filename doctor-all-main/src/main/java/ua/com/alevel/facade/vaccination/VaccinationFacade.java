package ua.com.alevel.facade.vaccination;

import ua.com.alevel.facade.BaseFacade;
import ua.com.alevel.web.dto.request.vaccination.VaccinationRequestDto;
import ua.com.alevel.web.dto.response.vaccination.VaccinationResponseDto;

import java.util.List;

public interface VaccinationFacade extends BaseFacade<VaccinationRequestDto, VaccinationResponseDto> {

    List<VaccinationResponseDto> findAll();
}
