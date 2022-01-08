package ua.com.alevel.persistence.entity.user;

import ua.com.alevel.persistence.entity.doctor.Doctor;
import ua.com.alevel.persistence.entity.patient.Patient;
import ua.com.alevel.persistence.listener.FullNameGenerationListener;
import ua.com.alevel.persistence.type.RoleType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.OneToOne;
import java.util.Set;

@Entity
@DiscriminatorValue("PATIENT")
/*@EntityListeners({
        FullNameGenerationListener.class,
        AgeByBirthDayGenerationListener.class
})*/
public class PatientUser extends User {

    @OneToOne(mappedBy = "patientUser")
    private Patient patient;

    public PatientUser() {
        super();
        setRoleType(RoleType.ROLE_PATIENT);
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
