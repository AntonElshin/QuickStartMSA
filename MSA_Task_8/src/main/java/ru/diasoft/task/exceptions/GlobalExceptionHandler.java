package ru.diasoft.task.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.diasoft.task.dto.BusinessErrorDTO;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity handleException(BusinessException businessException) {

        BusinessErrorDTO businessErrorDTO = new BusinessErrorDTO(businessException.getCode(), businessException.getMessage());

        if(businessException.getCode() < 50) {
            return new ResponseEntity<>(businessErrorDTO, HttpStatus.BAD_REQUEST);
        }
        else {
            return new ResponseEntity<>(businessErrorDTO, HttpStatus.NOT_FOUND);
        }

    }

}
