package ua.com.alevel.service.doctor;

import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.doctor.Doctor;

import java.util.List;

public interface DoctorService {

    void saveDoctor(Doctor doctor);

    @Transactional(readOnly = true)
    DataTableResponse<Doctor> findAll(DataTableRequest request);

    List<Doctor> findAll();

/*    List<PatientUser> getPatients(Long id);

    void addPatient(Long doctorId, Long patientId);

    void removePatient(Long doctorId, Long patientId);*/
}
