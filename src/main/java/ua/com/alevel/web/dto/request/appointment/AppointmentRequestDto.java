package ua.com.alevel.web.dto.request.appointment;

import org.springframework.format.annotation.DateTimeFormat;
import ua.com.alevel.persistence.entity.doctor.Doctor;
import ua.com.alevel.web.dto.request.RequestDto;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentRequestDto extends RequestDto {

    private Doctor doctor;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private LocalTime time;

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
