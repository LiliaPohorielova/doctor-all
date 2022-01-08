package ua.com.alevel.persistence.entity.patient;

import ua.com.alevel.persistence.entity.BaseEntity;
import ua.com.alevel.persistence.entity.appointment.PatientAppointment;
import ua.com.alevel.persistence.entity.doctor.Doctor;
import ua.com.alevel.persistence.entity.user.PatientUser;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "patients")
public class Patient extends BaseEntity {

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    private Boolean patientVisible;

    @OneToMany(
            mappedBy = "slot",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY
    )
    private Set<PatientAppointment> patientAppointments;

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_user_id", referencedColumnName = "id")
    private PatientUser patientUser;

    public Set<PatientAppointment> getPatientAppointments() {
        return patientAppointments;
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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Boolean getPatientVisible() {
        return patientVisible;
    }

    public void setPatientVisible(Boolean patientVisible) {
        this.patientVisible = patientVisible;
    }

    public PatientUser getPatientUser() {
        return patientUser;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}