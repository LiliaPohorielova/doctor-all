package ua.com.alevel.web.dto.request.patient;

import ua.com.alevel.persistence.type.Gender;
import ua.com.alevel.web.dto.request.auth.AuthDto;

import java.util.Date;

public class PatientRequestDto extends AuthDto {

    private Gender gender;
    private String phoneNumber;
    private Date dateOfBirth;

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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
