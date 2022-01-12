package ua.com.alevel.service.patient;

import ua.com.alevel.persistence.entity.user.PatientUser;
import ua.com.alevel.service.BaseCrudService;

public interface PatientUserService extends BaseCrudService<PatientUser> {

    PatientUser findByEmail(String email);
}