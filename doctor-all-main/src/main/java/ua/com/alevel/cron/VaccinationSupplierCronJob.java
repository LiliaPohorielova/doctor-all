package ua.com.alevel.cron;

import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ua.com.alevel.cron.model.VaccinationSupplier;

@Service
public class VaccinationSupplierCronJob {

    @Scheduled(fixedDelay = 3000)
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
        }
    }
}
