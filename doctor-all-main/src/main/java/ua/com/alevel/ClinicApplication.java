package ua.com.alevel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ua.com.alevel.elastic.index.DoctorIndex;
import ua.com.alevel.persistence.entity.doctor.Doctor;
import ua.com.alevel.persistence.repository.doctor.DoctorRepository;
import ua.com.alevel.persistence.repository.user.AdminUserRepository;
import ua.com.alevel.persistence.repository.user.DoctorUserRepository;
import ua.com.alevel.persistence.repository.user.PatientUserRepository;
import ua.com.alevel.persistence.repository.vaccination.VaccinationRepository;

import javax.annotation.PreDestroy;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class })
public class ClinicApplication {

    private final BCryptPasswordEncoder encoder;
    private final AdminUserRepository adminUserRepository;
    private final DoctorUserRepository doctorUserRepository;
    private final DoctorRepository doctorRepository;
    private final PatientUserRepository patientUserRepository;
    private final VaccinationRepository vaccinationRepository;
    private final ElasticsearchOperations elasticsearchOperations;

    public ClinicApplication(BCryptPasswordEncoder encoder,
                             AdminUserRepository adminUserRepository,
                             DoctorUserRepository doctorUserRepository,
                             DoctorRepository doctorRepository, PatientUserRepository patientUserRepository, VaccinationRepository vaccinationRepository, ElasticsearchOperations elasticsearchOperations) {
        this.encoder = encoder;
        this.adminUserRepository = adminUserRepository;
        this.doctorUserRepository = doctorUserRepository;
        this.doctorRepository = doctorRepository;
        this.patientUserRepository = patientUserRepository;
        this.vaccinationRepository = vaccinationRepository;
        this.elasticsearchOperations = elasticsearchOperations;
    }

    public static void main(String[] args) {
        SpringApplication.run(ClinicApplication.class, args);
    }

    @PreDestroy
    public void deleteIndex() {
        elasticsearchOperations.indexOps(DoctorIndex.class).delete();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void listen() {
/*        Vaccination astrazeneca = new Vaccination();
        vaccinationRepository.save(astrazeneca);

        Vaccination pfizer = new Vaccination();
        vaccinationRepository.save(pfizer);

        Vaccination coronaVac = new Vaccination();
        vaccinationRepository.save(coronaVac);*/
/*        List<Doctor> doctors = doctorRepository.findAll();
        for (Doctor doctor:
             doctors) {
            System.out.println(doctor);
        }*/

/*        PatientUser patient = new PatientUser();
        patient.setEmail("patient@mail.com");
        patient.setPassword(encoder.encode("rootroot"));
        patientUserRepository.save(patient);*/

/*        Admin admin = new Admin();
        admin.setEmail("admin@mail.com");
        admin.setPassword(encoder.encode("rootroot"));
        adminUserRepository.save(admin);*/

/*        DoctorUser doctor = new DoctorUser();
        doctor.setEmail("doctor@mail.com");
        doctor.setPassword(encoder.encode("rootroot"));
        doctorRepository.save(doctor);*/
    }
}
