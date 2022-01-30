package ua.com.alevel.persistence.repository.patient;

import org.springframework.stereotype.Repository;
import ua.com.alevel.persistence.entity.doctor.Doctor;
import ua.com.alevel.persistence.entity.patient.Patient;
import ua.com.alevel.persistence.repository.BaseRepository;

import java.util.List;

@Repository
public interface PatientRepository extends BaseRepository<Patient> {

    List<Patient> findByLastnameContaining(String patientName);
}
