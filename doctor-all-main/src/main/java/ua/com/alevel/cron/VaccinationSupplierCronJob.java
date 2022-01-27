package ua.com.alevel.cron;

import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ua.com.alevel.cron.model.VaccinationSupplier;
import ua.com.alevel.persistence.entity.vaccination.Vaccination;
import ua.com.alevel.persistence.repository.vaccination.VaccinationRepository;

import java.util.Optional;

@Service
public class VaccinationSupplierCronJob {

    private final VaccinationRepository vaccinationRepository;

    public VaccinationSupplierCronJob(VaccinationRepository vaccinationRepository) {
        this.vaccinationRepository = vaccinationRepository;
    }

    @Scheduled(fixedDelay = 1000 * 60 * 10)
    public void synchronizationWithVaccinationSupplier() {
        System.out.println("VaccinationSupplierCronJob.synchronizationWithVaccinationSupplier");
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("token", "vaccination");
        HttpEntity request = new HttpEntity(headers);
        ResponseEntity<VaccinationSupplier[]> response = restTemplate.exchange(
                "http://localhost:8081/api/vaccinations",
                HttpMethod.GET,
                request,
                VaccinationSupplier[].class);
        if (response.getStatusCodeValue() == 200) {
            VaccinationSupplier[] vaccinationSuppliers = response.getBody();
            for (VaccinationSupplier vaccinationSupplier : vaccinationSuppliers) {
                Optional<Vaccination> vaccinationOptional = vaccinationRepository.findById(vaccinationSupplier.getId());
                if (vaccinationOptional.isPresent()) {
                    Vaccination vaccination = vaccinationOptional.get();
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
