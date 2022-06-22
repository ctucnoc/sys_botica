package com.sys.botica.crce.pe.sys_botica.errorhandler;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.List;

@Data
public class SysBoticaError {

    private String code;
    private HttpStatus httpStatus;
    private Date timestamp;
    private String message;
    private String debugMessage;
    private List<SysBoticaSubError> subErrors;

    private SysBoticaError() {
        timestamp = new Date();
    }

    public SysBoticaError(HttpStatus httpStatus, String code) {
        this();
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public SysBoticaError(HttpStatus httpStatus, String code, Throwable e) {
        this();
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = "Unexpected error";
        this.debugMessage = e.getLocalizedMessage();
    }

    public SysBoticaError(HttpStatus httpStatus, String code, String message, Throwable e) {
        this();
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
        this.debugMessage = e.getLocalizedMessage();
    }
}
