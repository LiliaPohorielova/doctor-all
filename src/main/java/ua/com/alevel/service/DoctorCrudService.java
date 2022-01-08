package ua.com.alevel.service;

import ua.com.alevel.persistence.entity.user.DoctorUser;
import ua.com.alevel.persistence.entity.user.PatientUser;

import java.util.List;

public interface DoctorCrudService extends BaseCrudService<DoctorUser> {

   List<PatientUser> getPatients(Long id);

    void addPatient(Long doctorId, Long patientId);

    void removePatient(Long doctorId, Long patientId);
}