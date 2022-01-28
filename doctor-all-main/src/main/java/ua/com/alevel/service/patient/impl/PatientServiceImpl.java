package ua.com.alevel.service.patient.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.persistence.crud.CrudRepositoryHelper;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.appointment.PatientAppointment;
import ua.com.alevel.persistence.entity.doctor.Doctor;
import ua.com.alevel.persistence.entity.patient.Patient;
import ua.com.alevel.persistence.entity.vaccination.Vaccination;
import ua.com.alevel.persistence.repository.patient.PatientRepository;
import ua.com.alevel.service.patient.PatientService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final CrudRepositoryHelper<Patient, PatientRepository> patientRepositoryHelper;

    public PatientServiceImpl(PatientRepository patientRepository, CrudRepositoryHelper<Patient, PatientRepository> patientRepositoryHelper) {
        this.patientRepository = patientRepository;
        this.patientRepositoryHelper = patientRepositoryHelper;
    }

    public void savePatient(Patient patient) {
        patientRepository.save(patient);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void create(Patient entity) {
        patientRepositoryHelper.create(patientRepository, entity);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void update(Patient entity) {
        patientRepositoryHelper.update(patientRepository, entity);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void delete(Long id) {
        patientRepositoryHelper.delete(patientRepository, id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Patient> findById(Long id) {
        return patientRepositoryHelper.findById(patientRepository, id);
    }

    @Override
    @Transactional(readOnly = true)
    public DataTableResponse<Patient> findAll(DataTableRequest request) {
        return patientRepositoryHelper.findAll(patientRepository, request);
    }

    @Override
    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    @Override
    public Set<Doctor> getDoctors(Long id) {
        return patientRepositoryHelper.findById(patientRepository, id).get().getDoctors();
    }

    @Override
    public Set<PatientAppointment> getAppointments(Long id) {
        return patientRepositoryHelper.findById(patientRepository, id).get().getPatientAppointments();
    }

    @Override
    public Set<Vaccination> getVaccinations(Long id) {
        return patientRepositoryHelper.findById(patientRepository, id).get().getPatientVaccinations();
    }
}
