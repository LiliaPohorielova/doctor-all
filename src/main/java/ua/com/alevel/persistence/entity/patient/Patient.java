package ua.com.alevel.persistence.entity.patient;

import ua.com.alevel.persistence.entity.BaseEntity;
import ua.com.alevel.persistence.entity.appointment.PatientAppointment;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "patients")
public class Patient extends BaseEntity {

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "firstname")
    private String firstname;

    private Integer age;
    private Boolean patientVisible;

    @OneToMany(
            mappedBy = "slot",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY
    )
    private Set<PatientAppointment> patientAppointments;

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getPatientVisible() {
        return patientVisible;
    }

    public void setPatientVisible(Boolean patientVisible) {
        this.patientVisible = patientVisible;
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