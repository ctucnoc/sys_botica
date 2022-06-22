package com.sys.botica.crce.pe.sys_botica.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.dto.DtSaleDTO;
import com.sys.botica.crce.pe.sys_botica.service.DtSaleService;

@RestController
@RequestMapping(SysBoticaConstant.API_VERSION + SysBoticaConstant.RESOURCE_DTSALES)
@CrossOrigin(origins = SysBoticaConstant.PATH_FROTEND_SYSCE)
public class DtSaleController {
	
	final
	DtSaleService dtSaleService;

	public DtSaleController(DtSaleService dtSaleService) {
		this.dtSaleService = dtSaleService;
	}
	
	@GetMapping(SysBoticaConstant.RESOURCE_DTSALES_DTSALE + SysBoticaConstant.RESOURCE_SALE_ID)
	public ResponseEntity<List<DtSaleDTO>> findBySale(@PathVariable Long idSale) {
		return new ResponseEntity<>(this.dtSaleService.findBySale(idSale), HttpStatus.OK);
	}	

}
