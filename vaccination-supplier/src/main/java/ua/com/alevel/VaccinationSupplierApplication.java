package ua.com.alevel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import ua.com.alevel.repository.VaccinationRepository;

@SpringBootApplication
public class VaccinationSupplierApplication {

    private final VaccinationRepository vaccinationRepository;

    public VaccinationSupplierApplication(VaccinationRepository vaccinationRepository) {
        this.vaccinationRepository = vaccinationRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(VaccinationSupplierApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {

    }
}
