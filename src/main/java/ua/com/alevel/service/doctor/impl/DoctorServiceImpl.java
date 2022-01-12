package ua.com.alevel.service.doctor.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.entity.doctor.Doctor;
import ua.com.alevel.persistence.repository.doctor.DoctorRepository;
import ua.com.alevel.service.doctor.DoctorService;

@Service
public class DoctorServiceImpl implements DoctorService {

    private DoctorRepository doctorRepository;

    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public void saveDoctor(Doctor doctor) {
        doctorRepository.save(doctor);
    }
}
