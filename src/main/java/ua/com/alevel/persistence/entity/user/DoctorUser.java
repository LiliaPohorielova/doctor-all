package ua.com.alevel.persistence.entity.user;

import ua.com.alevel.persistence.entity.doctor.Doctor;
import ua.com.alevel.persistence.type.RoleType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@DiscriminatorValue("DOCTOR")
public class DoctorUser extends User {

    @OneToOne(mappedBy = "doctorUser")
    private Doctor doctor;

    public DoctorUser() {
        super();
        setRoleType(RoleType.ROLE_DOCTOR);
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}
