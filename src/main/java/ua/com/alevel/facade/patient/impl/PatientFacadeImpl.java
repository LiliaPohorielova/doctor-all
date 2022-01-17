package ua.com.alevel.facade.patient.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.patient.PatientFacade;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.doctor.Doctor;
import ua.com.alevel.persistence.entity.patient.Patient;
import ua.com.alevel.persistence.entity.user.PatientUser;
import ua.com.alevel.service.patient.PatientService;
import ua.com.alevel.service.patient.PatientUserService;
import ua.com.alevel.util.WebUtil;
import ua.com.alevel.web.dto.request.data.PageAndSizeData;
import ua.com.alevel.web.dto.request.data.SortData;
import ua.com.alevel.web.dto.request.patient.PatientRequestDto;
import ua.com.alevel.web.dto.response.PageData;
import ua.com.alevel.web.dto.response.doctor.DoctorResponseDto;
import ua.com.alevel.web.dto.response.patient.PatientResponseDto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PatientFacadeImpl implements PatientFacade {

    private final PatientUserService patientUserService;
    private final PatientService patientService;

    public PatientFacadeImpl(PatientUserService patientUserService, PatientService patientService) {
        this.patientUserService = patientUserService;
        this.patientService = patientService;
    }

    @Override
    public void create(PatientRequestDto patientRequestDto) {
        Patient patient = new Patient();
        patient.setLastname(patientRequestDto.getLastname());
        patient.setFirstname(patientRequestDto.getFirstname());
        patient.setGender(patientRequestDto.getGender());
        patient.setPhoneNumber(patientRequestDto.getPhoneNumber());
        patient.setDateOfBirth(patientRequestDto.getDateOfBirth());
        patientService.create(patient);
    }

    @Override
    public void update(PatientRequestDto patientRequestDto, Long id) {
        Patient patient = patientService.findById(id).get();
        patient.setLastname(patientRequestDto.getLastname());
        patient.setFirstname(patientRequestDto.getFirstname());
        patient.setGender(patientRequestDto.getGender());
        patient.setPhoneNumber(patientRequestDto.getPhoneNumber());
        patient.setDateOfBirth(patientRequestDto.getDateOfBirth());
        patientService.update(patient);
    }

    @Override
    public void delete(Long id) {
        patientService.delete(id);
    }

    @Override
    public PatientResponseDto findById(Long id) {
        Patient patient = patientService.findById(id).get();
        return new PatientResponseDto(patient);
    }

    @Override
    public PageData<PatientResponseDto> findAll(WebRequest request) {
        PageAndSizeData pageAndSizeData = WebUtil.generatePageAndSizeData(request);
        SortData sortData = WebUtil.generateSortData(request);
        DataTableRequest dataTableRequest = new DataTableRequest();
        dataTableRequest.setSize(pageAndSizeData.getSize());
        dataTableRequest.setPage(pageAndSizeData.getPage());
        dataTableRequest.setSort(sortData.getSort());
        dataTableRequest.setOrder(sortData.getOrder());

        DataTableResponse<Patient> all = patientService.findAll(dataTableRequest);

        List< PatientResponseDto> list = all.getItems().
                stream().
                map( PatientResponseDto::new).
                collect(Collectors.toList());

        PageData< PatientResponseDto> pageData = new PageData<>();
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
    public List<PatientResponseDto> findAll() {
        List<Patient> all = patientService.findAll();
        List<PatientResponseDto> items = all.stream().map(PatientResponseDto::new).collect(Collectors.toList());
        return items;
    }

    @Override
    public PatientUser getPatientUser(Long id) {
        PatientUser user = patientService.findById(id).get().getPatientUser();
        return user;
    }

    @Override
    public Set<DoctorResponseDto> getDoctors(Long id) {
        Set<Doctor> doctors = patientService.getDoctors(id);
        Set<DoctorResponseDto> list = new HashSet<>();
        for (Doctor doctor : doctors) {
            DoctorResponseDto doctorResponseDto = new DoctorResponseDto(doctor);
            list.add(doctorResponseDto);
        }
        return list;
    }
}
