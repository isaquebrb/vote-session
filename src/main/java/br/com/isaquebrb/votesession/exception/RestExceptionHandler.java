package br.com.isaquebrb.votesession.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(RuntimeException ex, WebRequest request) {

        StandardError body = StandardError.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .errorsList(Collections.singletonList(ErrorMessage.ENTITY_NOT_FOUND.getMessage()))
                .message(ex.getMessage())
                .build();

        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = DatabaseException.class)
    protected ResponseEntity<Object> handleDatabaseError(RuntimeException ex, WebRequest request) {

        StandardError body = StandardError.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .errorsList(Collections.singletonList(ErrorMessage.DATABASE_ERROR.getMessage()))
                .message(ex.getMessage())
                .build();

        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        List<String> errorList = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        StandardError body = StandardError.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .errorsList(errorList)
                .message(ex.getMessage())
                .build();

        return handleExceptionInternal(ex, body, headers, HttpStatus.BAD_REQUEST, request);
    }
}
