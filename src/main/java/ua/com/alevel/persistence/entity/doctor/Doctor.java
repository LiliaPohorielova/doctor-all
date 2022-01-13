package ua.com.alevel.persistence.entity.doctor;

import ua.com.alevel.persistence.entity.BaseEntity;
import ua.com.alevel.persistence.entity.department.DoctorsDepartment;
import ua.com.alevel.persistence.entity.patient.Patient;
import ua.com.alevel.persistence.entity.slot.Slot;
import ua.com.alevel.persistence.entity.user.DoctorUser;
import ua.com.alevel.persistence.entity.user.PatientUser;
import ua.com.alevel.persistence.type.DoctorSpecialization;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "doctors")
public class Doctor extends BaseEntity {

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "middle_name")
    private String middleName;

    @Enumerated(EnumType.STRING)
    @Column(name = "specialization")
    private DoctorSpecialization specialization;

    @Column(name = "image_url")
    private String imageUrl;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_user_id", referencedColumnName = "id")
    private DoctorUser doctorUser;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private DoctorsDepartment department;

    @OneToMany(mappedBy = "doctor",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY
    )
    private Set<Slot> appointments;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "declaration",
            joinColumns = @JoinColumn(name = "doctor"),
            inverseJoinColumns = @JoinColumn(name = "patient"))
    private Set<Patient> patients;

    public Doctor() {
        super();
        this.patients = new HashSet<>();
        this.appointments = new HashSet<>();
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Set<Patient> getPatients() {
        return patients;
    }

    public void setPatients(Set<Patient> patients) {
        this.patients = patients;
    }

    public DoctorUser getDoctorUser() {
        return doctorUser;
    }

    public void setDoctorUser(DoctorUser doctorUser) {
        this.doctorUser = doctorUser;
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
                ", doctorUser=" + doctorUser.getId() +
                '}';
    }
}
