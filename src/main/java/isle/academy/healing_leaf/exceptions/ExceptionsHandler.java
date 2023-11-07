package isle.academy.healing_leaf.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

import static isle.academy.healing_leaf.data.StringsPackage.*;

@Slf4j
@ControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<String> handleAnyException(Exception exception, WebRequest request) {
        log.error(exception.getLocalizedMessage(), exception.getLocalizedMessage());
        return new ResponseEntity<>(INTERNAL_SERVER_ERROR + exception.getLocalizedMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {AuthenticationException.class})
    public ResponseEntity<String> handleUserAlreadyInLobbyException(AuthenticationException exception, WebRequest request) {
        log.error(AUTHENTICATION_EXCEPTION, exception.getLocalizedMessage());
        return new ResponseEntity<>(exception.getLocalizedMessage(), new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {ForbiddenException.class})
    public ResponseEntity<String> handleForbiddenException(ForbiddenException exception, WebRequest request) {
        log.error(FORBIDDEN_EXCEPTION, exception.getLocalizedMessage());
        return new ResponseEntity<>(exception.getLocalizedMessage(), new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = {InvalidRequestException.class})
    public ResponseEntity<String> handleInvalidRequestException(InvalidRequestException exception, WebRequest request) {
        log.error(INVALID_REQUEST_EXCEPTION, exception.getLocalizedMessage());
        return new ResponseEntity<>(exception.getLocalizedMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        String field = ex.getBindingResult().getFieldErrors().get(0).getField();
        String errorMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        String completeErrorMessage = "'" + field + "': " + errorMessage;
        log.error(REQUEST_VALIDATION_EXCEPTION, completeErrorMessage);
        return new ResponseEntity<>(completeErrorMessage, status);
    }
}