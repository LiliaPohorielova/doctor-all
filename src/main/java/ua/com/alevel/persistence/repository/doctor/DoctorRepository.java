package ua.com.alevel.persistence.repository.doctor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.com.alevel.persistence.entity.doctor.Doctor;
import ua.com.alevel.persistence.entity.patient.Patient;
import ua.com.alevel.persistence.repository.BaseRepository;

@Repository
public interface DoctorRepository extends BaseRepository<Doctor> {

    @Query("select p from Patient p INNER JOIN p.doctors d WHERE d.id  = ?1")
    Page<Patient> getPatientsById(Long id, Pageable pageable);
}
