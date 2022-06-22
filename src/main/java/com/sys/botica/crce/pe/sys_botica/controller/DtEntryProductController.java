package com.sys.botica.crce.pe.sys_botica.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.dto.DtEntryProductDTO;
import com.sys.botica.crce.pe.sys_botica.service.DtEntryProductService;

@RestController
@RequestMapping(SysBoticaConstant.API_VERSION + SysBoticaConstant.RESOURCE_DTENTRYPRODUCTS)
@CrossOrigin(origins = SysBoticaConstant.PATH_FROTEND_SYSCE)
public class DtEntryProductController {

	final
	DtEntryProductService dtEntryProductService;

	public DtEntryProductController(DtEntryProductService dtEntryProductService) {
		this.dtEntryProductService = dtEntryProductService;
	}
	
	@GetMapping(SysBoticaConstant.RESOURCE_DTENTRYPRODUCTS_DTENTRYPRODUCT + SysBoticaConstant.RESOURCE_DTENTRYPRODUCT_FIND_IDWHAREHOUSE)
	public ResponseEntity<Page<DtEntryProductDTO>> find(@PathVariable Long id,Pageable pageable,@RequestParam String word_key){
		return ResponseEntity.ok(this.dtEntryProductService.findByIdWharehouse(id, word_key, pageable));
	}
}
