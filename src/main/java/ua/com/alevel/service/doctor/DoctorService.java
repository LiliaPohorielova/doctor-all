package ua.com.alevel.service.doctor;

import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.doctor.Doctor;
import ua.com.alevel.service.BaseCrudService;

import java.util.List;

public interface DoctorService extends BaseCrudService<Doctor> {

    void saveDoctor(Doctor doctor);

    DataTableResponse<Doctor> findAll(DataTableRequest request);

    List<Doctor> findAll();

/*    List<PatientUser> getPatients(Long id);

    void addPatient(Long doctorId, Long patientId);

    void removePatient(Long doctorId, Long patientId);*/
}
