package com.sys.botica.crce.pe.sys_botica.errorhandler;

public class SysBoticaUnauthorizedException extends RuntimeException{

    public SysBoticaUnauthorizedException() {
        super();
    }

    public SysBoticaUnauthorizedException(String message, Throwable ex)	{
        super(message, ex);
    }

    public SysBoticaUnauthorizedException(String message) {
        super(message);
    }

    public SysBoticaUnauthorizedException(Throwable ex) {
        super(ex);
    }
}
