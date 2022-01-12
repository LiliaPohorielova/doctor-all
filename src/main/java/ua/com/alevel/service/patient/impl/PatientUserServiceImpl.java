package ua.com.alevel.service.patient.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.exception.EntityExistException;
import ua.com.alevel.persistence.crud.CrudRepositoryHelper;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.user.PatientUser;
import ua.com.alevel.persistence.repository.user.PatientUserRepository;
import ua.com.alevel.service.patient.PatientUserService;

import java.util.Optional;

@Service
public class PatientUserServiceImpl implements PatientUserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PatientUserRepository patientUserRepository;
    private final CrudRepositoryHelper<PatientUser, PatientUserRepository> crudRepositoryHelper;

    public PatientUserServiceImpl(
            BCryptPasswordEncoder bCryptPasswordEncoder,
            PatientUserRepository patientUserRepository, CrudRepositoryHelper<PatientUser, PatientUserRepository> crudRepositoryHelper) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.patientUserRepository = patientUserRepository;
        this.crudRepositoryHelper = crudRepositoryHelper;
    }

    public PatientUser findByEmail(String email) {
        return patientUserRepository.findByEmail(email);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    public void create(PatientUser patient) {
        if (patientUserRepository.existsByEmail(patient.getEmail())) {
            throw new EntityExistException("this patient is exist");
        }
        patient.setPassword(bCryptPasswordEncoder.encode(patient.getPassword()));
        crudRepositoryHelper.create(patientUserRepository, patient);
    }

    @Override
    public void update(PatientUser entity) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Optional<PatientUser> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public DataTableResponse<PatientUser> findAll(DataTableRequest request) {
        return null;
    }
}
