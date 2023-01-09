package com.sys.botica.crce.pe.sys_botica.report;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

@Slf4j
@Repository
public class ReportModelImpl implements ReportModel {

	final DataSource dataSource;

	final ApplicationContext applicationContext;

	public ReportModelImpl(DataSource dataSource, ApplicationContext applicationContext) {
		this.dataSource = dataSource;
		this.applicationContext = applicationContext;
	}

	@Override
	public JasperPrint mkReport(String name, String userId) throws JRException, IOException {
		try {
			ReportGeneric generic = new ReportGeneric(this.dataSource.getConnection());
			generic.setReportParent(
					this.applicationContext.getResource("classpath:report" + File.separator + name + ".jrxml"));
			generic.setReportLogo("classpath:img" + File.separator + "logo.png");
			return generic.mkReport(name, userId);
		} catch (Exception e) {
			log.error("error : " + e.getMessage());
			return null;
		}
	}

	@Override
	public JasperPrint mkReport(String name, Map<String, Object> prmt) throws JRException, IOException {
		try {
			ReportGeneric generic = new ReportGeneric(this.dataSource.getConnection());
			generic.setReportParent(
					this.applicationContext.getResource("classpath:report" + File.separator + name + ".jrxml"));
			return generic.mkReport(name, prmt);
		} catch (Exception e) {
			log.error("error : " + e.getMessage());
			return null;
		}
	}

}
