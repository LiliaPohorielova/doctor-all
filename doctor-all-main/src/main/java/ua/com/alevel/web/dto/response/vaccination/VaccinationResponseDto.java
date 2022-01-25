package ua.com.alevel.web.dto.response.vaccination;

import ua.com.alevel.persistence.entity.vaccination.Vaccination;
import ua.com.alevel.web.dto.response.ResponseDto;

import javax.persistence.Column;

public class VaccinationResponseDto extends ResponseDto {

    private String name;
    private Integer quantity;
    private String imageUrl;
    private String manufacturer;
    private String methodOfAdministration;

    public VaccinationResponseDto(Vaccination vaccination) {
        setId(vaccination.getId());
        setCreated(vaccination.getCreated());
        setUpdated(vaccination.getUpdated());
        this.name = vaccination.getName();
        this.quantity = vaccination.getQuantity();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getMethodOfAdministration() {
        return methodOfAdministration;
    }

    public void setMethodOfAdministration(String methodOfAdministration) {
        this.methodOfAdministration = methodOfAdministration;
    }
}
