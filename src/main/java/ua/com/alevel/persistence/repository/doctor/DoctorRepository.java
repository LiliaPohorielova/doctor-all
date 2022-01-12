package ua.com.alevel.persistence.repository.doctor;

import org.springframework.stereotype.Repository;
import ua.com.alevel.persistence.entity.doctor.Doctor;
import ua.com.alevel.persistence.repository.BaseRepository;

@Repository
public interface DoctorRepository extends BaseRepository<Doctor> { }
