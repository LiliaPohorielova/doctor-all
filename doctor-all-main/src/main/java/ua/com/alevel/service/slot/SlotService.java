package ua.com.alevel.service.slot;

import ua.com.alevel.persistence.entity.appointment.PatientAppointment;
import ua.com.alevel.persistence.entity.doctor.Doctor;
import ua.com.alevel.persistence.entity.patient.Patient;
import ua.com.alevel.persistence.entity.slot.Slot;
import ua.com.alevel.persistence.type.SlotStatus;
import ua.com.alevel.service.BaseCrudService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface SlotService extends BaseCrudService<Slot> {

    List<Slot> findAll();

    Slot getSlot(Doctor doctor, LocalDate date, LocalTime time);

    PatientAppointment bookSlot(Long slotId, Long patientId);

    List<Slot> findSlotByDoctor(SlotStatus slotStatus, Long doctorId);
}
