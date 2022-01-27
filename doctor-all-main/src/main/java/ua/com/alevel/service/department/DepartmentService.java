package ua.com.alevel.service.department;

import ua.com.alevel.persistence.entity.department.DoctorsDepartment;
import ua.com.alevel.persistence.type.DoctorSpecialization;
import ua.com.alevel.service.BaseCrudService;

import java.util.List;

public interface DepartmentService extends BaseCrudService<DoctorsDepartment> {

    List<DoctorsDepartment> findAll();

    DoctorsDepartment findDepartmentBySpecialization(DoctorSpecialization doctorSpecialization);
}