package ua.com.alevel.facade.doctor.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.doctor.DoctorFacade;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.doctor.Doctor;
import ua.com.alevel.persistence.entity.patient.Patient;
import ua.com.alevel.persistence.entity.slot.Slot;
import ua.com.alevel.persistence.entity.user.DoctorUser;
import ua.com.alevel.persistence.type.DoctorSpecialization;
import ua.com.alevel.service.department.DepartmentService;
import ua.com.alevel.service.doctor.DoctorService;
import ua.com.alevel.service.patient.PatientService;
import ua.com.alevel.service.slot.SlotService;
import ua.com.alevel.util.WebUtil;
import ua.com.alevel.web.dto.request.data.PageAndSizeData;
import ua.com.alevel.web.dto.request.data.SortData;
import ua.com.alevel.web.dto.request.doctor.DoctorRequestDto;
import ua.com.alevel.web.dto.response.PageData;
import ua.com.alevel.web.dto.response.doctor.DoctorResponseDto;
import ua.com.alevel.web.dto.response.patient.PatientResponseDto;
import ua.com.alevel.web.dto.response.slot.SlotResponseDto;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DoctorFacadeImpl implements DoctorFacade {

    private final SlotService slotService;
    private final DoctorService doctorService;
    private final PatientService patientService;
    private final DepartmentService departmentService;

    public DoctorFacadeImpl(SlotService slotService, DoctorService doctorService, PatientService patientService, DepartmentService departmentService) {
        this.slotService = slotService;
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.departmentService = departmentService;
    }

    @Override
    public void addPatient(Long doctorId, Long patientId) {
        Doctor doctor = doctorService.findById(doctorId).get();
        Patient patient = patientService.findById(patientId).get();
        doctor.addPatient(patient);
        doctorService.update(doctor);
    }

    @Override
    public void removePatient(Long doctorId, Long patientId) {
        Doctor doctor = doctorService.findById(doctorId).get();
        Patient patient = patientService.findById(patientId).get();
        doctor.removePatient(patient);
        doctorService.update(doctor);
    }

    @Override
    public Set<PatientResponseDto> getPatients(Long id) {
        Set<Patient> patients = doctorService.findById(id).get().getPatients();
        Set<PatientResponseDto> set = new HashSet<>();
        for (Patient patient : patients) {
            PatientResponseDto patientResponseDto = new PatientResponseDto(patient);
            set.add(patientResponseDto);
        }
        return set;
    }

    @Override
    public PageData<PatientResponseDto> getPatientsTable(Long id, WebRequest request) {
        PageAndSizeData pageAndSizeData = WebUtil.generatePageAndSizeData(request);
        SortData sortData = WebUtil.generateSortData(request);
        DataTableRequest dataTableRequest = new DataTableRequest();
        dataTableRequest.setSize(pageAndSizeData.getSize());
        dataTableRequest.setPage(pageAndSizeData.getPage());
        dataTableRequest.setSort(sortData.getSort());
        dataTableRequest.setOrder(sortData.getOrder());

        DataTableResponse<Patient> patients = doctorService.findPatientsByDoctor(doctorService.findById(id).get(), dataTableRequest);

        List<PatientResponseDto> list = patients.getItems().
                stream().
                map(PatientResponseDto::new).
                collect(Collectors.toList());

        PageData<PatientResponseDto> pageData = new PageData<>();
        pageData.setItems(list);
        pageData.setCurrentPage(pageAndSizeData.getPage());
        pageData.setPageSize(pageAndSizeData.getSize());
        pageData.setOrder(sortData.getOrder());
        pageData.setSort(sortData.getSort());
        pageData.setItemsSize(patients.getItemsSize());
        pageData.initPaginationState(pageData.getCurrentPage());

        return pageData;
    }

    @Override
    public PageData<DoctorResponseDto> getDoctorsByDepartment(Long id, WebRequest request) {
        PageAndSizeData pageAndSizeData = WebUtil.generatePageAndSizeData(request);
        SortData sortData = WebUtil.generateSortData(request);
        DataTableRequest dataTableRequest = new DataTableRequest();
        dataTableRequest.setSize(pageAndSizeData.getSize());
        dataTableRequest.setPage(pageAndSizeData.getPage());
        dataTableRequest.setSort(sortData.getSort());
        dataTableRequest.setOrder(sortData.getOrder());

        DataTableResponse<Doctor> doctors = doctorService.findDoctorsByDepartment(departmentService.findById(id).get(),dataTableRequest);

        List<DoctorResponseDto> list = doctors.getItems().
                stream().
                map(DoctorResponseDto::new).
                collect(Collectors.toList());

        PageData<DoctorResponseDto> pageData = new PageData<>();
        pageData.setItems(list);
        pageData.setCurrentPage(pageAndSizeData.getPage());
        pageData.setPageSize(pageAndSizeData.getSize());
        pageData.setOrder(sortData.getOrder());
        pageData.setSort(sortData.getSort());
        pageData.setItemsSize(doctors.getItemsSize());
        pageData.initPaginationState(pageData.getCurrentPage());

        return pageData;
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

        List<DoctorResponseDto> list = all.getItems().
                stream().
                map(DoctorResponseDto::new).
                collect(Collectors.toList());

        PageData<DoctorResponseDto> pageData = new PageData<>();
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
    public List<DoctorResponseDto> findFirst() {
        List<DoctorResponseDto> dtos = new ArrayList<>();
        dtos.add(findById(4L));
        dtos.add(findById(5L));
        dtos.add(findById(6L));
        dtos.add(findById(7L));
        return dtos;
    }

    @Override
    public DoctorUser getDoctorUser(Long id) {
        DoctorUser user = doctorService.findById(id).get().getDoctorUser();
        return user;
    }

    @Override
    public Set<SlotResponseDto> getSlots(Long id) {
        Set<Slot> slots = doctorService.getSlots(id);
        Set<SlotResponseDto> set = new HashSet<>();
        for (Slot slot : slots) {
            SlotResponseDto slotResponseDto = new SlotResponseDto(slot);
            set.add(slotResponseDto);
        }
        return set;
    }

    @Override
    public void removeSlot(Long doctorId, Long slotId) {
        Doctor doctor = doctorService.findById(doctorId).get();
        slotService.delete(slotId);
        doctorService.update(doctor);
    }

    @Override
    public Map<Long, Set<String>> getDoctorsAndSpec() {
        Map<Long, Set<String>> doctorsAndSpec = new HashMap<>();
        List<Doctor> all = doctorService.findAll();
        Set<String> doc = new HashSet<>();
        Set<Long> idSet = new HashSet<>();
        for (Doctor doctor : all) {
            doc.add(doctor.getLastname() + " " + doctor.getFirstname());
            Long id = Long.valueOf(DoctorSpecialization.valueOf(doctor.getSpecialization().toString()).ordinal());
            if (!idSet.contains(id)) {
                idSet.add(id);
                doctorsAndSpec.put(id, doc);
            }
        }
        return doctorsAndSpec;
    }

    @Override
    public List<String> getDoctorsBySpecId(Integer specializationId) {
        List<Doctor> all = doctorService.findAll();
        List<String> doc = new ArrayList<>();
        for (Doctor doctor : all) {
            if (DoctorSpecialization.valueOf(doctor.getSpecialization().toString()).ordinal() == specializationId) {
                doc.add(doctor.getId().toString());
                doc.add(doctor.getLastname() + " " + doctor.getFirstname() + " " + doctor.getMiddleName());
            }
        }
        return doc;
    }

    @Override
    public List<DoctorResponseDto> search(WebRequest webRequest) {
        Map<String, Object> queryMap = new HashMap<>();
        if (webRequest.getParameterMap().get(WebUtil.DOCTOR_SEARCH_PARAM) != null) {
            String[] params = webRequest.getParameterMap().get(WebUtil.DOCTOR_SEARCH_PARAM);
            String doctorName = params[0];
            String mainDoctorName = doctorName.split(",", 2)[0];
            queryMap.put(WebUtil.DOCTOR_SEARCH_PARAM, mainDoctorName);
        }
        List<Doctor> doctors = patientService.searchDoctor(queryMap);
        return doctors.stream().map(DoctorResponseDto::new).toList();
    }
}
