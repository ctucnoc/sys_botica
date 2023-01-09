package com.sys.botica.crce.pe.sys_botica.report;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import com.sys.botica.crce.pe.sys_botica.errorhandler.SysBoticaGenericClientException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReportGeneric {

	private Resource reportParent;

	private String reportLogo;

	private Connection connection;


	public ReportGeneric(Connection connection) {
		this.connection = connection;
	}

	public JasperPrint mkReport(String name, String userId) throws JRException {
		JasperPrint jasperPrint = null;
		JasperReport jasperReport = null;
		Map<String, Object> parametro = null;
		log.info("crce report -> {}"+getReportParent());
		try {
			log.info("crce report generic path img -> {} " + getReportLogo());
			// File img_logo=ResourceUtils.getFile(getReportLogo());
			// File file = ResourceUtils.getFile(getReportParent());
			jasperReport = JasperCompileManager.compileReport(getReportParent().getInputStream());
			parametro = new HashMap<>();
			parametro.put("REPORT_LOGO", getReportLogo());
			log.info("crce report generic -> {} " + getReportLogo());
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
			// File file = ResourceUtils.getFile(getReportParent());
			jasperReport = JasperCompileManager.compileReport(getReportParent().getInputStream());
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
			// File file = ResourceUtils.getFile(getReportParent());
			jasperReport = JasperCompileManager.compileReport(getReportParent().getInputStream());
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

	public Resource getReportParent() {
		return reportParent;
	}

	public void setReportParent(Resource reportParent) {
		this.reportParent = reportParent;
	}

	public String getReportLogo() {
		return reportLogo;
	}

	public void setReportLogo(String reportLogo) {
		this.reportLogo = reportLogo;
	}

}
