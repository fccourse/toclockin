package digital.ilia.toclockinapi.exceptions.handler;

import digital.ilia.toclockinapi.dtos.response.Error;
import digital.ilia.toclockinapi.dtos.response.ResponseErrors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ControllerAdviceHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String errorMessage = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
        List<Error> errors = List.of(new Error("property in invalid format.", errorMessage));
        return handleExceptionInternal(ex, new ResponseErrors(errors), headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<Error> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(field -> {
            String userMessage = field.getField() + " " + field.getDefaultMessage();
            String errorMessage = field.toString();
            errors.add(new Error(userMessage, errorMessage));
        });

        return handleExceptionInternal(ex, new ResponseErrors(errors), headers, HttpStatus.BAD_REQUEST, request);
    }
}