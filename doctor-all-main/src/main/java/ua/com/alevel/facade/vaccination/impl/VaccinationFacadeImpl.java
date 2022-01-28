package ua.com.alevel.facade.vaccination.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.vaccination.VaccinationFacade;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.vaccination.Vaccination;
import ua.com.alevel.service.vaccination.VaccinationService;
import ua.com.alevel.util.WebUtil;
import ua.com.alevel.web.dto.request.data.PageAndSizeData;
import ua.com.alevel.web.dto.request.data.SortData;
import ua.com.alevel.web.dto.request.vaccination.VaccinationRequestDto;
import ua.com.alevel.web.dto.response.PageData;
import ua.com.alevel.web.dto.response.vaccination.VaccinationResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VaccinationFacadeImpl implements VaccinationFacade {

    private final VaccinationService vaccinationService;

    public VaccinationFacadeImpl(VaccinationService vaccinationService) {
        this.vaccinationService = vaccinationService;
    }

    @Override
    public void create(VaccinationRequestDto vaccinationRequestDto) {
        Vaccination vaccination = new Vaccination();
        vaccination.setVaccinationId(vaccinationRequestDto.getVaccinationId());
        vaccination.setManufacturer(vaccinationRequestDto.getManufacturer());
        vaccination.setMethodOfAdministration(vaccinationRequestDto.getMethodOfAdministration());
        vaccination.setName(vaccinationRequestDto.getName());
        vaccination.setImageUrl(vaccinationRequestDto.getImageUrl());
        vaccination.setQuantity(vaccinationRequestDto.getQuantity());
        vaccinationService.create(vaccination);
    }

    @Override
    public void update(VaccinationRequestDto vaccinationRequestDto, Long id) {
        Vaccination vaccination = vaccinationService.findById(id).get();
        vaccination.setVaccinationId(vaccinationRequestDto.getVaccinationId());
        vaccination.setManufacturer(vaccinationRequestDto.getManufacturer());
        vaccination.setMethodOfAdministration(vaccinationRequestDto.getMethodOfAdministration());
        vaccination.setName(vaccinationRequestDto.getName());
        vaccination.setImageUrl(vaccinationRequestDto.getImageUrl());
        vaccination.setQuantity(vaccinationRequestDto.getQuantity());
        vaccinationService.update(vaccination);
    }

    @Override
    public void delete(Long id) {
        vaccinationService.delete(id);
    }

    @Override
    public VaccinationResponseDto findById(Long id) {
        Vaccination vaccination = vaccinationService.findById(id).get();
        return new VaccinationResponseDto(vaccination);
    }

    @Override
    public PageData<VaccinationResponseDto> findAll(WebRequest request) {
        PageAndSizeData pageAndSizeData = WebUtil.generatePageAndSizeData(request);
        SortData sortData = WebUtil.generateSortData(request);
        DataTableRequest dataTableRequest = new DataTableRequest();
        dataTableRequest.setSize(pageAndSizeData.getSize());
        dataTableRequest.setPage(pageAndSizeData.getPage());
        dataTableRequest.setSort(sortData.getSort());
        dataTableRequest.setOrder(sortData.getOrder());

        DataTableResponse<Vaccination> all = vaccinationService.findAll(dataTableRequest);

        List<VaccinationResponseDto> list = all.getItems().
                stream().
                map(VaccinationResponseDto::new).
                collect(Collectors.toList());

        PageData<VaccinationResponseDto> pageData = new PageData<>();
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
    public List<VaccinationResponseDto> findAll() {
        List<Vaccination> all = vaccinationService.findAll();
        List<VaccinationResponseDto> items = all.stream().map(VaccinationResponseDto::new).collect(Collectors.toList());
        return items;
    }
}
