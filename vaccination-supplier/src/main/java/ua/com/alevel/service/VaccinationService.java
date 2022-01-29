package ua.com.alevel.service;

import ua.com.alevel.entity.Vaccination;

import java.util.List;
import java.util.Optional;

public interface VaccinationService {

    void create(Vaccination entity);
    void update(Vaccination entity);
    void delete(Long id);
    Optional<Vaccination> findById(Long id);
    List<Vaccination> findAll();
}
