package ua.com.alevel.service.doctor;

import ua.com.alevel.persistence.entity.doctor.Doctor;

public interface DoctorService {

    void saveDoctor(Doctor doctor);

/*    List<PatientUser> getPatients(Long id);

    void addPatient(Long doctorId, Long patientId);

    void removePatient(Long doctorId, Long patientId);*/
}
