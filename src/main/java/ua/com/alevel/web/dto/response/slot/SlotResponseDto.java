package ua.com.alevel.web.dto.response.slot;

import ua.com.alevel.persistence.entity.appointment.PatientAppointment;
import ua.com.alevel.persistence.entity.doctor.Doctor;
import ua.com.alevel.persistence.entity.slot.Slot;
import ua.com.alevel.persistence.type.SlotStatus;
import ua.com.alevel.web.dto.response.ResponseDto;

import java.time.LocalDate;
import java.time.LocalTime;

public class SlotResponseDto extends ResponseDto {

    private Doctor doctor;
    private LocalDate appDate;
    private LocalTime startTime;
    private SlotStatus status;
    private PatientAppointment patientAppointment;

    public SlotResponseDto() { }

    public SlotResponseDto(Slot slot) {
        this.doctor = slot.getDoctor();
        this.appDate = slot.getAppDate();
        this.patientAppointment = slot.getPatientAppointment();
        this.startTime = slot.getStartTime();
        this.status = slot.getStatus();
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public LocalDate getAppDate() {
        return appDate;
    }

    public void setAppDate(LocalDate appDate) {
        this.appDate = appDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public SlotStatus getStatus() {
        return status;
    }

    public void setStatus(SlotStatus status) {
        this.status = status;
    }

    public PatientAppointment getPatientAppointment() {
        return patientAppointment;
    }

    public void setPatientAppointment(PatientAppointment patientAppointment) {
        this.patientAppointment = patientAppointment;
    }
}
