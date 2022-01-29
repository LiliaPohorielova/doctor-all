package ua.com.alevel.service.supplier.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ua.com.alevel.cron.model.VaccinationSupplier;
import ua.com.alevel.persistence.entity.vaccination.Vaccination;
import ua.com.alevel.persistence.repository.vaccination.VaccinationRepository;
import ua.com.alevel.service.supplier.VaccinationSupplierService;

import java.util.Optional;

@Service
public class VaccinationSupplierServiceImpl implements VaccinationSupplierService {

    @Value("${supplier.url}")
    private String url;

    @Value("${supplier.token}")
    private String token;

    private final VaccinationRepository vaccinationRepository;

    public VaccinationSupplierServiceImpl(VaccinationRepository vaccinationRepository) {
        this.vaccinationRepository = vaccinationRepository;
    }

    @Override
    public void syncToSupplier() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("token", token);
        HttpEntity request = new HttpEntity(headers);
        ResponseEntity<VaccinationSupplier[]> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                VaccinationSupplier[].class);
        if (response.getStatusCodeValue() == 200) {
            VaccinationSupplier[] vaccinationSuppliers = response.getBody();
            if (vaccinationSuppliers != null) {
                for (VaccinationSupplier vaccinationSupplier : vaccinationSuppliers) {
                    Optional<Vaccination> res = vaccinationRepository.findById(vaccinationSupplier.getId());
                    if (res.isPresent()) {
                        Vaccination vaccination = res.get();
                        vaccination.setVaccinationId(vaccinationSupplier.getId());
                        vaccination.setName(vaccinationSupplier.getName());
                        vaccination.setQuantity(vaccinationSupplier.getQuantity());
                        vaccination.setImageUrl(vaccinationSupplier.getImageUrl());
                        vaccination.setMethodOfAdministration(vaccinationSupplier.getMethodOfAdministration());
                        vaccination.setManufacturer(vaccinationSupplier.getManufacturer());
                        vaccinationRepository.save(vaccination);
                    }
                }
            }
        }
    }

}
