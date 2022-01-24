package ua.com.alevel.facade.slot;

import ua.com.alevel.web.dto.request.slot.SlotRequestDto;

public interface SlotValidationFacade {

    String validateSlot(SlotRequestDto slotRequestDto);

    String validatePastSlot(Long slotId);
}
