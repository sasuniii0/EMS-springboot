package lk.ijse.gdse.springboot_practice.exception;

public class ResourceAlreadyFoundException extends RuntimeException {
    public ResourceAlreadyFoundException(String message) {
        super(message);
    }
}
