package ua.com.alevel.service.doctor.impl;

import org.apache.commons.collections4.MapUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.persistence.crud.CrudRepositoryHelper;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.department.DoctorsDepartment;
import ua.com.alevel.persistence.entity.doctor.Doctor;
import ua.com.alevel.persistence.entity.patient.Patient;
import ua.com.alevel.persistence.entity.slot.Slot;
import ua.com.alevel.persistence.repository.department.DepartmentRepository;
import ua.com.alevel.persistence.repository.doctor.DoctorRepository;
import ua.com.alevel.persistence.repository.patient.PatientRepository;
import ua.com.alevel.service.doctor.DoctorService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final DepartmentRepository departmentRepository;
    private final CrudRepositoryHelper<Doctor, DoctorRepository> doctorRepositoryHelper;
    private final CrudRepositoryHelper<Patient, PatientRepository> patientRepositoryHelper;
    private final PatientRepository patientRepository;

    public DoctorServiceImpl(DoctorRepository doctorRepository, DepartmentRepository departmentRepository, CrudRepositoryHelper<Doctor, DoctorRepository> doctorRepositoryHelper, CrudRepositoryHelper<Patient, PatientRepository> patientRepositoryHelper, PatientRepository patientRepository) {
        this.doctorRepository = doctorRepository;
        this.departmentRepository = departmentRepository;
        this.doctorRepositoryHelper = doctorRepositoryHelper;
        this.patientRepositoryHelper = patientRepositoryHelper;
        this.patientRepository = patientRepository;
    }

    public void saveDoctor(Doctor doctor) {
        doctorRepository.save(doctor);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void addPatient(Long doctorId, Long patientId) {
        Doctor doctor = doctorRepositoryHelper.findById(doctorRepository, doctorId).get();
        Patient patient = patientRepositoryHelper.findById(patientRepository, patientId).get();
        doctor.addPatient(patient);
        doctorRepositoryHelper.update(doctorRepository, doctor);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void removePatient(Long doctorId, Long patientId) {
        Doctor doctor = doctorRepositoryHelper.findById(doctorRepository, doctorId).get();
        Patient patient = patientRepositoryHelper.findById(patientRepository, patientId).get();
        doctor.removePatient(patient);
        doctorRepositoryHelper.update(doctorRepository, doctor);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Set<Patient> getPatients(Long id) {
        return doctorRepositoryHelper.findById(doctorRepository, id).get().getPatients();
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void create(Doctor entity) {
        doctorRepositoryHelper.create(doctorRepository, entity);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void update(Doctor entity) {
        doctorRepositoryHelper.update(doctorRepository, entity);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void delete(Long id) {
        doctorRepositoryHelper.delete(doctorRepository, id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Doctor> findById(Long id) {
        return doctorRepositoryHelper.findById(doctorRepository, id);
    }

    @Override
    @Transactional(readOnly = true)
    public DataTableResponse<Doctor> findAll(DataTableRequest request) {
        return doctorRepositoryHelper.findAll(doctorRepository, request);
    }

    @Override
    public List<Doctor> findAll() {
        return doctorRepository.findAll();
    }

    @Override
    public DataTableResponse<Patient> findPatientsByDoctor(Doctor doctor, DataTableRequest dataTableRequest) {
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

        Page<Patient> pageEntity = doctorRepository.getPatientsById(doctor.getId(), request);

        DataTableResponse<Patient> dataTableResponse = new DataTableResponse<>();
        dataTableResponse.setSort(sortParam);
        dataTableResponse.setOrder(orderParam);
        dataTableResponse.setPageSize(size);
        dataTableResponse.setCurrentPage(page);
        dataTableResponse.setItemsSize(pageEntity.getTotalElements());
        dataTableResponse.setTotalPageSize(pageEntity.getTotalPages());
        dataTableResponse.setItems(pageEntity.getContent());

        return dataTableResponse;
    }

    @Override
    public DataTableResponse<Doctor> findDoctorsByDepartment(DoctorsDepartment department, DataTableRequest dataTableRequest) {
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

        Page<Doctor> pageEntity = doctorRepository.getDoctorsByDepartmentId(department.getId(), request);

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

    @Override
    public Set<Slot> getSlots(Long id) {
        return doctorRepositoryHelper.findById(doctorRepository, id).get().getSlots();
    }
}
