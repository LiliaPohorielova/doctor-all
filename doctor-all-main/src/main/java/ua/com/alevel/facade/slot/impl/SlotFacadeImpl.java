package ua.com.alevel.facade.slot.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.slot.SlotFacade;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.appointment.PatientAppointment;
import ua.com.alevel.persistence.entity.doctor.Doctor;
import ua.com.alevel.persistence.entity.patient.Patient;
import ua.com.alevel.persistence.entity.slot.Slot;
import ua.com.alevel.persistence.type.SlotStatus;
import ua.com.alevel.service.doctor.DoctorService;
import ua.com.alevel.service.slot.SlotService;
import ua.com.alevel.util.WebUtil;
import ua.com.alevel.web.dto.request.data.PageAndSizeData;
import ua.com.alevel.web.dto.request.data.SortData;
import ua.com.alevel.web.dto.request.slot.SlotRequestDto;
import ua.com.alevel.web.dto.response.PageData;
import ua.com.alevel.web.dto.response.slot.SlotResponseDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SlotFacadeImpl implements SlotFacade {
    
    private final SlotService slotService;
    private final DoctorService doctorService;

    public SlotFacadeImpl(SlotService slotService, DoctorService doctorService) {
        this.slotService = slotService;
        this.doctorService = doctorService;
    }

    @Override
    public void create(SlotRequestDto slotRequestDto) {
        Slot slot = new Slot();
        slot.setStatus(slotRequestDto.getStatus());
        slot.setAppDate(slotRequestDto.getAppDate());
        slot.setDoctor(slotRequestDto.getDoctor());
        slot.setStartTime(slotRequestDto.getStartTime());
        slot.setPatientAppointment(new PatientAppointment());
        slotService.create(slot);
    }

    @Override
    public void update(SlotRequestDto slotRequestDto, Long id) {
        Slot slot = slotService.findById(id).get();
        slot.setStatus(slotRequestDto.getStatus());
        slot.setAppDate(slotRequestDto.getAppDate());
        slot.setDoctor(slotRequestDto.getDoctor());
        slot.setStartTime(slotRequestDto.getStartTime());
        slotService.update(slot);
    }

    @Override
    public void updateStatus(Long slotId, SlotStatus slotStatus) {
        Slot slot = slotService.findById(slotId).get();
        slot.setStatus(slotStatus);
        slotService.update(slot);
    }

    @Override
    public void delete(Long id) {
        slotService.delete(id);
    }

    @Override
    public SlotResponseDto findById(Long id) {
        Slot slot = slotService.findById(id).get();
        return new SlotResponseDto(slot);
    }

    @Override
    public PageData<SlotResponseDto> findAll(WebRequest request) {
        PageAndSizeData pageAndSizeData = WebUtil.generatePageAndSizeData(request);
        SortData sortData = WebUtil.generateSortData(request);
        DataTableRequest dataTableRequest = new DataTableRequest();
        dataTableRequest.setSize(pageAndSizeData.getSize());
        dataTableRequest.setPage(pageAndSizeData.getPage());
        dataTableRequest.setSort(sortData.getSort());
        dataTableRequest.setOrder(sortData.getOrder());

        DataTableResponse<Slot> all = slotService.findAll(dataTableRequest);

        List< SlotResponseDto> list = all.getItems().
                stream().
                map( SlotResponseDto::new).
                collect(Collectors.toList());

        PageData< SlotResponseDto> pageData = new PageData<>();
        pageData.setItems(list);
        pageData.setCurrentPage(pageAndSizeData.getPage());
        pageData.setPageSize(pageAndSizeData.getSize());
        pageData.setOrder(sortData.getOrder());
        pageData.setSort(sortData.getSort());
        pageData.setItemsSize(all.getItemsSize());
        pageData.initPaginationState(pageData.getCurrentPage());

        return pageData;
    }

    @Override
    public List<SlotResponseDto> findAll() {
        List<Slot> all = slotService.findAll();
        List<SlotResponseDto> items = all.stream().map(SlotResponseDto::new).collect(Collectors.toList());
        return items;
    }

    @Override
    public List<String> getDatesByDoctor(String doctorId) {
        List<Doctor> all = doctorService.findAll();
        List<String> dates = new ArrayList<>();
        for (Doctor doctor : all) {
            if (doctor.getId().toString().equals(doctorId)) {
                Set<Slot> slots = doctor.getSlots();
                for (Slot slot : slots)
                    if (slot.getStatus().equals(SlotStatus.FREE))
                        dates.add(slot.getAppDate().toString());
                break;
            }
        }
        return dates;
    }

    @Override
    public List<String> getTimeByDate(String doctorId, String date) {
        Optional<Doctor> doctor = doctorService.findById(Long.parseLong(doctorId));
        List<String> times = new ArrayList<>();
        Set<Slot> slots = doctor.get().getSlots();
        for (Slot slot : slots) {
            if (slot.getAppDate().toString().equals(date)) {
                if (slot.getStatus().equals(SlotStatus.FREE))
                    times.add(slot.getStartTime().toString());
            }
        }
        return times;
    }

    @Override
    public Slot getSlot(Doctor doctor, LocalDate date, LocalTime time) {
        return slotService.getSlot(doctor, date, time);
    }

    @Override
    public PatientAppointment bookSlot(Long slotId, Patient patient) {
        return slotService.bookSlot(slotId, patient);
    }

    @Override
    public List<Slot> findSlotByDoctor(SlotStatus slotStatus, Long doctorId) {
        return slotService.findSlotByDoctor(slotStatus, doctorId);
    }
}
