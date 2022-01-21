package ua.com.alevel.facade;

import ua.com.alevel.persistence.entity.user.User;
import ua.com.alevel.web.dto.request.auth.AuthDto;

public interface RegistrationFacade<AU extends AuthDto, U extends User> {

    void registration(AU au);
    U findByEmail(String email);
}
