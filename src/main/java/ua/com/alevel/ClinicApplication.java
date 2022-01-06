package ua.com.alevel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ua.com.alevel.persistence.entity.user.PatientUser;
import ua.com.alevel.persistence.entity.user.DoctorUser;
import ua.com.alevel.persistence.entity.user.Admin;
import ua.com.alevel.persistence.repository.user.AdminRepository;
import ua.com.alevel.persistence.repository.user.DoctorRepository;
import ua.com.alevel.persistence.repository.user.PatientRepository;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class })
public class ClinicApplication {

    private final BCryptPasswordEncoder encoder;
    private final AdminRepository adminRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    public ClinicApplication(BCryptPasswordEncoder encoder,
                             AdminRepository adminRepository,
                             DoctorRepository doctorRepository,
                             PatientRepository patientRepository) {
        this.encoder = encoder;
        this.adminRepository = adminRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(ClinicApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void listen() {

/*        PatientUser patient = new PatientUser();
        patient.setEmail("patient@mail.com");
        patient.setPassword(encoder.encode("rootroot"));
        patientRepository.save(patient);

        Admin admin = new Admin();
        admin.setEmail("admin@mail.com");
        admin.setPassword(encoder.encode("rootroot"));
        adminRepository.save(admin);*/
    }
}
