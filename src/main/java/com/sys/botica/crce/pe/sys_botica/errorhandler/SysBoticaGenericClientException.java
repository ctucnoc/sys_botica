package com.sys.botica.crce.pe.sys_botica.errorhandler;

import org.springframework.http.HttpStatus;

import java.util.List;

public class SysBoticaGenericClientException extends RuntimeException {

    private HttpStatus httpStatus;
    private List<SysBoticaSubError> subErrors;

    public SysBoticaGenericClientException() {
        super();
    }

    public SysBoticaGenericClientException(String message, Throwable ex) {
        super(message, ex);
    }

    public SysBoticaGenericClientException(String message) {
        super(message);
    }

    public SysBoticaGenericClientException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public SysBoticaGenericClientException(Throwable ex) {
        super(ex);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public List<SysBoticaSubError> getSubErrors() {
        return subErrors;
    }

    public void setSubErrors(List<SysBoticaSubError> subErrors) {
        this.subErrors = subErrors;
    }

    @Override
    public String toString() {
        return "CivilAgreementGenericClientException{" +
                "httpStatus=" + httpStatus +
                ", subErrors=" + subErrors +
                '}';
    }
}
