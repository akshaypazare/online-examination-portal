package com.onlineexaminationportal.exception;

import com.onlineexaminationportal.dto.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice  //wherever the exception happens in the project, the object of that exception will be given to this class by Spring boot
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

//specific exception
    @ExceptionHandler(ResourceNotFoundException.class) //ResourceNotFoundException will be by passed here
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(
            ResourceNotFoundException exception,
            WebRequest webRequest      //this class has lo
    ) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
                webRequest.getDescription(false)) ;  //this will generate Uri details

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    // global exceptions
    @ExceptionHandler(Exception.class ) //All the Exception will be by passed here
    public ResponseEntity<ErrorDetails> handleAllException(
            Exception exception,
            WebRequest webRequest
    ){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
                webRequest.getDescription(false)) ;
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
