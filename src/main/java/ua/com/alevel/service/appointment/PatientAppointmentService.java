package ua.com.alevel.service.appointment;

import ua.com.alevel.persistence.entity.appointment.PatientAppointment;
import ua.com.alevel.persistence.entity.slot.Slot;
import ua.com.alevel.service.BaseCrudService;

import java.util.List;

public interface PatientAppointmentService extends BaseCrudService<PatientAppointment> {

    List<Slot> findBookedByPatient(Long id);

    List<Slot> findPastByPatient(Long id);

    PatientAppointment findBySlotId(Long id);
}
