package ua.com.alevel;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import ua.com.alevel.config.ElasticsearchConfig;
import ua.com.alevel.config.HibernateConfig;
import ua.com.alevel.config.ScheduledConfig;
import ua.com.alevel.config.WebSecurityConfig;
import ua.com.alevel.exception.EntityExistException;
import ua.com.alevel.persistence.entity.doctor.Doctor;
import ua.com.alevel.persistence.entity.user.DoctorUser;
import ua.com.alevel.persistence.type.DoctorSpecialization;
import ua.com.alevel.service.doctor.DoctorService;
import ua.com.alevel.service.doctor.DoctorUserService;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ua.com.alevel.GenerationUtil.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@ContextConfiguration(classes = {ElasticsearchConfig.class, HibernateConfig.class, ScheduledConfig.class, WebSecurityConfig.class} )
public class DoctorServiceTest {

    @Autowired
    private DoctorUserService doctorUserService;

    @Autowired
    private DoctorService doctorService;

    private final static int COUNT_OF_DOCTORS = 5;

    @BeforeAll
    void init() {
        for (int i = 0; i < COUNT_OF_DOCTORS; i++) {
            DoctorUser doctorUser = GenerationUtil.generateDoctorUser(NAME_OF_DOCTOR + i + "@mail.com", PASSWORD + i);
            Doctor doctor = GenerationUtil.generateDoctor(NAME_OF_DOCTOR + i, NAME_OF_DOCTOR + i,NAME_OF_DOCTOR + i, DoctorSpecialization.DENTIST.toString());
            doctorUserService.create(doctorUser);
            doctorService.create(doctor);
            doctorUser.setDoctor(doctor);
            doctor.setDoctorUser(doctorUser);
        }

        Assertions.assertEquals(COUNT_OF_DOCTORS, doctorUserService.findAll().size());
    }

    @Order(1)
    @Test
    void shouldBeCreateDoctorWhenAllFieldsIsNotEmpty() {
        DoctorUser doctorUser = GenerationUtil.generateDoctorUser(NAME_OF_DOCTOR + "test@mail.com", PASSWORD + "test");
        Doctor doctor = GenerationUtil.generateDoctor(NAME_OF_DOCTOR + "test", NAME_OF_DOCTOR + "test", NAME_OF_DOCTOR + "test", DoctorSpecialization.DENTIST.toString());
        doctorUserService.create(doctorUser);
        doctorService.create(doctor);
        doctorUser.setDoctor(doctor);
        doctor.setDoctorUser(doctorUser);

        Assertions.assertEquals(COUNT_OF_DOCTORS + 1, doctorService.findAll().size());
    }

    @Order(2)
    @Test
    void shouldBeReturnExceptionWhenDoctorEmailIsAlreadyExist() {
        Exception exception = assertThrows(EntityExistException.class, () -> {
            DoctorUser doctorUser = GenerationUtil.generateDoctorUser(NAME_OF_DOCTOR + "test@mail.com", PASSWORD + "test");
            doctorUserService.create(doctorUser);
        });
        Assertions.assertEquals(COUNT_OF_DOCTORS + 1, doctorService.findAll().size());
    }

    @Order(3)
    @Test
    void shouldBeDeleteDoctor() {
        Long id = doctorService.findAll().stream().findFirst().get().getId();
        doctorService.delete(id);
        Assertions.assertEquals(COUNT_OF_DOCTORS, doctorService.findAll().size());
    }

    @Order(4)
    @Test
    void shouldBeUpdateDoctor() {
        Doctor doctor = doctorService.findAll().stream().findFirst().get();
        doctor.setLastname("Testing");
        doctor.setFirstname("Testing");
        doctorService.update(doctor);
        Assertions.assertEquals("Testing", doctorService.findById(doctor.getId()).get().getFirstname());
        Assertions.assertEquals("Testing", doctorService.findById(doctor.getId()).get().getLastname());
    }

    @Order(5)
    @Test
    void shouldBeDeleteAllDoctors() {
        doctorUserService.findAll().forEach(doctor -> doctorUserService.delete(doctor.getId()));
        Assertions.assertEquals(0, doctorUserService.findAll().size());
        doctorService.findAll().forEach(doctor -> doctorService.delete(doctor.getId()));
        Assertions.assertEquals(0, doctorService.findAll().size());
    }
}

