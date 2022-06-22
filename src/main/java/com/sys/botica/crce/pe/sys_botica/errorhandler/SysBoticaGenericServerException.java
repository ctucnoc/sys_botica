package com.sys.botica.crce.pe.sys_botica.errorhandler;

public class SysBoticaGenericServerException extends RuntimeException {

	private String code;

	public SysBoticaGenericServerException() {
		super();
	}

	public SysBoticaGenericServerException(String message, Throwable ex) {
		super(message, ex);
	}

	public SysBoticaGenericServerException(String message) {
		super(message);
	}

	public SysBoticaGenericServerException(String message, String code) {
		super(message);
		this.code = code;
	}

	public SysBoticaGenericServerException(Throwable ex) {
		super(ex);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
