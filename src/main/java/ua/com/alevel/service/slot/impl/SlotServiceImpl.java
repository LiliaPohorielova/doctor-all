package ua.com.alevel.service.slot.impl;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.persistence.crud.CrudRepositoryHelper;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.appointment.PatientAppointment;
import ua.com.alevel.persistence.entity.doctor.Doctor;
import ua.com.alevel.persistence.entity.patient.Patient;
import ua.com.alevel.persistence.entity.slot.Slot;
import ua.com.alevel.persistence.repository.doctor.SlotRepository;
import ua.com.alevel.persistence.repository.patient.PatientAppointmentRepository;
import ua.com.alevel.persistence.type.SlotStatus;
import ua.com.alevel.service.slot.SlotService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SlotServiceImpl implements SlotService {

    //TODO: FACADES!!!!!!!!!!!!
    
    private final SlotRepository slotRepository;
    private final CrudRepositoryHelper<Slot, SlotRepository> slotRepositoryHelper;
    private final PatientAppointmentRepository patientAppointmentRepository;

    public SlotServiceImpl(SlotRepository slotRepository, CrudRepositoryHelper<Slot, SlotRepository> slotRepositoryHelper, PatientAppointmentRepository patientAppointmentRepository) {
        this.slotRepository = slotRepository;
        this.slotRepositoryHelper = slotRepositoryHelper;
        this.patientAppointmentRepository = patientAppointmentRepository;
    }
    
    public List<Slot> getSlot(Doctor doctor, LocalDate date) {
        List<Slot> appointmentList = slotRepository.findByDoctorAndAppDate(doctor, date);

        List<Slot> freeSlots = new ArrayList<>();
        for (Slot appointment: appointmentList) {
            if (appointment.getStatus().equals(SlotStatus.FREE))
                freeSlots.add(appointment);
        }
        return freeSlots;
    }
    
    public Slot updateStatus(Long slotId, SlotStatus status) {
        Optional<Slot> appointment = slotRepository.findById(slotId);
        if(appointment.isPresent()){
            if(appointment.get().getStatus() != null){
                appointment.get().setStatus(status);
            }
            return slotRepository.save(appointment.get());
        }
        return null;
    }
    
    public PatientAppointment bookSlot(Long slotId, Patient patient) {
        Optional<Slot> appointment = slotRepository.findById(slotId);
        updateStatus(slotId, SlotStatus.BOOKED);
        PatientAppointment bookedSlot = new PatientAppointment();
        bookedSlot.setSlot(appointment.get());
        bookedSlot.setPatient(patient);

        return patientAppointmentRepository.save(bookedSlot);
    }

    //chceking for past appointments every 5 minutes since the application starts
    @Scheduled(fixedRate = 300000)
    public void updatePastSlots() {
        List<Slot> appointments = slotRepository.findAll();
        for (Slot appointment:appointments) {
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
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void update(Slot entity) {
        slotRepositoryHelper.update(slotRepository, entity);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void delete(Long id) {
        slotRepositoryHelper.delete(slotRepository, id);
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
}
