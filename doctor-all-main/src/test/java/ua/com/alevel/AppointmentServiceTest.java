package ua.com.alevel;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import ua.com.alevel.config.ElasticsearchConfig;
import ua.com.alevel.config.HibernateConfig;
import ua.com.alevel.config.ScheduledConfig;
import ua.com.alevel.config.WebSecurityConfig;
import ua.com.alevel.persistence.entity.appointment.PatientAppointment;
import ua.com.alevel.persistence.entity.doctor.Doctor;
import ua.com.alevel.persistence.entity.patient.Patient;
import ua.com.alevel.persistence.entity.slot.Slot;
import ua.com.alevel.persistence.type.DoctorSpecialization;
import ua.com.alevel.service.appointment.PatientAppointmentService;
import ua.com.alevel.service.doctor.DoctorService;
import ua.com.alevel.service.patient.PatientService;
import ua.com.alevel.service.slot.SlotService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ua.com.alevel.GenerationUtil.NAME_OF_DOCTOR;
import static ua.com.alevel.GenerationUtil.NAME_OF_PATIENT;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@ContextConfiguration(classes = {ElasticsearchConfig.class, HibernateConfig.class, ScheduledConfig.class, WebSecurityConfig.class} )
public class AppointmentServiceTest {

    @Autowired
    private SlotService slotService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientAppointmentService appointmentService;


    private final static int COUNT_OF_APPS = 5;

    @BeforeAll
    void init() {
        for (int i = 0; i < COUNT_OF_APPS; i++) {
            Doctor doctor = GenerationUtil.generateDoctor(NAME_OF_DOCTOR + i, NAME_OF_DOCTOR + i,NAME_OF_DOCTOR + i, DoctorSpecialization.DENTIST.toString());
            doctorService.create(doctor);
            Slot slot = GenerationUtil.generateSlot(doctor);
            slotService.create(slot);
            Patient patient = GenerationUtil.generatePatient(NAME_OF_PATIENT + i, NAME_OF_PATIENT + i);
            patientService.create(patient);
            PatientAppointment patientAppointment = GenerationUtil.generatePatientAppointment(patient, slot);
            appointmentService.create(patientAppointment);
        }

        Assertions.assertEquals(COUNT_OF_APPS, appointmentService.findAll().size());
    }

    @Order(1)
    @Test
    void shouldBeCreateAppWhenAllFieldsIsNotEmpty() {
        Doctor doctor = GenerationUtil.generateDoctor(NAME_OF_DOCTOR, NAME_OF_DOCTOR,NAME_OF_DOCTOR, DoctorSpecialization.DENTIST.toString());
        doctorService.create(doctor);
        Slot slot = GenerationUtil.generateSlot(doctor);
        slotService.create(slot);
        Patient patient = GenerationUtil.generatePatient(NAME_OF_PATIENT, NAME_OF_PATIENT);
        patientService.create(patient);
        PatientAppointment patientAppointment = GenerationUtil.generatePatientAppointment(patient, slot);
        appointmentService.create(patientAppointment);
        Assertions.assertEquals(COUNT_OF_APPS + 1, appointmentService.findAll().size());
    }

    @Order(2)
    @Test
    void shouldBeReturnExceptionWhenPatientIsNull() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            PatientAppointment patientAppointment = GenerationUtil.generatePatientAppointment(null, null);
            appointmentService.create(patientAppointment);
        });
        Assertions.assertEquals(COUNT_OF_APPS + 1, appointmentService.findAll().size());
    }

    @Order(3)
    @Test
    void shouldBeDeleteApp() {
        Long id = appointmentService.findAll().stream().findFirst().get().getId();
        appointmentService.delete(id);
        Assertions.assertEquals(COUNT_OF_APPS, appointmentService.findAll().size());
    }

    @Order(4)
    @Test
    void shouldBeUpdateApp() {
        Patient patient = GenerationUtil.generatePatient("Testing", "Testing");
        patientService.create(patient);
        PatientAppointment appointment = appointmentService.findAll().stream().findFirst().get();
        appointment.setPatient(patient);
        appointmentService.update(appointment);
        Assertions.assertEquals("Testing", appointmentService.findById(appointment.getId()).get().getPatient().getFirstname());
    }

    @Order(5)
    @Test
    void shouldBeDeleteAllApps() {
        appointmentService.findAll().forEach(slot -> appointmentService.delete(slot.getId()));
        patientService.findAll().forEach(slot -> patientService.delete(slot.getId()));
        slotService.findAll().forEach(slot -> slotService.delete(slot.getId()));
        doctorService.findAll().forEach(slot -> doctorService.delete(slot.getId()));
        Assertions.assertEquals(0, appointmentService.findAll().size());
    }
}
