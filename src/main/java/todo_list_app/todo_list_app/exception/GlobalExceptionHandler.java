package todo_list_app.todo_list_app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import todo_list_app.todo_list_app.utils.response.ErrorResponse;
import todo_list_app.todo_list_app.utils.response.Response;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException e){
        ErrorResponse errResponse = new ErrorResponse("500", e.getMessage());
        return new ResponseEntity<>(errResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException e){
        ErrorResponse errResponse = new ErrorResponse("403", e.getMessage());
        return new ResponseEntity<>(errResponse, HttpStatus.UNAUTHORIZED);
    }
}
