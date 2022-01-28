package ua.com.alevel.service.vaccination.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.persistence.crud.CrudRepositoryHelper;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.vaccination.Vaccination;
import ua.com.alevel.persistence.repository.vaccination.VaccinationRepository;
import ua.com.alevel.service.vaccination.VaccinationService;

import java.util.List;
import java.util.Optional;

@Service
public class VaccinationServiceImpl implements VaccinationService {

    private final VaccinationRepository vaccinationRepository;
    private final CrudRepositoryHelper<Vaccination, VaccinationRepository> vaccinationRepositoryHelper;

    public VaccinationServiceImpl(VaccinationRepository vaccinationRepository,
                                  CrudRepositoryHelper<Vaccination, VaccinationRepository> vaccinationRepositoryHelper) {
        this.vaccinationRepository = vaccinationRepository;
        this.vaccinationRepositoryHelper = vaccinationRepositoryHelper;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void create(Vaccination entity) {
        vaccinationRepositoryHelper.create(vaccinationRepository, entity);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void update(Vaccination entity) {
        vaccinationRepositoryHelper.update(vaccinationRepository, entity);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void delete(Long id) {
        vaccinationRepositoryHelper.delete(vaccinationRepository, id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Vaccination> findById(Long id) {
        return vaccinationRepositoryHelper.findById(vaccinationRepository, id);
    }

    @Override
    @Transactional(readOnly = true)
    public DataTableResponse<Vaccination> findAll(DataTableRequest request) {
        return vaccinationRepositoryHelper.findAll(vaccinationRepository, request);
    }

    @Override
    public List<Vaccination> findAll() {
        return vaccinationRepository.findAll();
    }
}
