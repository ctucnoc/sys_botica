package com.sys.botica.crce.pe.sys_botica.controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;

@Slf4j
@RestController
@RequestMapping(SysBoticaConstant.API_VERSION + SysBoticaConstant.RESOURCE_REPORTS)
@CrossOrigin(origins = SysBoticaConstant.PATH_FROTEND_SYSCE)
public class ReportController {
	
	final
	ReportService reportService;
	
	final
	HttpServletResponse response;

	public ReportController(ReportService reportService,HttpServletResponse response) {
		this.reportService = reportService;
		this.response = response;
	}
	
	@GetMapping(SysBoticaConstant.RESOURCE_REPORTS_REPORT + SysBoticaConstant.RESOURCE_REPORT_SALE_X_DATE)
	public ResponseEntity<?> findByTicket(@RequestParam String dateFrom, @RequestParam String dateTo,@RequestHeader String Authorization ) throws IOException, JRException{
		log.info("generated report -> {} "+dateFrom + " " +dateTo);
		response.setContentType(MediaType.APPLICATION_PDF_VALUE);
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "TI01-000001" + SysBoticaConstant.FILE_EXT_PDF);
		OutputStream outputStream = response.getOutputStream();
		this.reportService.mkReportSaleDate(dateFrom, dateTo, Authorization, outputStream);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}	

}
