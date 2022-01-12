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

import java.util.Optional;

@Service
public class DoctorUserServiceImpl implements DoctorUserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final DoctorUserRepository doctorUserRepository;
    private final CrudRepositoryHelper<DoctorUser, DoctorUserRepository> crudRepositoryHelper;

    public DoctorUserServiceImpl(
            BCryptPasswordEncoder bCryptPasswordEncoder,
            DoctorUserRepository doctorUserRepository, CrudRepositoryHelper<DoctorUser, DoctorUserRepository> crudRepositoryHelper) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.doctorUserRepository = doctorUserRepository;
        this.crudRepositoryHelper = crudRepositoryHelper;
    }

    public DoctorUser findByEmail(String email) {
        return doctorUserRepository.findByEmail(email);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    public void create(DoctorUser doctor) {
        if (doctorUserRepository.existsByEmail(doctor.getEmail())) {
            throw new EntityExistException("this doctor is exist");
        }
        doctor.setPassword(bCryptPasswordEncoder.encode(doctor.getPassword()));
        crudRepositoryHelper.create(doctorUserRepository, doctor);
    }

    @Override
    public void update(DoctorUser entity) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Optional<DoctorUser> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public DataTableResponse<DoctorUser> findAll(DataTableRequest request) {
        return null;
    }
}
