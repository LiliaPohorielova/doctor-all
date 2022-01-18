package ua.com.alevel.persistence.repository.patient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.alevel.persistence.entity.appointment.PatientAppointment;
import ua.com.alevel.persistence.type.SlotStatus;

import java.util.List;

@Repository
public interface PatientAppointmentRepository extends JpaRepository<PatientAppointment, Long> {

    List<PatientAppointment> findByPatientIdAndSlotStatus(Long id, SlotStatus slotStatus);

    PatientAppointment findBySlotId(Long id);
}
