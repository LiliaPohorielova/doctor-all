package ua.com.alevel.service.patient.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.entity.patient.Patient;
import ua.com.alevel.persistence.repository.patient.PatientRepository;
import ua.com.alevel.service.patient.PatientService;

@Service
public class PatientServiceImpl implements PatientService {

    private PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public void savePatient(Patient patient) {
        patientRepository.save(patient);
    }
}
