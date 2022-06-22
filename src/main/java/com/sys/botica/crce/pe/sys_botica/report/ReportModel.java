package com.sys.botica.crce.pe.sys_botica.report;

import java.io.IOException;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

public interface ReportModel {
	
	public JasperPrint mkReport(String name,String userId) throws JRException,IOException;
	
	public JasperPrint mkReport(String name, Map<String, Object> prmt) throws JRException,IOException;

}

