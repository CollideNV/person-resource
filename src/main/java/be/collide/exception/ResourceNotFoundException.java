package be.collide.exception;

import jakarta.ws.rs.NotFoundException;
import lombok.Getter;

@Getter
public class ResourceNotFoundException extends NotFoundException {
    public ResourceNotFoundException(String message) {

        super(message);
    }
}
