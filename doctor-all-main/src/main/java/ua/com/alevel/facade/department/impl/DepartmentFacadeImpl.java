package ua.com.alevel.facade.department.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.department.DepartmentFacade;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.department.DoctorsDepartment;
import ua.com.alevel.persistence.type.DoctorSpecialization;
import ua.com.alevel.service.department.DepartmentService;
import ua.com.alevel.util.WebUtil;
import ua.com.alevel.web.dto.request.data.PageAndSizeData;
import ua.com.alevel.web.dto.request.data.SortData;
import ua.com.alevel.web.dto.request.department.DepartmentRequestDto;
import ua.com.alevel.web.dto.response.PageData;
import ua.com.alevel.web.dto.response.department.DepartmentResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentFacadeImpl implements DepartmentFacade {

    private final DepartmentService departmentService;

    public DepartmentFacadeImpl(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @Override
    public void create(DepartmentRequestDto departmentRequestDto) {
        DoctorsDepartment department = new DoctorsDepartment();
        department.setDepartmentImage(departmentRequestDto.getDepartmentImage());
        department.setDepartmentName(departmentRequestDto.getDepartmentName());
        department.setDescription(departmentRequestDto.getDescription());
        departmentService.create(department);
    }

    @Override
    public void update(DepartmentRequestDto departmentRequestDto, Long id) {
        DoctorsDepartment department = departmentService.findById(id).get();
        department.setDepartmentImage(departmentRequestDto.getDepartmentImage());
        department.setDepartmentName(departmentRequestDto.getDepartmentName());
        department.setDescription(departmentRequestDto.getDescription());
        departmentService.update(department);
    }

    @Override
    public void delete(Long id) {
        departmentService.delete(id);
    }

    @Override
    public DepartmentResponseDto findById(Long id) {
        DoctorsDepartment department = departmentService.findById(id).get();
        return new DepartmentResponseDto(department);
    }

    @Override
    public PageData<DepartmentResponseDto> findAll(WebRequest request) {
        PageAndSizeData pageAndSizeData = WebUtil.generatePageAndSizeData(request);
        SortData sortData = WebUtil.generateSortData(request);
        DataTableRequest dataTableRequest = new DataTableRequest();
        dataTableRequest.setSize(pageAndSizeData.getSize());
        dataTableRequest.setPage(pageAndSizeData.getPage());
        dataTableRequest.setSort(sortData.getSort());
        dataTableRequest.setOrder(sortData.getOrder());

        DataTableResponse<DoctorsDepartment> all = departmentService.findAll(dataTableRequest);

        List<DepartmentResponseDto> list = all.getItems().
                stream().
                map(DepartmentResponseDto::new).
                collect(Collectors.toList());

        PageData<DepartmentResponseDto> pageData = new PageData<>();
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
    public List<DepartmentResponseDto> findAll() {
        List<DoctorsDepartment> all = departmentService.findAll();
        List<DepartmentResponseDto> items = all.stream().map(DepartmentResponseDto::new).collect(Collectors.toList());
        return items;
    }

    @Override
    public DoctorsDepartment findDepartmentBySpecialization(DoctorSpecialization doctorSpecialization) {
        return departmentService.findDepartmentBySpecialization(doctorSpecialization);
    }
}
