package ua.com.alevel.web.dto.response.doctor;

import ua.com.alevel.persistence.entity.doctor.Doctor;
import ua.com.alevel.persistence.entity.user.DoctorUser;
import ua.com.alevel.persistence.type.DoctorSpecialization;
import ua.com.alevel.web.dto.response.ResponseDto;

public class DoctorResponseDto extends ResponseDto {

    private String lastname;
    private String firstname;
    private String middleName;
    private DoctorSpecialization specialization;
    private DoctorUser doctorUser;
    private String imgUrl;

    public DoctorResponseDto() { }

    public DoctorResponseDto(Doctor doctor) {
        if (doctor != null) {
            setId(doctor.getId());
            setCreated(doctor.getCreated());
            setUpdated(doctor.getUpdated());
            this.lastname = doctor.getLastname();
            this.firstname = doctor.getFirstname();
            this.middleName = doctor.getMiddleName();
            this.specialization = doctor.getSpecialization();
            this.doctorUser = doctor.getDoctorUser();
            this.imgUrl = doctor.getImageUrl();
        }
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

    public DoctorUser getDoctorUser() {
        return doctorUser;
    }

    public void setDoctorUser(DoctorUser doctorUser) {
        this.doctorUser = doctorUser;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public String toString() {
        return "DoctorResponseDto{" +
                "lastname='" + lastname + '\'' +
                ", firstname='" + firstname + '\'' +
                ", middleName='" + middleName + '\'' +
                ", specialization='" + specialization + '\'' +
                '}';
    }
}