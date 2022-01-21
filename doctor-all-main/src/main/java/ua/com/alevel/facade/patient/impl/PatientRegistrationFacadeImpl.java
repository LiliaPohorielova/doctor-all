package ua.com.alevel.facade.patient.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.facade.patient.PatientRegistrationFacade;
import ua.com.alevel.persistence.entity.patient.Patient;
import ua.com.alevel.persistence.entity.user.PatientUser;
import ua.com.alevel.service.patient.PatientService;
import ua.com.alevel.service.patient.PatientUserService;
import ua.com.alevel.web.dto.request.patient.PatientRequestDto;

@Service
public class PatientRegistrationFacadeImpl implements PatientRegistrationFacade {

    private final PatientUserService patientUserService;
    private final PatientService patientService;

    public PatientRegistrationFacadeImpl(PatientUserService patientUserService, PatientService patientService) {
        this.patientUserService = patientUserService;
        this.patientService = patientService;
    }

    @Override
    public void registration(PatientRequestDto dto) {
        PatientUser patientUser = new PatientUser();
        patientUser.setEmail(dto.getEmail());
        patientUser.setPassword(dto.getPassword());
        patientUserService.create(patientUser);

        Patient patient = new Patient();
        patient.setFirstname(dto.getFirstname());
        patient.setLastname(dto.getLastname());
        patient.setPhoneNumber(dto.getPhoneNumber());
        patient.setDateOfBirth(dto.getDateOfBirth());
        patient.setGender(dto.getGender());
        patient.setPatientUser(patientUser);

        patientService.savePatient(patient);
        patientUser.setPatient(patient);
    }

    @Override
    public PatientUser findByEmail(String email) {
        return patientUserService.findByEmail(email);
    }

    @Override
    public void delete(Long id) {
        patientUserService.delete(id);
    }
}
