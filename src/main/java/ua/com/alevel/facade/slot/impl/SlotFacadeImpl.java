package ua.com.alevel.facade.slot.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.slot.SlotFacade;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.slot.Slot;
import ua.com.alevel.service.slot.SlotService;
import ua.com.alevel.util.WebUtil;
import ua.com.alevel.web.dto.request.data.PageAndSizeData;
import ua.com.alevel.web.dto.request.data.SortData;
import ua.com.alevel.web.dto.request.slot.SlotRequestDto;
import ua.com.alevel.web.dto.response.PageData;
import ua.com.alevel.web.dto.response.slot.SlotResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SlotFacadeImpl implements SlotFacade {
    
    private final SlotService slotService;

    public SlotFacadeImpl(ua.com.alevel.service.slot.SlotService slotService) {
        this.slotService = slotService;
    }

    @Override
    public void create(SlotRequestDto slotRequestDto) {
        Slot slot = new Slot();
        slot.setStatus(slotRequestDto.getStatus());
        slot.setAppDate(slotRequestDto.getAppDate());
        slot.setDoctor(slotRequestDto.getDoctor());
        slot.setStartTime(slotRequestDto.getStartTime());
        slot.setPatientAppointment(slotRequestDto.getPatientAppointment());
        slotService.create(slot);
    }

    @Override
    public void update(SlotRequestDto slotRequestDto, Long id) {
        Slot slot = slotService.findById(id).get();
        slot.setStatus(slotRequestDto.getStatus());
        slot.setAppDate(slotRequestDto.getAppDate());
        slot.setDoctor(slotRequestDto.getDoctor());
        slot.setStartTime(slotRequestDto.getStartTime());
        slot.setPatientAppointment(slotRequestDto.getPatientAppointment());
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
}
