package ua.com.alevel.facade.slot.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.facade.slot.SlotValidationFacade;
import ua.com.alevel.persistence.repository.doctor.SlotRepository;
import ua.com.alevel.web.dto.request.slot.SlotRequestDto;

@Service
public class SlotValidationFacadeImpl implements SlotValidationFacade {

    private final SlotRepository slotRepository;

    public SlotValidationFacadeImpl(SlotRepository slotRepository) {
        this.slotRepository = slotRepository;
    }

    public String validateSlot(SlotRequestDto slotRequestDto) {
        String message = "";
        if (slotRepository.findByDoctorAndAppDateAndStartTime(slotRequestDto.getDoctor(), slotRequestDto.getAppDate(), slotRequestDto.getStartTime()) != null)
            message = "Such slot is already exist!!! Choose another time or date!";
        return message;
    }
}
