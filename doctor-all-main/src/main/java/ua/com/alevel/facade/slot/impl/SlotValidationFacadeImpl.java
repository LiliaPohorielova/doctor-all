package ua.com.alevel.facade.slot.impl;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import ua.com.alevel.facade.slot.SlotValidationFacade;
import ua.com.alevel.persistence.entity.slot.Slot;
import ua.com.alevel.persistence.repository.doctor.SlotRepository;
import ua.com.alevel.persistence.type.SlotStatus;
import ua.com.alevel.web.dto.request.slot.SlotRequestDto;

import java.time.LocalDate;

@Service
public class SlotValidationFacadeImpl implements SlotValidationFacade {

    private final SlotRepository slotRepository;

    public SlotValidationFacadeImpl(SlotRepository slotRepository) {
        this.slotRepository = slotRepository;
    }

    public String validateSlot(SlotRequestDto slotRequestDto) {
        String message = "";
        try {
            Slot slot = slotRepository.findByDoctorAndAppDateAndStartTime(slotRequestDto.getDoctor(), slotRequestDto.getAppDate(), slotRequestDto.getStartTime());
            if (slot != null && slot.getStatus() != SlotStatus.CANCELLED)
                message = "Such slot is already exist!!! Choose another time or date!";
        } catch (IncorrectResultSizeDataAccessException e) {
            message = "Such slot is already exist!!! Choose another time or date!";
        }
        return message;
    }

    @Override
    public String validatePastSlot(Long slotId) {
        String message = "";
        Slot slot = slotRepository.findById(slotId).get();
        if (slot.getAppDate().isAfter(LocalDate.now()))
            message = "You can't mark this visit as past. The past hasn't arrived yet :)";
        return message;
    }
}
