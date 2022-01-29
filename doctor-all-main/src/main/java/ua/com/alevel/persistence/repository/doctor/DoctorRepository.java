package ua.com.alevel.persistence.repository.doctor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.com.alevel.persistence.entity.department.DoctorsDepartment;
import ua.com.alevel.persistence.entity.doctor.Doctor;
import ua.com.alevel.persistence.entity.patient.Patient;
import ua.com.alevel.persistence.repository.BaseRepository;

import java.util.List;

@Repository
public interface DoctorRepository extends BaseRepository<Doctor> {

    @Query("select p from Patient p INNER JOIN p.doctors d WHERE d.id  = ?1")
    Page<Patient> getPatientsById(Long id, Pageable pageable);

    @Query("select d from Doctor d INNER JOIN d.department dt WHERE dt.id  = ?1")
    Page<Doctor> getDoctorsByDepartmentId(Long id, Pageable pageable);

    List<Doctor> findByDoctorNameContaining(String doctorName);
}
