package ua.com.alevel.facade.doctor.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.doctor.DoctorFacade;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.doctor.Doctor;
import ua.com.alevel.persistence.entity.user.DoctorUser;
import ua.com.alevel.service.doctor.DoctorService;
import ua.com.alevel.service.doctor.DoctorUserService;
import ua.com.alevel.util.WebUtil;
import ua.com.alevel.web.dto.request.data.PageAndSizeData;
import ua.com.alevel.web.dto.request.data.SortData;
import ua.com.alevel.web.dto.request.doctor.DoctorRequestDto;
import ua.com.alevel.web.dto.response.PageData;
import ua.com.alevel.web.dto.response.doctor.DoctorResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorFacadeImpl implements DoctorFacade {

    private final DoctorUserService doctorUserService;
    private final DoctorService doctorService;

    public DoctorFacadeImpl(DoctorUserService doctorUserService, DoctorService doctorService) {
        this.doctorUserService = doctorUserService;
        this.doctorService = doctorService;
    }

    @Override
    public void create(DoctorRequestDto doctorRequestDto) {
        Doctor doctor = new Doctor();
        doctor.setLastname(doctorRequestDto.getLastname());
        doctor.setFirstname(doctorRequestDto.getFirstname());
        doctor.setMiddleName(doctorRequestDto.getMiddleName());
        doctor.setSpecialization(doctorRequestDto.getSpecialization());
        doctorService.create(doctor);
    }

    @Override
    public void update(DoctorRequestDto doctorRequestDto, Long id) {
        Doctor doctor = doctorService.findById(id).get();
        doctor.setLastname(doctorRequestDto.getLastname());
        doctor.setFirstname(doctorRequestDto.getFirstname());
        doctor.setMiddleName(doctorRequestDto.getMiddleName());
        doctor.setSpecialization(doctorRequestDto.getSpecialization());
        doctorService.update(doctor);
    }

    @Override
    public void delete(Long id) {
        doctorService.delete(id);
    }

    @Override
    public DoctorResponseDto findById(Long id) {
        Doctor doctor = doctorService.findById(id).get();
        return new DoctorResponseDto(doctor);
    }

    @Override
    public PageData<DoctorResponseDto> findAll(WebRequest request) {
        PageAndSizeData pageAndSizeData = WebUtil.generatePageAndSizeData(request);
        SortData sortData = WebUtil.generateSortData(request);
        DataTableRequest dataTableRequest = new DataTableRequest();
        dataTableRequest.setSize(pageAndSizeData.getSize());
        dataTableRequest.setPage(pageAndSizeData.getPage());
        dataTableRequest.setSort(sortData.getSort());
        dataTableRequest.setOrder(sortData.getOrder());

        DataTableResponse<Doctor> all = doctorService.findAll(dataTableRequest);

        List< DoctorResponseDto> list = all.getItems().
                stream().
                map( DoctorResponseDto::new).
                collect(Collectors.toList());

        PageData< DoctorResponseDto> pageData = new PageData<>();
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
    public List<DoctorResponseDto> findAll() {
        List<Doctor> all = doctorService.findAll();
        List<DoctorResponseDto> items = all.stream().map(DoctorResponseDto::new).collect(Collectors.toList());
        return items;
    }

    @Override
    public DoctorUser getDoctorUser(Long id) {
        DoctorUser user = doctorService.findById(id).get().getDoctorUser();
        return user;
    }
}