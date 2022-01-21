package ua.com.alevel.facade.appointment.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.appointment.AppointmentFacade;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.appointment.PatientAppointment;
import ua.com.alevel.persistence.entity.slot.Slot;
import ua.com.alevel.persistence.type.SlotStatus;
import ua.com.alevel.service.appointment.PatientAppointmentService;
import ua.com.alevel.service.slot.SlotService;
import ua.com.alevel.util.WebUtil;
import ua.com.alevel.web.dto.request.appointment.AppointmentRequestDto;
import ua.com.alevel.web.dto.request.data.PageAndSizeData;
import ua.com.alevel.web.dto.request.data.SortData;
import ua.com.alevel.web.dto.response.PageData;
import ua.com.alevel.web.dto.response.appointment.AppointmentResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentFacadeImpl implements AppointmentFacade {
    
    private final PatientAppointmentService appointmentService;
    private final SlotService slotService;

    public AppointmentFacadeImpl(PatientAppointmentService appointmentService, SlotService slotService) {
        this.appointmentService = appointmentService;
        this.slotService = slotService;
    }

    @Override
    public void create(AppointmentRequestDto patientAppointmentRequestDto) {
        PatientAppointment patientAppointment = new PatientAppointment();
        patientAppointment.setPatient(patientAppointmentRequestDto.getPatient());
        Slot slot = slotService.getSlot(patientAppointmentRequestDto.getDoctor(),
                patientAppointmentRequestDto.getDate(),
                patientAppointmentRequestDto.getTime());
        patientAppointment.setSlot(slot);
        slot.setStatus(SlotStatus.BOOKED);
        slot.setPatientAppointment(patientAppointment);
        appointmentService.create(patientAppointment);
    }

    @Override
    public void update(AppointmentRequestDto patientAppointmentRequestDto, Long id) {
        PatientAppointment patientAppointment = appointmentService.findById(id).get();
        patientAppointment.setPatient(patientAppointmentRequestDto.getPatient());
        patientAppointment.setSlot(slotService.getSlot(patientAppointmentRequestDto.getDoctor(),
                patientAppointmentRequestDto.getDate(),
                patientAppointmentRequestDto.getTime()));
        appointmentService.update(patientAppointment);
    }

    @Override
    public void delete(Long id) {
        appointmentService.delete(id);
    }

    @Override
    public AppointmentResponseDto findById(Long id) {
        PatientAppointment patientAppointment = appointmentService.findById(id).get();
        return new AppointmentResponseDto(patientAppointment);
    }

    @Override
    public PageData<AppointmentResponseDto> findAll(WebRequest request) {
        PageAndSizeData pageAndSizeData = WebUtil.generatePageAndSizeData(request);
        SortData sortData = WebUtil.generateSortData(request);
        DataTableRequest dataTableRequest = new DataTableRequest();
        dataTableRequest.setSize(pageAndSizeData.getSize());
        dataTableRequest.setPage(pageAndSizeData.getPage());
        dataTableRequest.setSort(sortData.getSort());
        dataTableRequest.setOrder(sortData.getOrder());

        DataTableResponse<PatientAppointment> all = appointmentService.findAll(dataTableRequest);

        List<AppointmentResponseDto> list = all.getItems().
                stream().
                map(AppointmentResponseDto::new).
                collect(Collectors.toList());

        PageData<AppointmentResponseDto> pageData = new PageData<>();
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
    public List<Slot> findBookedByPatient(Long id, SlotStatus slotStatus) {
        return appointmentService.findBookedByPatient(id,slotStatus);
    }
}
