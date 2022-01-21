package ua.com.alevel.persistence.repository.patient;

import org.springframework.stereotype.Repository;
import ua.com.alevel.persistence.entity.appointment.PatientAppointment;
import ua.com.alevel.persistence.repository.BaseRepository;
import ua.com.alevel.persistence.type.SlotStatus;

import java.util.List;

@Repository
public interface PatientAppointmentRepository extends BaseRepository<PatientAppointment> {

    List<PatientAppointment> findByPatientIdAndSlotStatus(Long patientId, SlotStatus slotStatus);

    PatientAppointment findBySlotId(Long id);
}
