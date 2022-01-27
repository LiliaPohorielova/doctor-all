package ua.com.alevel.web.dto.request.department;

import ua.com.alevel.web.dto.request.RequestDto;

public class DepartmentRequestDto extends RequestDto {

    private String departmentImage;
    private String departmentName;
    private String description;

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
