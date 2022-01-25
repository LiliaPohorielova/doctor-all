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
        astrazeneca.setName("AstraZeneca");
        astrazeneca.setImageUrl("https://khoda.gov.ua/image/catalog/Anton/1205vaktsinats%D1%96ya.jpg");
        astrazeneca.setManufacturer("Great Britain");
        astrazeneca.setMethodOfAdministration("intramuscularly");
        astrazeneca.setQuantity(100);
        vaccinationRepository.save(astrazeneca);

        Vaccination pfizer = new Vaccination();
        pfizer.setName("Pfizer");
        pfizer.setImageUrl("https://gdb.voanews.com/3E8439CB-9EF0-48FF-90B2-A50E5CEE7760_w1080_h608.jpg");
        pfizer.setManufacturer("USA");
        pfizer.setMethodOfAdministration("intramuscularly");
        pfizer.setQuantity(200);
        vaccinationRepository.save(pfizer);

        Vaccination coronaVac = new Vaccination();
        coronaVac.setName("CoronaVac");
        coronaVac.setImageUrl("https://media.slovoidilo.ua/media/publications/14/135463/135463-1_large.jpg");
        coronaVac.setManufacturer("China");
        coronaVac.setMethodOfAdministration("intramuscularly");
        coronaVac.setQuantity(300);
        vaccinationRepository.save(coronaVac);*/
    }
}
