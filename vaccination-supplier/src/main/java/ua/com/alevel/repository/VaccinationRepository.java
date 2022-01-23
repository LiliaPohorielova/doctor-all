package ua.com.alevel.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.com.alevel.entity.Vaccination;

@Repository
public interface VaccinationRepository extends CrudRepository<Vaccination, Long> { }
