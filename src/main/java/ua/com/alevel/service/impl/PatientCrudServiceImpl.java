package ua.com.alevel.service.impl;

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
import ua.com.alevel.persistence.repository.user.PatientRepository;
import ua.com.alevel.service.PatientCrudService;

import java.util.Optional;

@Service
public class PatientCrudServiceImpl implements PatientCrudService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PatientRepository patientRepository;
    private final CrudRepositoryHelper<PatientUser, PatientRepository> crudRepositoryHelper;

    public PatientCrudServiceImpl(
            BCryptPasswordEncoder bCryptPasswordEncoder,
            PatientRepository patientRepository, CrudRepositoryHelper<PatientUser, PatientRepository> crudRepositoryHelper) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.patientRepository = patientRepository;
        this.crudRepositoryHelper = crudRepositoryHelper;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    public void create(PatientUser patient) {
        if (patientRepository.existsByEmail(patient.getEmail())) {
            throw new EntityExistException("this patient is exist");
        }
        patient.setPassword(bCryptPasswordEncoder.encode(patient.getPassword()));
        crudRepositoryHelper.create(patientRepository, patient);
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
