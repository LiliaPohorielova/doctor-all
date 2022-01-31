package ua.com.alevel;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import ua.com.alevel.config.ElasticsearchConfig;
import ua.com.alevel.config.HibernateConfig;
import ua.com.alevel.config.ScheduledConfig;
import ua.com.alevel.config.WebSecurityConfig;
import ua.com.alevel.persistence.entity.vaccination.Vaccination;
import ua.com.alevel.service.vaccination.VaccinationService;

import static ua.com.alevel.GenerationUtil.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@ContextConfiguration(classes = {ElasticsearchConfig.class, HibernateConfig.class, ScheduledConfig.class, WebSecurityConfig.class})
public class VaccinationServiceTest {

    @Autowired
    private VaccinationService vaccinationService;

    private final static int COUNT_OF_VACS = 5;

    @BeforeAll
    void init() {
        for (int i = 0; i < COUNT_OF_VACS; i++) {
            Vaccination vaccination = GenerationUtil.generateVaccination(NAME_OF_VACCINATION + i, NAME_OF_MANUFACTURER + i, 100 + i);
            vaccinationService.create(vaccination);
        }

        Assertions.assertEquals(COUNT_OF_VACS, vaccinationService.findAll().size());
    }

    @Order(1)
    @Test
    void shouldBeCreateVaccinationWhenAllFieldsIsNotEmpty() {
        Vaccination vaccination = GenerationUtil.generateVaccination(NAME_OF_VACCINATION + "test", NAME_OF_MANUFACTURER + "test", 500);
        vaccinationService.create(vaccination);
        Assertions.assertEquals(COUNT_OF_VACS + 1, vaccinationService.findAll().size());
    }

    @Order(2)
    @Test
    void vaccinationNameIsNull() {
        Vaccination vaccination = GenerationUtil.generateVaccination(NAME_OF_VACCINATION + "test", NAME_OF_MANUFACTURER + "test", 500);
        vaccinationService.create(vaccination);
        Assertions.assertEquals(COUNT_OF_VACS + 2, vaccinationService.findAll().size());
    }

    @Order(3)
    @Test
    void shouldBeDeleteVaccination() {
        Long id = vaccinationService.findAll().stream().findFirst().get().getId();
        vaccinationService.delete(id);
        Assertions.assertEquals(COUNT_OF_VACS + 1, vaccinationService.findAll().size());
    }

    @Order(4)
    @Test
    void shouldBeUpdateVaccination() {
        Vaccination vaccination = vaccinationService.findAll().stream().findFirst().get();
        vaccination.setName("Testing");
        vaccinationService.update(vaccination);
        Assertions.assertEquals("Testing", vaccinationService.findById(vaccination.getId()).get().getName());
    }

    @Order(5)
    @Test
    void shouldBeDeleteAllVaccinations() {
        vaccinationService.findAll().forEach(vaccination -> vaccinationService.delete(vaccination.getId()));
        Assertions.assertEquals(0, vaccinationService.findAll().size());
    }
}
