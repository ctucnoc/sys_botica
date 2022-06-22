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
import com.sys.botica.crce.pe.sys_botica.dto.request.TransferDTORequest;
import com.sys.botica.crce.pe.sys_botica.service.TransferService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(SysBoticaConstant.API_VERSION + SysBoticaConstant.RESOURCE_TRANSFERS)
@CrossOrigin(origins = SysBoticaConstant.PATH_FROTEND_SYSCE)
public class TransferController {

	final
	TransferService transferService;

	public TransferController(TransferService transferService) {
		this.transferService = transferService;
	}
	
	@PostMapping(SysBoticaConstant.RESOURCE_TRANSFERS_TRANSFER)
	public ResponseEntity<HrefEntityDTO> save(@Valid @RequestBody TransferDTORequest dto,@RequestHeader String Authorization){
		log.info("crce-save transfer -> {} " + dto + " "+Authorization);
		return new ResponseEntity<>(this.transferService.save(dto, Authorization),HttpStatus.OK);
	}
	
}
