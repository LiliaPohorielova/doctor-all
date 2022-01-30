package ua.com.alevel.persistence.repository.patient;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.com.alevel.persistence.entity.doctor.Doctor;
import ua.com.alevel.persistence.entity.patient.Patient;
import ua.com.alevel.persistence.repository.BaseRepository;

import java.util.List;

@Repository
public interface PatientRepository extends BaseRepository<Patient> {

    List<Patient> findByLastnameContaining(String patientName);

    @Query("select d from Doctor d INNER JOIN d.patients p WHERE p.id  = ?1")
    Page<Doctor> getDoctorsById(Long id, Pageable pageable);
}
