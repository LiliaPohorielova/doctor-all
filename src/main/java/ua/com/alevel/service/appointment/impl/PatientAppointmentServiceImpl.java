package ua.com.alevel.service.appointment.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.entity.appointment.PatientAppointment;
import ua.com.alevel.persistence.entity.slot.Slot;
import ua.com.alevel.persistence.repository.patient.PatientAppointmentRepository;
import ua.com.alevel.persistence.type.SlotStatus;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PatientAppointmentServiceImpl {
    @Autowired
    private PatientAppointmentRepository patientAppointmentRepository;

    public List<Slot> findBookedByPatient(Long id) {
        List<PatientAppointment> patientAppointments = patientAppointmentRepository.findByPatientIdAndSlotStatus(id, SlotStatus.BOOKED);

        List<Slot> appointments = new ArrayList<>();

        for (PatientAppointment booked : patientAppointments) {
            appointments.add(booked.getSlot());

        }

        return appointments;
    }

    public List<Slot> findPastByPatient(Long id) {
        List<PatientAppointment> patientAppointments = patientAppointmentRepository.findByPatientIdAndSlotStatus(id, SlotStatus.PAST);


        List<Slot> appointments = new ArrayList<>();

        for (PatientAppointment booked : patientAppointments) {

            appointments.add(booked.getSlot());

        }

        return appointments;
    }
    
    public PatientAppointment find(Long id) {
        return patientAppointmentRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public void delete(PatientAppointment patientAppointment) {
        patientAppointmentRepository.delete(patientAppointment);
    }

    public PatientAppointment findByAppointmentId(Long id) {
        return patientAppointmentRepository.findBySlotId(id);
    }
}
