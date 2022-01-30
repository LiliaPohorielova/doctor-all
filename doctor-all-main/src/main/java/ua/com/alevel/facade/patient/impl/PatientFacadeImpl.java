package ua.com.alevel.facade.patient.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.patient.PatientFacade;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.appointment.PatientAppointment;
import ua.com.alevel.persistence.entity.doctor.Doctor;
import ua.com.alevel.persistence.entity.patient.Patient;
import ua.com.alevel.persistence.entity.slot.Slot;
import ua.com.alevel.persistence.entity.user.PatientUser;
import ua.com.alevel.persistence.entity.vaccination.Vaccination;
import ua.com.alevel.service.appointment.PatientAppointmentService;
import ua.com.alevel.service.patient.PatientService;
import ua.com.alevel.service.vaccination.VaccinationService;
import ua.com.alevel.util.WebUtil;
import ua.com.alevel.web.dto.request.data.PageAndSizeData;
import ua.com.alevel.web.dto.request.data.SortData;
import ua.com.alevel.web.dto.request.patient.PatientRequestDto;
import ua.com.alevel.web.dto.response.PageData;
import ua.com.alevel.web.dto.response.appointment.AppointmentResponseDto;
import ua.com.alevel.web.dto.response.doctor.DoctorResponseDto;
import ua.com.alevel.web.dto.response.patient.PatientResponseDto;
import ua.com.alevel.web.dto.response.slot.SlotResponseDto;
import ua.com.alevel.web.dto.response.vaccination.VaccinationResponseDto;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PatientFacadeImpl implements PatientFacade {

    private final PatientAppointmentService patientAppointmentService;
    private final PatientService patientService;
    private final VaccinationService vaccinationService;

    public PatientFacadeImpl(PatientService patientService, PatientAppointmentService patientAppointmentService, VaccinationService vaccinationService) {
        this.patientService = patientService;
        this.patientAppointmentService = patientAppointmentService;
        this.vaccinationService = vaccinationService;
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
        return all.stream().map(PatientResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public PatientUser getPatientUser(Long id) {
        return patientService.findById(id).get().getPatientUser();
    }

    @Override
    public Set<DoctorResponseDto> getDoctors(Long id) {
        Set<Doctor> doctors = patientService.getDoctors(id);
        Set<DoctorResponseDto> set = new HashSet<>();
        for (Doctor doctor : doctors) {
            DoctorResponseDto doctorResponseDto = new DoctorResponseDto(doctor);
            set.add(doctorResponseDto);
        }
        return set;
    }

    @Override
    public Set<AppointmentResponseDto> getAppointments(Long id) {
        Set<PatientAppointment> appointments = patientService.getAppointments(id);
        Set<AppointmentResponseDto> set = new HashSet<>();
        for (PatientAppointment appointment : appointments) {
            AppointmentResponseDto patientAppointmentResponseDto = new AppointmentResponseDto(appointment);
            set.add(patientAppointmentResponseDto);
        }
        return set;
    }

    @Override
    public void addAppointment(Long appointmentId, Long patientId) {
        Patient patient = patientService.findById(patientId).get();
        PatientAppointment patientAppointment = patientAppointmentService.findById(appointmentId).get();
        patient.addPatientAppointment(patientAppointment);
        patientService.update(patient);
    }

    @Override
    public void addVaccination(Long vaccinationId, Long patientId) {
        Vaccination vaccination = vaccinationService.findById(vaccinationId).get();
        Patient patient = patientService.findById(patientId).get();
        patient.addVaccination(vaccination);
        patientService.update(patient);
    }

    @Override
    public Set<VaccinationResponseDto> getVaccinations(Long id) {
        Set<Vaccination> vaccinations = patientService.getVaccinations(id);
        Set<VaccinationResponseDto> set = new HashSet<>();
        for (Vaccination vaccination : vaccinations) {
            VaccinationResponseDto vaccinationResponseDto = new VaccinationResponseDto(vaccination);
            set.add(vaccinationResponseDto);
        }
        return set;
    }

    @Override
    public List<PatientResponseDto> search(WebRequest webRequest) {
        Map<String, Object> queryMap = new HashMap<>();
        if (webRequest.getParameterMap().get(WebUtil.PATIENT_SEARCH_PARAM) != null) {
            String[] params = webRequest.getParameterMap().get(WebUtil.PATIENT_SEARCH_PARAM);
            String patientName = params[0];
            String mainPatientName = patientName.split(",", 2)[0];
            queryMap.put(WebUtil.PATIENT_SEARCH_PARAM, mainPatientName);
        }
        List<Patient> patients = patientService.searchPatient(queryMap);
        return patients.stream().map(PatientResponseDto::new).toList();
    }

    @Override
    public PageData<DoctorResponseDto> getDoctorsTable(Long id, WebRequest request) {
        PageAndSizeData pageAndSizeData = WebUtil.generatePageAndSizeData(request);
        SortData sortData = WebUtil.generateSortData(request);
        DataTableRequest dataTableRequest = new DataTableRequest();
        dataTableRequest.setSize(pageAndSizeData.getSize());
        dataTableRequest.setPage(pageAndSizeData.getPage());
        dataTableRequest.setSort(sortData.getSort());
        dataTableRequest.setOrder(sortData.getOrder());

        DataTableResponse<Doctor> doctors = patientService.findDoctorsByPatient(patientService.findById(id).get(), dataTableRequest);

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
}
