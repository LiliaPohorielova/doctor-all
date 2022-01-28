package ua.com.alevel.persistence.entity.patient;

import org.springframework.format.annotation.DateTimeFormat;
import ua.com.alevel.persistence.entity.BaseEntity;
import ua.com.alevel.persistence.entity.appointment.PatientAppointment;
import ua.com.alevel.persistence.entity.doctor.Doctor;
import ua.com.alevel.persistence.entity.user.PatientUser;
import ua.com.alevel.persistence.entity.vaccination.Vaccination;
import ua.com.alevel.persistence.type.Gender;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "patients")
public class Patient extends BaseEntity {

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "firstname")
    private String firstname;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "date_of_birth")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @Column(name = "image_url")
    private String imageUrl;

    @OneToMany(
            mappedBy = "slot",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY
    )
    private Set<PatientAppointment> patientAppointments;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "patients_vaccinations",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "vaccination_id"))
    private Set<Vaccination> patientVaccinations;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_user_id", referencedColumnName = "id")
    private PatientUser patientUser;

    @ManyToMany(mappedBy = "patients", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private Set<Doctor> doctors;

    public Set<PatientAppointment> getPatientAppointments() {
        return patientAppointments;
    }

    public Patient() {
        this.dateOfBirth = new Date();
        this.doctors = new HashSet<>();
        this.patientAppointments = new HashSet<>();
    }

    public void setPatientAppointments(Set<PatientAppointment> patientAppointments) {
        this.patientAppointments = patientAppointments;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getDateOfBirthChangeFormat() {
        return new SimpleDateFormat("dd/MM/yyyy").format(dateOfBirth);
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Set<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(Set<Doctor> doctors) {
        this.doctors = doctors;
    }

    public PatientUser getPatientUser() {
        return patientUser;
    }

    public void setPatientUser(PatientUser patientUser) {
        this.patientUser = patientUser;
    }

    public Set<Vaccination> getPatientVaccinations() {
        return patientVaccinations;
    }

    public void setPatientVaccinations(Set<Vaccination> patientVaccinations) {
        this.patientVaccinations = patientVaccinations;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public void addPatientAppointment(PatientAppointment patientAppointment) {
        patientAppointments.add(patientAppointment);
        patientAppointment.setPatient(this);
    }

    public void addVaccination(Vaccination vaccination) {
        patientVaccinations.add(vaccination);
        vaccination.getPatients().add(this);
    }
}