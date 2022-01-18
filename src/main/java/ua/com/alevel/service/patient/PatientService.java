package ua.com.alevel.service.patient;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.doctor.Doctor;
import ua.com.alevel.persistence.entity.patient.Patient;
import ua.com.alevel.service.BaseCrudService;

import java.util.List;
import java.util.Set;

public interface PatientService extends BaseCrudService<Patient> {

    void savePatient(Patient patient);

    DataTableResponse<Patient> findAll(DataTableRequest request);

    List<Patient> findAll();

    Set<Doctor> getDoctors(Long id);
}
