package ua.com.alevel;

import org.springframework.beans.factory.annotation.Autowired;
import ua.com.alevel.persistence.entity.BaseEntity;
import ua.com.alevel.persistence.entity.appointment.PatientAppointment;
import ua.com.alevel.persistence.entity.department.DoctorsDepartment;
import ua.com.alevel.persistence.entity.doctor.Doctor;
import ua.com.alevel.persistence.entity.patient.Patient;
import ua.com.alevel.persistence.entity.slot.Slot;
import ua.com.alevel.persistence.entity.user.DoctorUser;
import ua.com.alevel.persistence.entity.user.PatientUser;
import ua.com.alevel.persistence.entity.vaccination.Vaccination;
import ua.com.alevel.persistence.type.DoctorSpecialization;
import ua.com.alevel.persistence.type.RoleType;
import ua.com.alevel.persistence.type.SlotStatus;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class GenerationUtil {

    public static final String NAME_OF_PATIENT = "patient_test";
    public static final String NAME_OF_DOCTOR = "doctor_test";
    public static final String NAME_OF_DEPARTMENT = "department_test";
    public static final String NAME_OF_VACCINATION = "vaccination_test";
    public static final String NAME_OF_MANUFACTURER = "manufacturer_test";
    public static final String PASSWORD = "rootroot";

    private GenerationUtil() { }

    public static PatientUser generatePatientUser(String email, String password) {
        PatientUser patientUser = new PatientUser();
        patientUser.setRoleType(RoleType.ROLE_PATIENT);
        patientUser.setEmail(email);
        patientUser.setPassword(password);
        return patientUser;
    }

    public static Patient generatePatient(String firstname, String lastname) {
        Patient patient = new Patient();
        patient.setFirstname(firstname);
        patient.setLastname(lastname);
        patient.setDateOfBirth(new Date());
        return patient;
    }

    public static DoctorUser generateDoctorUser(String email, String password) {
        DoctorUser doctorUser = new DoctorUser();
        doctorUser.setRoleType(RoleType.ROLE_DOCTOR);
        doctorUser.setEmail(email);
        doctorUser.setPassword(password);
        return doctorUser;
    }

    public static Doctor generateDoctor(String firstname, String lastname, String middleName, String spec) {
        Doctor doctor = new Doctor();
        doctor.setFirstname(firstname);
        doctor.setLastname(lastname);
        doctor.setMiddleName(middleName);
        doctor.setSpecialization(DoctorSpecialization.valueOf(spec));
        return doctor;
    }

    public static Slot generateSlot(Doctor doctor) {
        Slot slot = new Slot();
        slot.setDoctor(doctor);
        slot.setStatus(SlotStatus.FREE);
        slot.setAppDate(LocalDate.now());
        slot.setStartTime(LocalTime.MIDNIGHT);
        return slot;
    }

    public static PatientAppointment generatePatientAppointment(Patient patient, Slot slot) {
        PatientAppointment patientAppointment = new PatientAppointment();
        patientAppointment.setPatient(patient);
        patientAppointment.setSlot(slot);
        return patientAppointment;
    }

    public static DoctorsDepartment generateDepartment(String name) {
        DoctorsDepartment doctorsDepartment = new DoctorsDepartment();
        doctorsDepartment.setDepartmentName(name);
        return doctorsDepartment;
    }

    public static Vaccination generateVaccination(String name, String manufacturer, Integer quantity) {
        Vaccination vaccination = new Vaccination();
        vaccination.setName(name);
        vaccination.setManufacturer(manufacturer);
        vaccination.setQuantity(quantity);
        return vaccination;
    }
}
