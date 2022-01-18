package ua.com.alevel.persistence.repository.patient;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.entity.doctor.Doctor;
import ua.com.alevel.persistence.entity.patient.Patient;
import ua.com.alevel.persistence.repository.BaseRepository;

import java.util.List;
import java.util.Set;

@Repository
public interface PatientRepository extends BaseRepository<Patient> { }
