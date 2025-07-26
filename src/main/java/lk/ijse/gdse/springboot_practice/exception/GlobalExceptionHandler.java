package lk.ijse.gdse.springboot_practice.exception;

import lk.ijse.gdse.springboot_practice.util.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// Global exception handler for the application
// This class can be used to handle exceptions globally across all controllers
// You can define methods here to handle specific exceptions and return custom responses
// For example, you can handle ResourceNotFoundException, ResourceAlreadyFoundException, etc.

@RestControllerAdvice
public class GlobalExceptionHandler {

    // This method handles generic exceptions that are not caught by other exception handlers
    // It returns a ResponseEntity with a custom APIResponse object containing the error details
    //@ExceptionHandler (Exception.class) - // This annotation is used to specify that this method should handle exceptions of type Exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse> handleGenericException(Exception e){
        // Log the exception or return a custom error response
        // Here we are returning a generic error response with status code 500
        // You can customize the response as per your requirements
        // e.getMessage() can be used to get the error message from the exception
        return new ResponseEntity(new APIResponse(500,e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIResponse> handleResourceNotFoundException(ResourceNotFoundException e) {
        // This method handles ResourceNotFoundException and returns a 404 Not Found response
        return new ResponseEntity(new APIResponse(404, e.getMessage(), null), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceAlreadyFoundException.class)
    public ResponseEntity<APIResponse> handleResourceAlreadyFoundException(ResourceAlreadyFoundException e) {
        // This method handles ResourceAlreadyFoundException and returns a 409 Conflict response
        return new ResponseEntity(new APIResponse(409, e.getMessage(), null), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<APIResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        // This method handles IllegalArgumentException and returns a 400 Bad Request response
        return new ResponseEntity(new APIResponse(400, e.getMessage(), null), HttpStatus.BAD_REQUEST);
    }
}
