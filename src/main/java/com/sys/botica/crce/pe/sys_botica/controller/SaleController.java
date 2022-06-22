package com.sys.botica.crce.pe.sys_botica.controller;

import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.dto.SaleDTO;
import com.sys.botica.crce.pe.sys_botica.dto.request.SaleDTORequest;
import com.sys.botica.crce.pe.sys_botica.param.SaleParam;
import com.sys.botica.crce.pe.sys_botica.service.SaleService;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;

@Slf4j
@RestController
@RequestMapping(SysBoticaConstant.API_VERSION + SysBoticaConstant.RESOURCE_SALES)
@CrossOrigin(origins = SysBoticaConstant.PATH_FROTEND_SYSCE)
public class SaleController {

	final 
	SaleService saleService;
	
	final
	HttpServletResponse response;

	public SaleController(SaleService saleService,HttpServletResponse response) {
		this.saleService = saleService;
		this.response = response;
	}
	
	@PostMapping(SysBoticaConstant.RESOURCE_SALES_SALE)
	public ResponseEntity<?> save(@Valid @RequestBody SaleDTORequest dto,@RequestHeader String Authorization)throws IOException, JRException{
		log.info(" crce save -> {} " + dto);
		response.setContentType(MediaType.APPLICATION_PDF_VALUE);
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "TI01-000001" + SysBoticaConstant.FILE_EXT_PDF);
		OutputStream outputStream = response.getOutputStream();
		this.saleService.save(dto, Authorization,outputStream);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@GetMapping(SysBoticaConstant.RESOURCE_SALES_SALE)
	public ResponseEntity<Page<SaleDTO>> findAllParams(SaleParam params,Pageable pageable){
		log.info(" crce findAllParams -> {} " + params);
		return ResponseEntity.ok(this.saleService.findByParameter(params, pageable));
	}
	
}
