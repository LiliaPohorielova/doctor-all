package ua.com.alevel.facade.slot;

import ua.com.alevel.facade.BaseFacade;
import ua.com.alevel.web.dto.request.slot.SlotRequestDto;
import ua.com.alevel.web.dto.response.slot.SlotResponseDto;

import java.util.List;

public interface SlotFacade extends BaseFacade<SlotRequestDto, SlotResponseDto> {
    List<SlotResponseDto> findAll();
}
