package ua.com.alevel.web.dto.response;

import ua.com.alevel.persistence.entity.patient.Patient;

public class PatientResponseDto extends ResponseDto {

    private String firstname;
    private String lastname;

    public PatientResponseDto() { }

    public PatientResponseDto(Patient patient) {
        if (patient != null) {
            setId(patient.getId());
            setCreated(patient.getCreated());
            setUpdated(patient.getUpdated());
            this.firstname = patient.getFirstname();
            this.lastname = patient.getLastname();
        }
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }
}