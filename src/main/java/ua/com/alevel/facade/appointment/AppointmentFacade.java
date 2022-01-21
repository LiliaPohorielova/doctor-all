package ua.com.alevel.facade.appointment;

import ua.com.alevel.facade.BaseFacade;
import ua.com.alevel.persistence.entity.slot.Slot;
import ua.com.alevel.persistence.type.SlotStatus;
import ua.com.alevel.web.dto.request.appointment.AppointmentRequestDto;
import ua.com.alevel.web.dto.response.appointment.AppointmentResponseDto;

import java.util.List;

public interface AppointmentFacade extends BaseFacade<AppointmentRequestDto, AppointmentResponseDto> {

    List<Slot> findBookedByPatient(Long id, SlotStatus slotStatus);
}