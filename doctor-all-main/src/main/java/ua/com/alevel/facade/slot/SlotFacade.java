package ua.com.alevel.facade.slot;

import ua.com.alevel.facade.BaseFacade;
import ua.com.alevel.persistence.entity.appointment.PatientAppointment;
import ua.com.alevel.persistence.entity.doctor.Doctor;
import ua.com.alevel.persistence.entity.patient.Patient;
import ua.com.alevel.persistence.entity.slot.Slot;
import ua.com.alevel.persistence.type.SlotStatus;
import ua.com.alevel.web.dto.request.slot.SlotRequestDto;
import ua.com.alevel.web.dto.response.slot.SlotResponseDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

public interface SlotFacade extends BaseFacade<SlotRequestDto, SlotResponseDto> {

    void updateStatus(Long slotId, SlotStatus slotStatus);

    List<SlotResponseDto> findAll();

    Set<String> getDatesByDoctor(String doctorId);

    List<String> getTimeByDate(String doctorId, String date);

    Slot getSlot(Doctor doctor, LocalDate date, LocalTime time);

    PatientAppointment bookSlot(Long slotId, Long patientId);

    List<Slot> findSlotByDoctor(SlotStatus slotStatus, Long doctorId);
}
