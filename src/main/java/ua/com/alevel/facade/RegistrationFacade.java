package ua.com.alevel.facade;

import ua.com.alevel.web.dto.request.register.AuthDto;

public interface RegistrationFacade<AU extends AuthDto> {

    void registration(AU au);
}
