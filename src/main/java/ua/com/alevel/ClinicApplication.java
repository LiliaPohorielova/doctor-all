package ua.com.alevel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ua.com.alevel.persistence.repository.user.AdminUserRepository;
import ua.com.alevel.persistence.repository.user.DoctorUserRepository;
import ua.com.alevel.persistence.repository.user.PatientUserRepository;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class })
public class ClinicApplication {

    private final BCryptPasswordEncoder encoder;
    private final AdminUserRepository adminUserRepository;
    private final DoctorUserRepository doctorUserRepository;
    private final PatientUserRepository patientUserRepository;

    public ClinicApplication(BCryptPasswordEncoder encoder,
                             AdminUserRepository adminUserRepository,
                             DoctorUserRepository doctorUserRepository,
                             PatientUserRepository patientUserRepository) {
        this.encoder = encoder;
        this.adminUserRepository = adminUserRepository;
        this.doctorUserRepository = doctorUserRepository;
        this.patientUserRepository = patientUserRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(ClinicApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void listen() {

/*        PatientUser patient = new PatientUser();
        patient.setEmail("patient@mail.com");
        patient.setPassword(encoder.encode("rootroot"));
        patientUserRepository.save(patient);*/

/*        Admin admin = new Admin();
        admin.setEmail("admin@mail.com");
        admin.setPassword(encoder.encode("rootroot"));
        adminRepository.save(admin);*/

/*        DoctorUser doctor = new DoctorUser();
        doctor.setEmail("doctor@mail.com");
        doctor.setPassword(encoder.encode("rootroot"));
        doctorRepository.save(doctor);*/
    }
}
