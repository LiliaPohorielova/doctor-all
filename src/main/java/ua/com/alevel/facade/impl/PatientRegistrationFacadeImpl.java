package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.facade.RegistrationFacade;
import ua.com.alevel.facade.patient.PatientRegistrationFacade;
import ua.com.alevel.persistence.entity.patient.Patient;
import ua.com.alevel.persistence.entity.user.PatientUser;
import ua.com.alevel.service.PatientService;
import ua.com.alevel.service.PatientUserService;
import ua.com.alevel.service.impl.PatientServiceImpl;
import ua.com.alevel.web.dto.request.register.AuthDto;
import ua.com.alevel.web.dto.request.register.PatientRequestDto;

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
        PatientUser patient = new PatientUser();
        Patient patient1 = new Patient();
        patient.setEmail(dto.getEmail());
        patient.setPassword(dto.getPassword());
        patientUserService.create(patient);
        patient1.setFirstname(dto.getFirstname());
        patient1.setLastname(dto.getLastname());
        patient1.setPatientUser(patient);
        patientService.savePatient(patient1);
        patient.setPatient(patient1);
    }
}
