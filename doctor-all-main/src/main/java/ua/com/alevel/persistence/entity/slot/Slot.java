package ua.com.alevel.persistence.entity.slot;

import ua.com.alevel.persistence.entity.BaseEntity;
import ua.com.alevel.persistence.entity.appointment.PatientAppointment;
import ua.com.alevel.persistence.entity.doctor.Doctor;
import ua.com.alevel.persistence.type.SlotStatus;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "slots")
public class Slot extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    private Doctor doctor;

    @Column(name = "app_date")
    private LocalDate appDate;

    @Column(name = "start_time")
    private LocalTime startTime;
    private SlotStatus status;

    @OneToOne(mappedBy = "slot")
    private PatientAppointment patientAppointment;

    public Slot() {
        super();
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
        doctor.getSlots().add(this);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Slot slot = (Slot) o;
        return Objects.equals(doctor, slot.doctor) && Objects.equals(appDate, slot.appDate) && Objects.equals(startTime, slot.startTime) && status == slot.status && Objects.equals(patientAppointment, slot.patientAppointment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), doctor, appDate, startTime, status, patientAppointment);
    }
}
