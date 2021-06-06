package vs.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // for the @Valid annotation in request body
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status,
            WebRequest request) {

        // Get all errors
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(x -> x.getDefaultMessage()).collect(Collectors.toList());

        return ResponseEntity.status(status).body(new ErrorResponse(status.value(), errors));

    }

    // for the @Validated annotation in path parameters
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex,
            WebRequest request) {
        
        List<String> errors = new ArrayList<String>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getPropertyPath() + ": " + violation.getMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), errors));
    }

    private class ErrorResponse {
        public int status;
        public List<String> errors;

        public ErrorResponse(int status, List<String> errors) {
            this.status = status;
            this.errors = errors;
        }
    }
}
