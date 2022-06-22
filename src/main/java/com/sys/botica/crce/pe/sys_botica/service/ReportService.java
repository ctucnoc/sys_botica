package com.sys.botica.crce.pe.sys_botica.service;

import java.io.IOException;
import java.io.OutputStream;
import net.sf.jasperreports.engine.JRException;

public interface ReportService {
	public void mkReportVoucher(String name,Long idsale,OutputStream outputStream) throws JRException,IOException;
	public void mkReportSaleDate(String dateFrom, String dateTo,String token,OutputStream outputStream) throws JRException,IOException;
	
	public byte[] mkReporteTicket(String name,Long idsale) throws JRException,IOException;

}
