package ua.com.alevel.service.patient.impl;

import org.apache.commons.collections4.MapUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.logs.LogLevel;
import ua.com.alevel.logs.LogService;
import ua.com.alevel.persistence.crud.CrudRepositoryHelper;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.appointment.PatientAppointment;
import ua.com.alevel.persistence.entity.doctor.Doctor;
import ua.com.alevel.persistence.entity.patient.Patient;
import ua.com.alevel.persistence.entity.vaccination.Vaccination;
import ua.com.alevel.persistence.repository.doctor.DoctorRepository;
import ua.com.alevel.persistence.repository.patient.PatientRepository;
import ua.com.alevel.service.patient.PatientService;
import ua.com.alevel.util.WebUtil;

import java.util.*;

@Service
public class PatientServiceImpl implements PatientService {

    private final LogService logService;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final CrudRepositoryHelper<Patient, PatientRepository> patientRepositoryHelper;

    public PatientServiceImpl(LogService logService, DoctorRepository doctorRepository, PatientRepository patientRepository, CrudRepositoryHelper<Patient, PatientRepository> patientRepositoryHelper) {
        this.logService = logService;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.patientRepositoryHelper = patientRepositoryHelper;
    }

    public void savePatient(Patient patient) {
        patientRepository.save(patient);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void create(Patient entity) {
        patientRepositoryHelper.create(patientRepository, entity);
        logService.log(LogLevel.INFO, entity.getFirstname() + " was successfully created");
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void update(Patient entity) {
        patientRepositoryHelper.update(patientRepository, entity);
        logService.log(LogLevel.INFO, entity.getFirstname() + " was successfully updated");
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void delete(Long id) {
        patientRepositoryHelper.delete(patientRepository, id);
        logService.log(LogLevel.WARN, "patient " + id + " was deleted");
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Patient> findById(Long id) {
        return patientRepositoryHelper.findById(patientRepository, id);
    }

    @Override
    @Transactional(readOnly = true)
    public DataTableResponse<Patient> findAll(DataTableRequest request) {
        return patientRepositoryHelper.findAll(patientRepository, request);
    }

    @Override
    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    @Override
    public Set<Doctor> getDoctors(Long id) {
        return patientRepositoryHelper.findById(patientRepository, id).get().getDoctors();
    }

    @Override
    public Set<PatientAppointment> getAppointments(Long id) {
        return patientRepositoryHelper.findById(patientRepository, id).get().getPatientAppointments();
    }

    @Override
    public Set<Vaccination> getVaccinations(Long id) {
        return patientRepositoryHelper.findById(patientRepository, id).get().getPatientVaccinations();
    }

    @Override
    public List<Patient> searchPatient(Map<String, Object> queryMap) {
        if (queryMap.get(WebUtil.PATIENT_SEARCH_PARAM) != null) {
            String patientName = (String) queryMap.get(WebUtil.PATIENT_SEARCH_PARAM);
            return patientRepository.findByLastnameContaining(patientName);
        }
        return patientRepository.findAll();
    }

    @Override
    public List<Doctor> searchDoctor(Map<String, Object> queryMap) {
        if (queryMap.get(WebUtil.DOCTOR_SEARCH_PARAM) != null) {
            String doctorName = (String) queryMap.get(WebUtil.DOCTOR_SEARCH_PARAM);
            return doctorRepository.findByLastnameContaining(doctorName);
        }
        return doctorRepository.findAll();
    }

    @Override
    public DataTableResponse<Doctor> findDoctorsByPatient(Patient patient, DataTableRequest dataTableRequest) {
        int page = dataTableRequest.getPage() - 1;
        int size = dataTableRequest.getSize();
        String sortParam = dataTableRequest.getSort();
        String orderParam = dataTableRequest.getOrder();

        Sort sort = orderParam.equals("desc")
                ? Sort.by(sortParam).descending()
                : Sort.by(sortParam).ascending();

        if (MapUtils.isNotEmpty(dataTableRequest.getRequestParamMap())) {
            System.out.println("dataTableRequest = " + dataTableRequest.getRequestParamMap());
        }

        PageRequest request = PageRequest.of(page, size, sort);

        Page<Doctor> pageEntity = patientRepository.getDoctorsById(patient.getId(), request);

        DataTableResponse<Doctor> dataTableResponse = new DataTableResponse<>();
        dataTableResponse.setSort(sortParam);
        dataTableResponse.setOrder(orderParam);
        dataTableResponse.setPageSize(size);
        dataTableResponse.setCurrentPage(page);
        dataTableResponse.setItemsSize(pageEntity.getTotalElements());
        dataTableResponse.setTotalPageSize(pageEntity.getTotalPages());
        dataTableResponse.setItems(pageEntity.getContent());

        return dataTableResponse;
    }
}
