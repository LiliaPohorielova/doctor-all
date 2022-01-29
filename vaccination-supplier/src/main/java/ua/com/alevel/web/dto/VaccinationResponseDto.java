package ua.com.alevel.web.dto;

import ua.com.alevel.entity.Vaccination;

public class VaccinationResponseDto {

    private Long id;
    private String name;
    private String imageUrl;
    private String manufacturer;
    private String methodOfAdministration;
    private Integer quantity;

    public VaccinationResponseDto() { }

    public VaccinationResponseDto(Vaccination vaccination) {
        if (vaccination != null) {
            this.id = vaccination.getId();
            this.name = vaccination.getName();
            this.imageUrl = vaccination.getImageUrl();
            this.manufacturer = vaccination.getManufacturer();
            this.methodOfAdministration = vaccination.getMethodOfAdministration();
            this.quantity = vaccination.getQuantity();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
