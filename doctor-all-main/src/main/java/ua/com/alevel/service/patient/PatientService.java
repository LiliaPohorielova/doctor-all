package ua.com.alevel.service.patient;

import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.appointment.PatientAppointment;
import ua.com.alevel.persistence.entity.doctor.Doctor;
import ua.com.alevel.persistence.entity.patient.Patient;
import ua.com.alevel.persistence.entity.vaccination.Vaccination;
import ua.com.alevel.service.BaseCrudService;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface PatientService extends BaseCrudService<Patient> {

    void savePatient(Patient patient);

    DataTableResponse<Patient> findAll(DataTableRequest request);

    List<Patient> findAll();

    Set<Doctor> getDoctors(Long id);

    Set<PatientAppointment> getAppointments(Long id);

    Set<Vaccination> getVaccinations(Long id);

    List<Doctor> searchDoctor(Map<String, Object> queryMap);

    List<Patient> searchPatient(Map<String, Object> queryMap);

    DataTableResponse<Doctor> findDoctorsByPatient(Patient patient, DataTableRequest dataTableRequest);
}
