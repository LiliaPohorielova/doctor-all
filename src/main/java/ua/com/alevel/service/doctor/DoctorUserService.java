package ua.com.alevel.service.doctor;

import ua.com.alevel.persistence.entity.user.DoctorUser;
import ua.com.alevel.service.BaseCrudService;

public interface DoctorUserService extends BaseCrudService<DoctorUser> {
    
    DoctorUser findByEmail(String email);
}