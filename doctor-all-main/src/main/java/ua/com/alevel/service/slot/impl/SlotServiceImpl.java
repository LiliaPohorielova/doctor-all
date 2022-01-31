package ua.com.alevel.service.slot.impl;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.logs.LogLevel;
import ua.com.alevel.logs.LogService;
import ua.com.alevel.persistence.crud.CrudRepositoryHelper;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.appointment.PatientAppointment;
import ua.com.alevel.persistence.entity.doctor.Doctor;
import ua.com.alevel.persistence.entity.patient.Patient;
import ua.com.alevel.persistence.entity.slot.Slot;
import ua.com.alevel.persistence.repository.doctor.SlotRepository;
import ua.com.alevel.persistence.repository.patient.PatientAppointmentRepository;
import ua.com.alevel.persistence.repository.patient.PatientRepository;
import ua.com.alevel.persistence.type.SlotStatus;
import ua.com.alevel.service.slot.SlotService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class SlotServiceImpl implements SlotService {

    private final LogService logService;
    private final SlotRepository slotRepository;
    private final CrudRepositoryHelper<Slot, SlotRepository> slotRepositoryHelper;
    private final PatientAppointmentRepository patientAppointmentRepository;
    private final PatientRepository patientRepository;

    public SlotServiceImpl(LogService logService, SlotRepository slotRepository,
                           CrudRepositoryHelper<Slot, SlotRepository> slotRepositoryHelper,
                           PatientAppointmentRepository patientAppointmentRepository,
                           PatientRepository patientRepository) {
        this.logService = logService;
        this.slotRepository = slotRepository;
        this.slotRepositoryHelper = slotRepositoryHelper;
        this.patientAppointmentRepository = patientAppointmentRepository;
        this.patientRepository = patientRepository;
    }

    public Slot getSlot(Doctor doctor, LocalDate date, LocalTime time) {
        return slotRepository.findByDoctorAndAppDateAndStartTime(doctor, date, time);
    }

    public void updateStatus(Long slotId, SlotStatus status) {
        Optional<Slot> appointment = slotRepository.findById(slotId);
        if (appointment.isPresent()) {
            if (appointment.get().getStatus() != null) {
                appointment.get().setStatus(status);
            }
            slotRepository.save(appointment.get());
        }
    }

    public PatientAppointment bookSlot(Long slotId, Long patientId) {
        Optional<Slot> slot = slotRepository.findById(slotId);
        Optional<Patient> patient = patientRepository.findById(patientId);
        updateStatus(slotId, SlotStatus.BOOKED);
        PatientAppointment bookedSlot = new PatientAppointment();
        bookedSlot.setSlot(slot.get());
        bookedSlot.setPatient(patient.get());
        patientAppointmentRepository.save(bookedSlot);
        return bookedSlot;
    }

    public PatientAppointment checkSlot(Long slotId, Patient patient) {
        Optional<Slot> slot = slotRepository.findById(slotId);
        updateStatus(slotId, SlotStatus.BOOKED);
        PatientAppointment bookedSlot = new PatientAppointment();
        bookedSlot.setSlot(slot.get());
        bookedSlot.setPatient(patient);
        patientAppointmentRepository.save(bookedSlot);
        return bookedSlot;
    }

    //checking for past appointments every 5 minutes since the application starts
    @Scheduled(fixedRate = 300000)
    public void updatePastSlots() {
        List<Slot> appointments = slotRepository.findAll();
        for (Slot appointment : appointments) {
            if (LocalDate.now().isEqual(appointment.getAppDate()) &&
                    LocalTime.now().isAfter(appointment.getStartTime())) {
                appointment.setStatus(SlotStatus.PAST);
                slotRepository.save(appointment);
            }
        }
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void create(Slot entity) {
        slotRepositoryHelper.create(slotRepository, entity);
        logService.log(LogLevel.INFO, "slot " + entity.getId() + " was successfully created");
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void update(Slot entity) {
        slotRepositoryHelper.update(slotRepository, entity);
        logService.log(LogLevel.INFO, "slot " + entity.getId() + " was successfully updated");
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void delete(Long id) {
        Slot slot = slotRepositoryHelper.findById(slotRepository, id).get();
        if (slot.getStatus() == SlotStatus.FREE)
            slotRepositoryHelper.delete(slotRepository, id);
        else if (slot.getStatus() == SlotStatus.CANCELLED) {
            patientAppointmentRepository.delete(slot.getPatientAppointment());
            slotRepositoryHelper.delete(slotRepository, id);
        }
        else slot.setStatus(SlotStatus.CANCELLED);
        logService.log(LogLevel.WARN, "slot " + id + " was canceled");
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Slot> findById(Long id) {
        return slotRepositoryHelper.findById(slotRepository, id);
    }

    @Override
    @Transactional(readOnly = true)
    public DataTableResponse<Slot> findAll(DataTableRequest request) {
        return slotRepositoryHelper.findAll(slotRepository, request);
    }

    @Override
    public List<Slot> findAll() {
        return slotRepository.findAll();
    }

    @Override
    public List<Slot> findSlotByDoctor(SlotStatus slotStatus, Long doctorId) {
        return slotRepository.findByStatusAndDoctorId(slotStatus, doctorId);
    }
}
