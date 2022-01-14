package ua.com.alevel.facade.doctor;

import ua.com.alevel.facade.RegistrationFacade;
import ua.com.alevel.persistence.entity.user.DoctorUser;
import ua.com.alevel.web.dto.request.doctor.DoctorRequestDto;

public interface DoctorRegistrationFacade extends RegistrationFacade<DoctorRequestDto, DoctorUser> {

    void delete(Long id);
}
