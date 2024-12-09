package by.clevertec.sakuuj.carshowroom.controller;

import by.clevertec.sakuuj.carshowroom.exception.EntityNotFoundException;
import lombok.Builder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionHandler {

    @Builder
    public record RestException(String message) {
    }

    @ExceptionHandler(exception = Exception.class)
    public ResponseEntity<RestException> handleUnhandled(Exception e) {

        RestException restException = RestException.builder()
                .message(e.getMessage())
                .build();

        return ResponseEntity.internalServerError()
                .body(restException);
    }

    @ExceptionHandler(exception = EntityNotFoundException.class)
    public ResponseEntity<Void> handleEntityNotFound(EntityNotFoundException e) {

        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(exception = MethodArgumentNotValidException.class)
    public ResponseEntity<RestException> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {

        RestException restException = RestException.builder()
                .message(
                        ex.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .map(RestExceptionHandler::getMethodArgNotValidFieldErrorInfo)
                                .collect(Collectors.joining())
                )
                .build();

        return ResponseEntity.badRequest().body(restException);
    }

    private static String getMethodArgNotValidFieldErrorInfo(FieldError fieldError) {

        return fieldError.getObjectName() + "." + fieldError.getField() + " " + fieldError.getDefaultMessage();
    }
}
