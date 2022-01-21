package ua.com.alevel.web.dto.request.doctor;

import ua.com.alevel.persistence.type.DoctorSpecialization;
import ua.com.alevel.web.dto.request.auth.AuthDto;

public class DoctorRequestDto extends AuthDto {

    private String middleName;
    private DoctorSpecialization specialization;

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
}
