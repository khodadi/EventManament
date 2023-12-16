package com.env.controller;

import com.basedata.generalcode.CodeException;
import com.form.OutputAPIForm;
import com.service.services.IMessageBundleSrv;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @Creator 12/16/2023
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/
@Slf4j
@RestControllerAdvice
public class GlobalControllerException   extends ResponseEntityExceptionHandler {
    @Autowired
    private IMessageBundleSrv messageBundleSrv;

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        OutputAPIForm error = new OutputAPIForm();
        error.setSuccess(false);
        error.getErrors().add(CodeException.INTERNAL_ERROR);
        log.error(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        OutputAPIForm error = new OutputAPIForm();
        error.setSuccess(false);
        error.getErrors().add(CodeException.INTERNAL_ERROR);
        log.error(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        OutputAPIForm error = new OutputAPIForm();
        error.setSuccess(false);
        error.getErrors().add(CodeException.INTERNAL_ERROR);
        log.error(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        OutputAPIForm error = new OutputAPIForm();
        error.setSuccess(false);
        error.getErrors().add(CodeException.INTERNAL_ERROR);
        log.error(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        OutputAPIForm error = new OutputAPIForm();
        error.setSuccess(false);
        error.getErrors().add(CodeException.INTERNAL_ERROR);
        log.error(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        OutputAPIForm error = new OutputAPIForm();
        error.setSuccess(false);
        error.getErrors().add(CodeException.INTERNAL_ERROR);
        log.error(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        OutputAPIForm error = new OutputAPIForm();
        error.setSuccess(false);
        error.getErrors().add(CodeException.INTERNAL_ERROR);
        log.error(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        OutputAPIForm error = new OutputAPIForm();
        error.setSuccess(false);
        error.getErrors().add(CodeException.INTERNAL_ERROR);
        log.error(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        OutputAPIForm error = new OutputAPIForm();
        error.setSuccess(false);
        error.getErrors().add(CodeException.INTERNAL_ERROR);
        log.error(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        OutputAPIForm error = new OutputAPIForm();
        error.setSuccess(false);
        error.getErrors().add(CodeException.INTERNAL_ERROR);
        log.error(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleErrorResponseException(ErrorResponseException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        OutputAPIForm error = new OutputAPIForm();
        error.setSuccess(false);
        error.getErrors().add(CodeException.INTERNAL_ERROR);
        log.error(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        OutputAPIForm error = new OutputAPIForm();
        error.setSuccess(false);
        error.getErrors().add(CodeException.INTERNAL_ERROR);
        log.error(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        OutputAPIForm error = new OutputAPIForm();
        error.setSuccess(false);
        error.getErrors().add(CodeException.INTERNAL_ERROR);
        log.error(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        OutputAPIForm error = new OutputAPIForm();
        error.setSuccess(false);
        error.getErrors().add(CodeException.INTERNAL_ERROR);
        error.setMessage(ex.getMessage());
        log.error(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        OutputAPIForm error = new OutputAPIForm();
        error.setSuccess(false);
        error.getErrors().add(CodeException.INTERNAL_ERROR);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        OutputAPIForm error = new OutputAPIForm();
        error.setSuccess(false);
        error.getErrors().add(CodeException.INTERNAL_ERROR);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> createResponseEntity(Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        OutputAPIForm error = new OutputAPIForm();
        error.setSuccess(false);
        error.getErrors().add(CodeException.INTERNAL_ERROR);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
