package ua.com.alevel.service;

import ua.com.alevel.persistence.entity.user.DoctorUser;
import ua.com.alevel.persistence.entity.user.PatientUser;

import java.util.Set;

public interface AdminCrudService extends BaseCrudService<DoctorUser>{
    /*Set<DoctorUser> getDoctors(Long id);

    Set<PatientUser> findAll();
    Set<PatientUser> getPatients(Long id);

    void addPatient(Long doctorId, Long patientId);

    void removePatient(Long doctorId, Long patientId);

    Set<DoctorUser> findAll();*/
}
