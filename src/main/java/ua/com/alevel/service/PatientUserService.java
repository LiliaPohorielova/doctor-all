package ua.com.alevel.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import ua.com.alevel.persistence.entity.user.DoctorUser;
import ua.com.alevel.persistence.entity.user.PatientUser;

import java.util.List;
/*@Primary
@Qualifier
public interface PatientUserService extends UserDetailsService {
    void create(PatientUser patient);
    PatientUser findByEmail(String email);
    *//*List<DoctorUser> getDoctors(Long id);*//*
}*/

public interface PatientUserService extends BaseCrudService<PatientUser> {
    PatientUser findByEmail(String email);
}