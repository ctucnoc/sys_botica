package com.sys.botica.crce.pe.sys_botica.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.dto.HrefEntityDTO;
import com.sys.botica.crce.pe.sys_botica.dto.request.EntryProductDTORequest;
import com.sys.botica.crce.pe.sys_botica.service.EntryProductService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(SysBoticaConstant.API_VERSION + SysBoticaConstant.RESOURCE_ENTRYPRODUCTS)
@CrossOrigin(origins = SysBoticaConstant.PATH_FROTEND_SYSCE)
public class EntryProductController {

	final EntryProductService entryProductService;

	public EntryProductController(EntryProductService entryProductService) {
		this.entryProductService = entryProductService;
	}
	
	@PostMapping(SysBoticaConstant.RESOURCE_ENTRYPRODUCTS_ENTRYPRODUCT)
	public ResponseEntity<HrefEntityDTO> save(@Valid @RequestBody EntryProductDTORequest dto,@RequestHeader String Authorization){
		log.info("crce-save -> {} " + dto + " "+Authorization);
		return new ResponseEntity<>(this.entryProductService.save(dto, Authorization),HttpStatus.OK);
	}
}
