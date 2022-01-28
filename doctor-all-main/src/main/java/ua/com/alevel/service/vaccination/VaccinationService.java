package ua.com.alevel.service.vaccination;

import ua.com.alevel.persistence.entity.vaccination.Vaccination;
import ua.com.alevel.service.BaseCrudService;

import java.util.List;

public interface VaccinationService extends BaseCrudService<Vaccination> {

    List<Vaccination> findAll();
}
