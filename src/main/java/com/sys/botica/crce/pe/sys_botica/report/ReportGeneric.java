package com.sys.botica.crce.pe.sys_botica.report;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.springframework.http.HttpStatus;
import org.springframework.util.ResourceUtils;
import com.sys.botica.crce.pe.sys_botica.errorhandler.SysBoticaGenericClientException;
import lombok.extern.slf4j.Slf4j;
import java.io.File;

@Slf4j
public class ReportGeneric {

	private String reportParent;
	
	private String reportLogo;
	
	private Connection connection;

	public ReportGeneric(Connection connection) {
		this.connection = connection;
	}

	public JasperPrint mkReport(String name,String userId) throws JRException {
		JasperPrint jasperPrint = null;
		JasperReport jasperReport = null;
		Map<String, Object> parametro = null;
		try {
			File img_logo=ResourceUtils.getFile(getReportLogo());
			File file = ResourceUtils.getFile(getReportParent());
			jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
			parametro = new HashMap<>();
			parametro.put("REPORT_LOGO", img_logo.getAbsolutePath());
			System.out.println(img_logo.getAbsolutePath());
			parametro.put("REPORT_USER", userId);
			jasperPrint = JasperFillManager.fillReport(jasperReport, parametro, connection);
			connection.close();
		} catch (Exception e) {
			try {
				connection.close();
			} catch (Exception e2) {
				log.error(e2.getMessage());
				jasperPrint = null;
			}
			log.error("error : " + e.getMessage());
			jasperPrint = null;
		}
		return jasperPrint;
	}

	public JasperPrint mkReport(String name, Map<String, Object> prmt) {
		JasperPrint jasperPrint = null;
		JasperReport jasperReport = null;
		Map<String, Object> parametro = null;
		try {
			File file = ResourceUtils.getFile(getReportParent());
			jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
			parametro = prmt;
			jasperPrint = JasperFillManager.fillReport(jasperReport, parametro, connection);
			connection.close();
		} catch (Exception e) {
			try {
				connection.close();
			} catch (Exception e2) {
				log.error(e2.getMessage());
				jasperPrint = null;
			}
			log.error("error : " + e.getMessage());
			jasperPrint = null;
		}
		return jasperPrint;
	}

	public JasperPrint mkReport(String name, String[] keys, Object[] values) {
		JasperPrint jasperPrint = null;
		JasperReport jasperReport = null;
		Map<String, Object> parametro = null;
		try {
			File file = ResourceUtils.getFile(getReportParent());
			jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
			parametro = new HashMap<>();
			if (keys.length == values.length && keys != null && values != null) {
				for (int i = 0; i < values.length; i++) {
					parametro.put(keys[i], values[i]);
				}
			} else {
				connection.close();
				throw new SysBoticaGenericClientException(
						"Parametros Invalidos(Llaves y Valores cantidades diferentes) o Nulos", HttpStatus.BAD_REQUEST);
			}
			jasperPrint = JasperFillManager.fillReport(jasperReport, parametro, connection);
			connection.close();
		} catch (Exception e) {
			try {
				connection.close();
			} catch (Exception e2) {
				log.error(e2.getMessage());
				jasperPrint = null;
			}
			log.error("error : " + e.getMessage());
			jasperPrint = null;
		}
		return jasperPrint;
	}

	public String getReportParent() {
		return reportParent;
	}

	public void setReportParent(String reportParent) {
		this.reportParent = reportParent;
	}

	public String getReportLogo() {
		return reportLogo;
	}

	public void setReportLogo(String reportLogo) {
		this.reportLogo = reportLogo;
	}
	

}
