package e_commecre.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT) // 409 Conflict
    public Map<String, String> handleEmailAlreadyExists(EmailAlreadyExistsException ex) {
        return createErrorResponse("Email đã tồn tại", ex.getMessage());
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return Map.of("error", "Dữ liệu không hợp lệ!");
    }
    
    @ExceptionHandler(PhoneNumberAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT) // 409 Conflict
    public Map<String, String> handlePhoneNumberAlreadyExists(PhoneNumberAlreadyExistsException ex) {
        return createErrorResponse("Số điện thoại đã tồn tại", ex.getMessage());
    }

    @ExceptionHandler(ResouceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) // 404 Not Found
    public Map<String, String> handleResourceNotFound(ResouceNotFoundException ex) {
        return createErrorResponse("Không tìm thấy tài nguyên", ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500 Internal Server Error
    public Map<String, String> handleGeneralException(Exception ex) {
        return createErrorResponse("Lỗi máy chủ", ex.getMessage());
    }

    private Map<String, String> createErrorResponse(String error, String message) {
        Map<String, String> response = new HashMap<>();
        response.put("error", error);
        response.put("message", message);
        return response;
    }
}
