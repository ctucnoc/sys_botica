package com.sys.botica.crce.pe.sys_botica.errorhandler;

public class SysBoticaEntityUnprocessableException extends RuntimeException{

    public SysBoticaEntityUnprocessableException() {
        super();
    }

    public SysBoticaEntityUnprocessableException(String message, Throwable ex)	{
        super(message, ex);
    }

    public SysBoticaEntityUnprocessableException(String message) {
        super(message);
    }

    public SysBoticaEntityUnprocessableException(Throwable ex) {
        super(ex);
    }
}
