package ua.com.alevel.persistence.entity.doctor;

import ua.com.alevel.persistence.entity.BaseEntity;
import ua.com.alevel.persistence.entity.department.DoctorsDepartment;
import ua.com.alevel.persistence.entity.slot.Slot;
import ua.com.alevel.persistence.type.DoctorSpecialization;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "doctors")
public class Doctor extends BaseEntity {

    private String lastname;
    private String firstname;
    private String middleName;
    private Boolean doctorVisible;

    @Enumerated(EnumType.STRING)
    @Column(name = "specialization")
    private DoctorSpecialization specialization;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private DoctorsDepartment department;

    @OneToMany(mappedBy = "doctor",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY
    )
    private Set<Slot> appointments;

    public Doctor() {
        super();
        doctorVisible = true;
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

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public DoctorSpecialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(DoctorSpecialization specialization) {
        this.specialization = specialization;
    }

    public Boolean getDoctorVisible() {
        return doctorVisible;
    }

    public void setDoctorVisible(Boolean doctorVisible) {
        this.doctorVisible = doctorVisible;
    }

    public DoctorsDepartment getDepartment() {
        return department;
    }

    public void setDepartment(DoctorsDepartment department) {
        this.department = department;
    }

    public Set<Slot> getAppointments() {
        return appointments;
    }

    public void setAppointments(Set<Slot> appointments) {
        this.appointments = appointments;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "lastname='" + lastname + '\'' +
                ", firstname='" + firstname + '\'' +
                ", middleName='" + middleName + '\'' +
                ", specialization=" + specialization +
                '}';
    }
}
