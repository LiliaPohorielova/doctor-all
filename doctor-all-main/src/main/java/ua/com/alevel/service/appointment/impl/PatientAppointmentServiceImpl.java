package ua.com.alevel.service.appointment.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.persistence.crud.CrudRepositoryHelper;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.appointment.PatientAppointment;
import ua.com.alevel.persistence.entity.slot.Slot;
import ua.com.alevel.persistence.repository.patient.PatientAppointmentRepository;
import ua.com.alevel.persistence.type.SlotStatus;
import ua.com.alevel.service.appointment.PatientAppointmentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PatientAppointmentServiceImpl implements PatientAppointmentService {

    private final PatientAppointmentRepository patientAppointmentRepository;
    private final CrudRepositoryHelper<PatientAppointment, PatientAppointmentRepository> patientAppointmentRepositoryHelper;

    public PatientAppointmentServiceImpl(PatientAppointmentRepository patientAppointmentRepository,
                                         CrudRepositoryHelper<PatientAppointment, PatientAppointmentRepository> patientAppointmentRepositoryHelper) {
        this.patientAppointmentRepository = patientAppointmentRepository;
        this.patientAppointmentRepositoryHelper = patientAppointmentRepositoryHelper;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void create(PatientAppointment entity) {
        patientAppointmentRepositoryHelper.create(patientAppointmentRepository, entity);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void update(PatientAppointment entity) {
        patientAppointmentRepositoryHelper.update(patientAppointmentRepository, entity);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void delete(Long id) {
        patientAppointmentRepositoryHelper.delete(patientAppointmentRepository, id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PatientAppointment> findById(Long id) {
        return patientAppointmentRepositoryHelper.findById(patientAppointmentRepository, id);
    }

    @Override
    public List<PatientAppointment> findAll() {
        return patientAppointmentRepository.findAll();
    }

    public List<Slot> findBookedByPatient(Long id, SlotStatus slotStatus) {
        List<PatientAppointment> patientAppointments = patientAppointmentRepository.findByPatientIdAndSlotStatus(id, slotStatus);
        List<Slot> appointments = new ArrayList<>();
        for (PatientAppointment booked : patientAppointments)
            appointments.add(booked.getSlot());
        return appointments;
    }

    @Override
    public DataTableResponse<PatientAppointment> findAll(DataTableRequest request) {
        return patientAppointmentRepositoryHelper.findAll(patientAppointmentRepository, request);
    }

    public void delete(PatientAppointment patientAppointment) {
        patientAppointmentRepository.delete(patientAppointment);
    }

    public PatientAppointment findBySlotId(Long id) {
        return patientAppointmentRepository.findBySlotId(id);
    }
}
