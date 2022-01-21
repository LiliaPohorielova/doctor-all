package ua.com.alevel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * custom exception for login
 * @author alicj
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidNameOrPasswordException extends RuntimeException {

    public InvalidNameOrPasswordException() {
    }

    public InvalidNameOrPasswordException(String message) {
        super(message);
    }
}
