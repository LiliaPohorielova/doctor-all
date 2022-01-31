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
import ua.com.alevel.persistence.entity.patient.Patient;
import ua.com.alevel.persistence.entity.user.PatientUser;
import ua.com.alevel.service.patient.PatientService;
import ua.com.alevel.service.patient.PatientUserService;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ua.com.alevel.GenerationUtil.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@ContextConfiguration(classes = {ElasticsearchConfig.class, HibernateConfig.class, ScheduledConfig.class, WebSecurityConfig.class} )
public class PatientServiceTest {

    @Autowired
    private PatientUserService patientUserService;

    @Autowired
    private PatientService patientService;

    private final static int COUNT_OF_PATIENTS = 5;

    @BeforeAll
    void init() {
        for (int i = 0; i < COUNT_OF_PATIENTS; i++) {
            PatientUser patientUser = GenerationUtil.generatePatientUser(NAME_OF_PATIENT + i + "@mail.com", PASSWORD + i);
            Patient patient = GenerationUtil.generatePatient(NAME_OF_PATIENT + i, NAME_OF_PATIENT + i);
            patientUserService.create(patientUser);
            patientService.create(patient);
            patientUser.setPatient(patient);
            patient.setPatientUser(patientUser);
        }

        Assertions.assertEquals(COUNT_OF_PATIENTS, patientUserService.findAll().size());
    }

    @Order(1)
    @Test
    void shouldBeCreatePatientWhenAllFieldsIsNotEmpty() {
        PatientUser patientUser = GenerationUtil.generatePatientUser(NAME_OF_PATIENT + "test@mail.com", PASSWORD + "test");
        Patient patient = GenerationUtil.generatePatient(NAME_OF_PATIENT + "test", NAME_OF_PATIENT + "test");
        patientUserService.create(patientUser);
        patientService.create(patient);
        patientUser.setPatient(patient);
        patient.setPatientUser(patientUser);

        Assertions.assertEquals(COUNT_OF_PATIENTS + 1, patientService.findAll().size());
    }

    @Order(2)
    @Test
    void shouldBeReturnExceptionWhenPatientEmailIsAlreadyExist() {
        Exception exception = assertThrows(EntityExistException.class, () -> {
            PatientUser patientUser = GenerationUtil.generatePatientUser(NAME_OF_PATIENT + "test@mail.com", PASSWORD + "test");
            patientUserService.create(patientUser);
        });
        Assertions.assertEquals(COUNT_OF_PATIENTS + 1, patientService.findAll().size());
    }

    @Order(3)
    @Test
    void shouldBeDeletePatient() {
        Long id = patientService.findAll().stream().findFirst().get().getId();
        patientService.delete(id);
        Assertions.assertEquals(COUNT_OF_PATIENTS, patientService.findAll().size());
    }

    @Order(4)
    @Test
    void shouldBeUpdatePatient() {
        Patient patient = patientService.findAll().stream().findFirst().get();
        patient.setLastname("Testing");
        patient.setFirstname("Testing");
        patientService.update(patient);
        Assertions.assertEquals("Testing", patientService.findById(patient.getId()).get().getFirstname());
        Assertions.assertEquals("Testing", patientService.findById(patient.getId()).get().getLastname());
    }

    @Order(5)
    @Test
    void shouldBeDeleteAllPatients() {
/*
        patientUserService.findAll().forEach(patient -> patientUserService.delete(patient.getId()));
        Assertions.assertEquals(0, patientUserService.findAll().size());*/

        patientService.findAll().forEach(patient -> patientService.delete(patient.getId()));
        Assertions.assertEquals(0, patientService.findAll().size());
    }
}

