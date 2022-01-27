package ua.com.alevel.facade.doctor.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.facade.doctor.DoctorRegistrationFacade;
import ua.com.alevel.persistence.entity.doctor.Doctor;
import ua.com.alevel.persistence.entity.user.DoctorUser;
import ua.com.alevel.service.department.DepartmentService;
import ua.com.alevel.service.doctor.DoctorService;
import ua.com.alevel.service.doctor.DoctorUserService;
import ua.com.alevel.web.dto.request.doctor.DoctorRequestDto;

@Service
public class DoctorRegistrationFacadeImpl implements DoctorRegistrationFacade {

    private final DoctorUserService doctorUserService;
    private final DoctorService doctorService;
    private final DepartmentService departmentService;

    public DoctorRegistrationFacadeImpl(DoctorUserService doctorUserService, DoctorService doctorService, DepartmentService departmentService) {
        this.doctorUserService = doctorUserService;
        this.doctorService = doctorService;
        this.departmentService = departmentService;
    }

    @Override
    public void registration(DoctorRequestDto dto) {
        DoctorUser doctorUser = new DoctorUser();
        doctorUser.setEmail(dto.getEmail());
        doctorUser.setPassword(dto.getPassword());
        doctorUserService.create(doctorUser);

        Doctor doctor = new Doctor();
        doctor.setFirstname(dto.getFirstname());
        doctor.setLastname(dto.getLastname());
        doctor.setMiddleName(dto.getMiddleName());
        doctor.setSpecialization(dto.getSpecialization());
        doctor.setDepartment(departmentService.findDepartmentBySpecialization(dto.getSpecialization()));
        doctor.setDoctorUser(doctorUser);

        doctorService.saveDoctor(doctor);
        doctorUser.setDoctor(doctor);
    }

    @Override
    public DoctorUser findByEmail(String email) {
        return doctorUserService.findByEmail(email);
    }

    @Override
    public void delete(Long id) {
        doctorUserService.delete(id);
    }
}
