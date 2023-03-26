package com.example.StartSPring.rest;


import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
@Order(1)
public class GlobalExceptionRestController extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                              HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        Response response = new Response(status.toString(),
                "Validation Failed",details);

        return new ResponseEntity(response, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Response> handleUserNotFoundException(Exception exception, WebRequest request) throws Exception {
        Response response = new Response("400",
                exception.getMessage(),request.getDescription(false));
        return new ResponseEntity(response, HttpStatus.NOT_FOUND);

    }
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Response> exceptionHandler(Exception exception,WebRequest request){
        Response response = new Response("500",
                exception.getMessage(),request.getDescription(false));
        return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
//    @Override
//    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        String error = "Malformed JSON request";
//        Response response = new Response("400",
//                error,request.getDescription(false));
//        return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
//
//    }


//    @ExceptionHandler({Exception.class})
//    public ResponseEntity<Response> handleMethodArgumentNotValid(Exception exception){
//        Response response = new Response("500",
//                exception.getMessage(),null);
//        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
//    }
//    @ExceptionHandler(Exception.class)
//    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
//        List<String> details = new ArrayList<>();
//        details.add(ex.getLocalizedMessage());
//        ErrorResponse error = new ErrorResponse("Server Error", details);
//        return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    @ExceptionHandler(RecordNotFoundException.class)
//    public final ResponseEntity<Object> handleUserNotFoundException(RecordNotFoundException ex, WebRequest request) {
//        List<String> details = new ArrayList<>();
//        details.add(ex.getLocalizedMessage());
//        ErrorResponse error = new ErrorResponse("Record Not Found", details);
//        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
//    }

}
