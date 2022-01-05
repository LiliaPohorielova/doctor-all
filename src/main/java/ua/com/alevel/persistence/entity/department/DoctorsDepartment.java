package ua.com.alevel.persistence.entity.department;

import ua.com.alevel.persistence.entity.BaseEntity;
import ua.com.alevel.persistence.entity.doctor.Doctor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "doc_departments")
public class DoctorsDepartment extends BaseEntity {

    @Column(name = "department_name")
    private String departmentName;

    public DoctorsDepartment() {
        super();
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
