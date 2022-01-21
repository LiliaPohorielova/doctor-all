package ua.com.alevel.web.dto.response.patient;

import ua.com.alevel.persistence.entity.patient.Patient;
import ua.com.alevel.persistence.entity.user.PatientUser;
import ua.com.alevel.persistence.type.Gender;
import ua.com.alevel.web.dto.response.ResponseDto;

import java.util.Date;

public class PatientResponseDto extends ResponseDto {

    private String firstname;
    private String lastname;
    private Gender gender;
    private String phoneNumber;
    private Date dateOfBirth;
    private PatientUser patientUser;

    public PatientResponseDto() { }

    public PatientResponseDto(Patient patient) {
        if (patient != null) {
            setId(patient.getId());
            setCreated(patient.getCreated());
            setUpdated(patient.getUpdated());
            this.firstname = patient.getFirstname();
            this.lastname = patient.getLastname();
            this.gender = patient.getGender();
            this.phoneNumber = patient.getPhoneNumber();
            this.dateOfBirth = patient.getDateOfBirth();
            this.patientUser = patient.getPatientUser();
        }
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public Gender getGender() {
        return gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public PatientUser getPatientUser() {
        return patientUser;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setPatientUser(PatientUser patientUser) {
        this.patientUser = patientUser;
    }
}