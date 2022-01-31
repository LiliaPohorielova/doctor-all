package ua.com.alevel;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import ua.com.alevel.config.ElasticsearchConfig;
import ua.com.alevel.config.HibernateConfig;
import ua.com.alevel.config.ScheduledConfig;
import ua.com.alevel.config.WebSecurityConfig;
import ua.com.alevel.persistence.entity.doctor.Doctor;
import ua.com.alevel.persistence.entity.slot.Slot;
import ua.com.alevel.persistence.type.DoctorSpecialization;
import ua.com.alevel.service.doctor.DoctorService;
import ua.com.alevel.service.slot.SlotService;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ua.com.alevel.GenerationUtil.NAME_OF_DOCTOR;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@ContextConfiguration(classes = {ElasticsearchConfig.class, HibernateConfig.class, ScheduledConfig.class, WebSecurityConfig.class} )
public class SlotServiceTest {

    @Autowired
    private SlotService slotService;

    @Autowired
    private DoctorService doctorService;


    private final static int COUNT_OF_SLOTS = 5;

    @BeforeAll
    void init() {
        for (int i = 0; i < COUNT_OF_SLOTS; i++) {
            Doctor doctor = GenerationUtil.generateDoctor(NAME_OF_DOCTOR + i, NAME_OF_DOCTOR + i,NAME_OF_DOCTOR + i, DoctorSpecialization.DENTIST.toString());
            doctorService.create(doctor);
            Slot slot = GenerationUtil.generateSlot(doctor);
            slotService.create(slot);
        }

        Assertions.assertEquals(COUNT_OF_SLOTS, slotService.findAll().size());
    }

    @Order(1)
    @Test
    void shouldBeCreateSlotWhenAllFieldsIsNotEmpty() {
        Doctor doctor = GenerationUtil.generateDoctor(NAME_OF_DOCTOR + "test", NAME_OF_DOCTOR + "test",NAME_OF_DOCTOR + "test", DoctorSpecialization.DENTIST.toString());
        doctorService.create(doctor);
        Slot slot = GenerationUtil.generateSlot(doctor);
        slotService.create(slot);
        Assertions.assertEquals(COUNT_OF_SLOTS + 1, slotService.findAll().size());
    }

    @Order(2)
    @Test
    void shouldBeReturnExceptionWhenSlotDoctorIsNull() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            Slot slot = GenerationUtil.generateSlot(null);
            slotService.create(slot);
        });
        Assertions.assertEquals(COUNT_OF_SLOTS + 1, slotService.findAll().size());
    }

    @Order(3)
    @Test
    void shouldBeDeleteSlot() {
        Long id = slotService.findAll().stream().findFirst().get().getId();
        slotService.delete(id);
        Assertions.assertEquals(COUNT_OF_SLOTS, slotService.findAll().size());
    }

    @Order(4)
    @Test
    void shouldBeUpdateSlot() {
        Doctor doctor = GenerationUtil.generateDoctor("Testing", NAME_OF_DOCTOR + "test",NAME_OF_DOCTOR + "test", DoctorSpecialization.DENTIST.toString());
        doctorService.create(doctor);
        Slot slot = slotService.findAll().stream().findFirst().get();
        slot.setDoctor(doctor);
        slotService.update(slot);
        Assertions.assertEquals("Testing", slotService.findById(slot.getId()).get().getDoctor().getFirstname());
    }

    @Order(5)
    @Test
    void shouldBeDeleteAllSlots() {
        slotService.findAll().forEach(slot -> slotService.delete(slot.getId()));
        doctorService.findAll().forEach(slot -> doctorService.delete(slot.getId()));
        Assertions.assertEquals(0, slotService.findAll().size());
    }
}
