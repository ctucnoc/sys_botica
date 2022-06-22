package com.sys.botica.crce.pe.sys_botica.errorhandler;

public class SysBoticaEntityNotFoundException extends RuntimeException {

    public SysBoticaEntityNotFoundException() {
        super();
    }

    public SysBoticaEntityNotFoundException(String message, Throwable ex)	{
        super(message, ex);
    }

    public SysBoticaEntityNotFoundException(String message) {
        super(message);
    }

    public SysBoticaEntityNotFoundException(Throwable ex) {
        super(ex);
    }
}
