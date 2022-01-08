package ua.com.alevel.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.entity.patient.Patient;
import ua.com.alevel.persistence.repository.patient.PatientRepository;
import ua.com.alevel.service.PatientService;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public void savePatient(Patient patient) {
        patientRepository.save(patient);
    }
}
