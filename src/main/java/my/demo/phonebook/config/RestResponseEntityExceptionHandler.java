package my.demo.phonebook.config;

import my.demo.phonebook.domain.ExceptionInfo;
import my.demo.phonebook.exceptions.ContentReadException;
import my.demo.phonebook.exceptions.CorreptedContactException;
import my.demo.phonebook.exceptions.EmptyFileException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(new ExceptionInfo(status,"Malformed JSON",ex),status);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity handleExcepion(Exception ex, WebRequest req) {
        ExceptionInfo exceptionInfo = new ExceptionInfo(HttpStatus.INTERNAL_SERVER_ERROR,"Something went wrong, please check",ex);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if(ex instanceof EmptyFileException) {
            exceptionInfo = new ExceptionInfo(HttpStatus.BAD_REQUEST,"Please Select a valid file",ex);
        } else if(ex instanceof ContentReadException) {
            exceptionInfo = new ExceptionInfo(HttpStatus.BAD_REQUEST,"Please check the file content format",ex);
        } else if (ex instanceof CorreptedContactException) {
            exceptionInfo = new ExceptionInfo(HttpStatus.BAD_REQUEST,"Corrupted file content",ex);
        }
        return new ResponseEntity(exceptionInfo,status);
    }
}
