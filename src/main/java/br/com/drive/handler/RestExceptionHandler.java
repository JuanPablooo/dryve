package br.com.drive.handler;

import br.com.drive.exception.*;
//import javassist.NotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Log4j2
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestExceptionDetails> handleBadRequestException(BadRequestException bre) {
        return new ResponseEntity<>(
                BadRequestExceptionDetails.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title("Bad Request Exception, Check the Documentation")
                        .details(bre.getMessage())
                        .developerMessage(bre.getClass().getName())
                        .build(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<NotFoundExceptionDetails> handleNotFoundException(ResourceNotFoundException bre) {
        return new ResponseEntity<>(
                NotFoundExceptionDetails.builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .title("Resource Not Found Exception")
                        .details(bre.getMessage())
                        .developerMessage(bre.getClass().getName())
                        .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<MyDataIntegrityViolationException> handleNotFoundException(DataIntegrityViolationException bre) {
        String message = bre.getMostSpecificCause().getMessage();
//        String message1 = bre.getCause().getMessage();
//        String message2 = bre.getMessage();
        return new ResponseEntity<>(
                MyDataIntegrityViolationException.builder()
                        .status(HttpStatus.CONFLICT.value())
                        .title("Data Violation Exception")
                        .details(message)
                        .developerMessage(bre.getClass().getName())
                        .build(), HttpStatus.CONFLICT);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));
        String fieldsMessage = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));

        return new ResponseEntity<>(
                ValidationExceptionDetails.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title("Bad Request Exception, Invalid Fields")
                        .details("Check the field(s) error")
                        .developerMessage(exception.getClass().getName())
                        .fields(fields)
                        .fieldsMessage(fieldsMessage)
                        .build(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ExceptionDetails exceptionDetails = ExceptionDetails.builder()
                .status(status.value())
                .title(ex.getCause().getMessage())
                .details(ex.getMessage())
                .developerMessage(ex.getClass().getName())
                .build();

        return new ResponseEntity<>(exceptionDetails, headers, status);
    }
}
