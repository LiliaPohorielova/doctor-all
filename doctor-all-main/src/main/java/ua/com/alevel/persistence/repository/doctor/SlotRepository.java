package ua.com.alevel.persistence.repository.doctor;

import org.springframework.stereotype.Repository;
import ua.com.alevel.persistence.entity.doctor.Doctor;
import ua.com.alevel.persistence.entity.slot.Slot;
import ua.com.alevel.persistence.repository.BaseRepository;
import ua.com.alevel.persistence.type.SlotStatus;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface SlotRepository extends BaseRepository<Slot> {

    List<Slot> findByDoctorAndAppDate(Doctor doctor, LocalDate appdate);

    Slot findByDoctorAndAppDateAndStartTime(Doctor doctor, LocalDate date, LocalTime startTime);

    List<Slot> findByStatusAndDoctorId(SlotStatus slotStatus, Long doctorId);
}
