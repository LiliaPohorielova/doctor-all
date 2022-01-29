package ua.com.alevel.cron;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ua.com.alevel.service.supplier.VaccinationSupplierService;


@Service
public class VaccinationSupplierCronJob {

    private final VaccinationSupplierService vaccinationSupplierService;

    public VaccinationSupplierCronJob(VaccinationSupplierService vaccinationSupplierService) {
        this.vaccinationSupplierService = vaccinationSupplierService;
    }

    @Scheduled(fixedDelay = 1000 * 60 * 10)
    public void synchronizationWithVaccinationSupplier() {
        System.out.println("VaccinationSupplierCronJob.synchronizationWithVaccinationSupplier");
        vaccinationSupplierService.syncToSupplier();
    }
}
