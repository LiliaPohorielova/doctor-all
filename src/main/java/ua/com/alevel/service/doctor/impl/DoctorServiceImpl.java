package ua.com.alevel.service.doctor.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.persistence.crud.CrudRepositoryHelper;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.doctor.Doctor;
import ua.com.alevel.persistence.entity.user.DoctorUser;
import ua.com.alevel.persistence.repository.doctor.DoctorRepository;
import ua.com.alevel.service.doctor.DoctorService;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    private DoctorRepository doctorRepository;
    private final CrudRepositoryHelper<Doctor, DoctorRepository> doctorRepositoryHelper;

    public DoctorServiceImpl(DoctorRepository doctorRepository, CrudRepositoryHelper<Doctor, DoctorRepository> doctorRepositoryHelper) {
        this.doctorRepository = doctorRepository;
        this.doctorRepositoryHelper = doctorRepositoryHelper;
    }

    public void saveDoctor(Doctor doctor) {
        doctorRepository.save(doctor);
    }

    @Override
    @Transactional(readOnly = true)
    public DataTableResponse<Doctor> findAll(DataTableRequest request) {
        List<Doctor> doctors = doctorRepositoryHelper.findAll(doctorRepository, request).getItems();
        for (Doctor doctor:
                doctors) {
            System.out.println(doctor);
        }
        return doctorRepositoryHelper.findAll(doctorRepository, request);
    }

    @Override
    public List<Doctor> findAll() {
        List<Doctor> doctors = doctorRepository.findAll();
        for (Doctor doctor:
                doctors) {
            System.out.println(doctor);
        }
        return doctorRepository.findAll();
    }
}
