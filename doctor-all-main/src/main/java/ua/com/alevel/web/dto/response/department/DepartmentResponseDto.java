package ua.com.alevel.web.dto.response.department;

import ua.com.alevel.persistence.entity.department.DoctorsDepartment;
import ua.com.alevel.web.dto.response.ResponseDto;

public class DepartmentResponseDto extends ResponseDto {

    private String departmentImage;
    private String departmentName;
    private String description;

    public DepartmentResponseDto(DoctorsDepartment department) {
        if (department != null) {
            setId(department.getId());
            setCreated(department.getCreated());
            setUpdated(department.getUpdated());
            this.departmentImage = department.getDepartmentImage();
            this.departmentName = department.getDepartmentName();
            this.description = department.getDescription();
        }
    }

    public String getDepartmentImage() {
        return departmentImage;
    }

    public void setDepartmentImage(String departmentImage) {
        this.departmentImage = departmentImage;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
