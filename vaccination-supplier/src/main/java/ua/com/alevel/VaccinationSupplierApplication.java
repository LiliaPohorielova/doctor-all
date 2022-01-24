package ua.com.alevel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import ua.com.alevel.entity.Vaccination;
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
/*        Vaccination astrazeneca = new Vaccination();
        astrazeneca.setVaccinationId(1L);
        astrazeneca.setName("AstraZeneca");
        astrazeneca.setQuantity(100);
        vaccinationRepository.save(astrazeneca);

        Vaccination pfizer = new Vaccination();
        pfizer.setVaccinationId(2L);
        pfizer.setName("Pfizer");
        pfizer.setQuantity(200);
        vaccinationRepository.save(pfizer);

        Vaccination coronaVac = new Vaccination();
        coronaVac.setVaccinationId(3L);
        coronaVac.setName("CoronaVac");
        coronaVac.setQuantity(300);
        vaccinationRepository.save(coronaVac);*/
    }
}
