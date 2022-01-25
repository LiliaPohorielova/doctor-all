package ua.com.alevel.persistence.entity.vaccination;

import ua.com.alevel.persistence.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "vaccinations")
public class Vaccination extends BaseEntity {

    private String name;
    private Integer quantity;

    @Column(name = "image_url")
    private String imageUrl;

    private String manufacturer;

    @Column(name = "method_of_administration")
    private String methodOfAdministration;

    public Vaccination() {
        super();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Vaccination that = (Vaccination) o;
        return Objects.equals(name, that.name) && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, quantity);
    }
}
