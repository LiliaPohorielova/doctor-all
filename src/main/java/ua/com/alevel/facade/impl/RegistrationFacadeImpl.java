package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.facade.RegistrationFacade;
import ua.com.alevel.persistence.entity.user.PatientUser;
import ua.com.alevel.service.PatientUserService;
import ua.com.alevel.web.dto.request.register.AuthDto;

@Service
public class RegistrationFacadeImpl implements RegistrationFacade {

    private final PatientUserService patientUserService;

    public RegistrationFacadeImpl(PatientUserService patientUserService) {
        this.patientUserService = patientUserService;
    }

    @Override
    public void registration(AuthDto dto) {
        PatientUser patient = new PatientUser();
        patient.setEmail(dto.getEmail());
        patient.setPassword(dto.getPassword());
        patientUserService.create(patient);
    }
}
