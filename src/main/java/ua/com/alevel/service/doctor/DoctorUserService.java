package ua.com.alevel.service.doctor;

import ua.com.alevel.persistence.entity.user.DoctorUser;
import ua.com.alevel.service.BaseCrudService;

import java.util.List;

public interface DoctorUserService extends BaseCrudService<DoctorUser> {
    
    DoctorUser findByEmail(String email);

    boolean existByEmail(String email);

    List<DoctorUser> findAll();
}