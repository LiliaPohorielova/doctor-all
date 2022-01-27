package ua.com.alevel.service.department.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.persistence.crud.CrudRepositoryHelper;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.department.DoctorsDepartment;
import ua.com.alevel.persistence.repository.department.DepartmentRepository;
import ua.com.alevel.persistence.type.DoctorSpecialization;
import ua.com.alevel.service.department.DepartmentService;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final CrudRepositoryHelper<DoctorsDepartment, DepartmentRepository> departmentRepositoryHelper;


    public DepartmentServiceImpl(DepartmentRepository departmentRepository,
                                 CrudRepositoryHelper<DoctorsDepartment, DepartmentRepository> departmentRepositoryHelper) {
        this.departmentRepository = departmentRepository;
        this.departmentRepositoryHelper = departmentRepositoryHelper;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void create(DoctorsDepartment entity) {
        departmentRepositoryHelper.create(departmentRepository, entity);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void update(DoctorsDepartment entity) {
        departmentRepositoryHelper.update(departmentRepository, entity);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void delete(Long id) {
        departmentRepositoryHelper.delete(departmentRepository, id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DoctorsDepartment> findById(Long id) {
        return departmentRepositoryHelper.findById(departmentRepository, id);
    }

    @Override
    @Transactional(readOnly = true)
    public DataTableResponse<DoctorsDepartment> findAll(DataTableRequest request) {
        return departmentRepositoryHelper.findAll(departmentRepository, request);
    }

    @Override
    public List<DoctorsDepartment> findAll() {
        return departmentRepository.findAll();
    }


    @Override
    public DoctorsDepartment findDepartmentBySpecialization(DoctorSpecialization doctorSpecialization) {
        return switch (doctorSpecialization) {
            case DENTIST -> departmentRepository.findById(1L).get();
            case THERAPIST -> departmentRepository.findById(5L).get();
            case SURGEON -> departmentRepository.findById(2L).get();
            case CARDIOLOGIST -> departmentRepository.findById(3L).get();
            case ORTHOPEDIST -> departmentRepository.findById(4L).get();
            case PHYSICIAN -> departmentRepository.findById(6L).get();
        };
    }
}
