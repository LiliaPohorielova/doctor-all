package ua.com.alevel.service.doctor.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.persistence.crud.CrudRepositoryHelper;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.doctor.Doctor;
import ua.com.alevel.persistence.entity.user.DoctorUser;
import ua.com.alevel.persistence.repository.doctor.DoctorRepository;
import ua.com.alevel.service.doctor.DoctorService;

import java.util.List;
import java.util.Optional;

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
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void create(Doctor entity) {
        doctorRepositoryHelper.create(doctorRepository, entity);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void update(Doctor entity) {
        doctorRepositoryHelper.update(doctorRepository, entity);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void delete(Long id) {
        doctorRepositoryHelper.delete(doctorRepository, id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Doctor> findById(Long id) {
        return doctorRepositoryHelper.findById(doctorRepository, id);
    }

    @Override
    @Transactional(readOnly = true)
    public DataTableResponse<Doctor> findAll(DataTableRequest request) {
        return doctorRepositoryHelper.findAll(doctorRepository, request);
    }

    @Override
    public List<Doctor> findAll() {
        return doctorRepository.findAll();
    }
}
