package com.personapiv2.demo.infra;

import com.personapiv2.demo.dto.exception.ExceptionDTO;
import com.personapiv2.demo.exception.AddressNotFoundException;
import com.personapiv2.demo.exception.PersonNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionDTO> throwsInvalidAttribute(HttpMessageNotReadableException exception) {
        String message = exception.getMessage();
        String problem = message.substring(message.indexOf("problem: ") + "problem: ".length());
        ExceptionDTO exceptionDTO = new ExceptionDTO(problem, HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.badRequest().body(exceptionDTO);
    }

    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<ExceptionDTO> throwsPersonNotFound(PersonNotFoundException exception) {
        ExceptionDTO exceptionDTO = new ExceptionDTO(exception.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionDTO);
    }

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<ExceptionDTO> throwsAddressNotFound(AddressNotFoundException exception) {
        ExceptionDTO exceptionDTO = new ExceptionDTO(exception.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionDTO);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDTO> throwsGeneralException(Exception exception) {
        ExceptionDTO exceptionDTO = new ExceptionDTO(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.badRequest().body(exceptionDTO);
    }
}
