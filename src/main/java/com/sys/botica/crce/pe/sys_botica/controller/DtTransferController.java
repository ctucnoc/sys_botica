package com.sys.botica.crce.pe.sys_botica.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.dto.ProductSaleDTO;
import com.sys.botica.crce.pe.sys_botica.service.DtTransferService;

@RestController
@RequestMapping(SysBoticaConstant.API_VERSION + SysBoticaConstant.RESOURCE_DTTRANSFERS)
@CrossOrigin(origins = SysBoticaConstant.PATH_FROTEND_SYSCE)
public class DtTransferController {

	final 
	DtTransferService dtTransferService;

	public DtTransferController(DtTransferService dtTransferService) {
		this.dtTransferService = dtTransferService;
	}
	
	@GetMapping(SysBoticaConstant.RESOURCE_DTTRANSFERS_DTTRANSFER)
	public ResponseEntity<List<ProductSaleDTO>> findByWordKey(@RequestParam Long idsubsidiary,@RequestParam String key_word){
		return ResponseEntity.ok(this.dtTransferService.findByKeyWord(idsubsidiary, key_word));
	}

}
