package ua.com.alevel.web.dto.response.appointment;

import ua.com.alevel.persistence.entity.appointment.PatientAppointment;
import ua.com.alevel.persistence.entity.patient.Patient;
import ua.com.alevel.persistence.entity.slot.Slot;
import ua.com.alevel.web.dto.response.ResponseDto;

public class AppointmentResponseDto extends ResponseDto {

    private Slot slot;
    private Patient patient;

    public AppointmentResponseDto() { }

    public AppointmentResponseDto(PatientAppointment patientAppointment) {
        if (patientAppointment != null) {
            setId(patientAppointment.getId());
            setCreated(patientAppointment.getCreated());
            setUpdated(patientAppointment.getUpdated());
            this.slot = patientAppointment.getSlot();
            this.patient = patientAppointment.getPatient();
        }
    }

    public Slot getSlot() {
        return slot;
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
