package ua.com.alevel.persistence.entity.department;

import ua.com.alevel.persistence.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "doc_departments")
public class DoctorsDepartment extends BaseEntity {

    @Column(name = "img_url")
    private String departmentImage;

    @Column(name = "department_name")
    private String departmentName;

    @Column(columnDefinition = "TEXT")
    private String description;

    public DoctorsDepartment() {
        super();
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentImage() {
        return departmentImage;
    }

    public void setDepartmentImage(String departmentImage) {
        this.departmentImage = departmentImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
