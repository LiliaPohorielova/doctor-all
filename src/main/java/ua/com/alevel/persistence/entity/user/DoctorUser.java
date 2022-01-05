package ua.com.alevel.persistence.entity.user;

import ua.com.alevel.persistence.type.RoleType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("DOCTOR")
/*@EntityListeners({
        FullNameGenerationListener.class,
        AgeByBirthDayGenerationListener.class
})*/
public class DoctorUser extends User {

    public DoctorUser() {
        super();
        setRoleType(RoleType.ROLE_DOCTOR);
    }
}
