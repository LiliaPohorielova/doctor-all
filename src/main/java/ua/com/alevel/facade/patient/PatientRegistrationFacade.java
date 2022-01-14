package ua.com.alevel.facade.patient;

import ua.com.alevel.facade.RegistrationFacade;
import ua.com.alevel.persistence.entity.user.PatientUser;
import ua.com.alevel.web.dto.request.patient.PatientRequestDto;

public interface PatientRegistrationFacade extends RegistrationFacade<PatientRequestDto, PatientUser> {

    void delete(Long id);
}
