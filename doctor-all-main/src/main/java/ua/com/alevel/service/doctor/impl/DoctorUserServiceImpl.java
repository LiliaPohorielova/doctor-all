package ua.com.alevel.service.doctor.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.exception.EntityExistException;
import ua.com.alevel.persistence.crud.CrudRepositoryHelper;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.user.DoctorUser;
import ua.com.alevel.persistence.repository.user.DoctorUserRepository;
import ua.com.alevel.service.doctor.DoctorUserService;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorUserServiceImpl implements DoctorUserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final DoctorUserRepository doctorUserRepository;
    private final CrudRepositoryHelper<DoctorUser, DoctorUserRepository> doctorUserRepositoryHelper;
    
    
    public DoctorUserServiceImpl(
            BCryptPasswordEncoder bCryptPasswordEncoder, DoctorUserRepository doctorUserRepository,
            CrudRepositoryHelper<DoctorUser, DoctorUserRepository> doctorUserRepositoryHelper) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.doctorUserRepository = doctorUserRepository;
        this.doctorUserRepositoryHelper = doctorUserRepositoryHelper;
    }

    public DoctorUser findByEmail(String email) {
        return doctorUserRepository.findByEmail(email);
    }

    @Override
    public boolean existByEmail(String email) {
        return doctorUserRepository.existsByEmail(email);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    public void create(DoctorUser doctor) {
        if (doctorUserRepository.existsByEmail(doctor.getEmail())) {
            throw new EntityExistException("this doctor is exist");
        }
        doctor.setPassword(bCryptPasswordEncoder.encode(doctor.getPassword()));
        doctorUserRepositoryHelper.create(doctorUserRepository, doctor);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void update(DoctorUser entity) {
        doctorUserRepositoryHelper.update(doctorUserRepository, entity);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void delete(Long id) {
        doctorUserRepositoryHelper.delete(doctorUserRepository, id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DoctorUser> findById(Long id) {
        return doctorUserRepositoryHelper.findById(doctorUserRepository, id);
    }

    @Override
    @Transactional(readOnly = true)
    public DataTableResponse<DoctorUser> findAll(DataTableRequest request) {
        return doctorUserRepositoryHelper.findAll(doctorUserRepository, request);
    }

    @Override
    public List<DoctorUser> findAll() {
        return doctorUserRepository.findAll();
    }
}
