package com.sys.botica.crce.pe.sys_botica.controller;

import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@Slf4j
@Controller
@RestController
@RequestMapping(SysBoticaConstant.API_VERSION + SysBoticaConstant.RESOURCE_VAUCHERS)
@CrossOrigin(origins = SysBoticaConstant.PATH_FROTEND_SYSCE)
public class VaucherController {

	final
	ReportService reportService;
	
	final
	HttpServletResponse response;

	public VaucherController(ReportService reportService,HttpServletResponse response) {
		this.reportService = reportService;
		this.response = response;
	}
	
	@GetMapping(SysBoticaConstant.RESOURCE_VAUCHERS_VAUCHER + SysBoticaConstant.RESOURCE_VAUCHERS_VAUCHER_TICKET)
	public ResponseEntity<?> findByTicket(@PathVariable Long idSale) throws IOException, JRException{
		log.info("resend vauchee -> {} "+idSale);
		response.setContentType(MediaType.APPLICATION_PDF_VALUE);
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "TI01-000001" + SysBoticaConstant.FILE_EXT_PDF);
		OutputStream outputStream = response.getOutputStream();
		this.reportService.mkReportVoucher(SysBoticaConstant.RESOURCE_VAUCHER_TICKET, idSale, outputStream);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
}
