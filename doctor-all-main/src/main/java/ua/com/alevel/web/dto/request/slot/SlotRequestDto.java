package ua.com.alevel.web.dto.request.slot;

import ua.com.alevel.persistence.entity.appointment.PatientAppointment;
import ua.com.alevel.persistence.entity.doctor.Doctor;
import ua.com.alevel.persistence.type.SlotStatus;
import ua.com.alevel.web.dto.request.RequestDto;

import java.time.LocalDate;
import java.time.LocalTime;

public class SlotRequestDto extends RequestDto {

    private Doctor doctor;
    private LocalDate appDate;
    private LocalTime startTime;
    private SlotStatus status;
    private PatientAppointment patientAppointment;

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
