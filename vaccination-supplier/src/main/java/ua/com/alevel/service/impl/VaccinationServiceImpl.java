package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.entity.Vaccination;
import ua.com.alevel.repository.VaccinationRepository;
import ua.com.alevel.service.VaccinationService;

import java.util.List;
import java.util.Optional;

@Service
public class VaccinationServiceImpl implements VaccinationService {

    private final VaccinationRepository repository;

    public VaccinationServiceImpl(VaccinationRepository repository) {
        this.repository = repository;
    }

    @Override
    public void create(Vaccination entity) {
        repository.save(entity);
    }

    @Override
    public void update(Vaccination entity) {
        checkExist(repository, entity.getId());
        repository.save(entity);
    }

    @Override
    public void delete(Long id) {
        checkExist(repository, id);
        repository.deleteById(id);
    }

    @Override
    public Optional<Vaccination> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Vaccination> findAll() {
        return (List<Vaccination>) repository.findAll();
    }

    private void checkExist(VaccinationRepository repository, Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("entity not found");
        }
    }
}
