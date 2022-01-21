package ua.com.alevel.service.patient;

import ua.com.alevel.persistence.entity.user.PatientUser;
import ua.com.alevel.service.BaseCrudService;

import java.util.List;

public interface PatientUserService extends BaseCrudService<PatientUser> {

    PatientUser findByEmail(String email);

    boolean existByEmail(String email);

    List<PatientUser> findAll();
}