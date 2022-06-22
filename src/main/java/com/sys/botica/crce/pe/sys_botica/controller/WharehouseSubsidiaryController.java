package com.sys.botica.crce.pe.sys_botica.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.dto.HrefEntityDTO;
import com.sys.botica.crce.pe.sys_botica.dto.WharehouseSubsidiaryDTO;
import com.sys.botica.crce.pe.sys_botica.dto.request.WharehouseSubsidiaryDTORequest;
import com.sys.botica.crce.pe.sys_botica.service.WharehouseSubsidiaryService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(SysBoticaConstant.API_VERSION + SysBoticaConstant.RESOURCE_WHAREHOUSESUBSIDIARYS)
@CrossOrigin(origins = SysBoticaConstant.PATH_FROTEND_SYSCE)
public class WharehouseSubsidiaryController {

	final
	WharehouseSubsidiaryService wharehouseSubsidiaryService;

	public WharehouseSubsidiaryController(WharehouseSubsidiaryService wharehouseSubsidiaryService) {
		this.wharehouseSubsidiaryService = wharehouseSubsidiaryService;
	}
	
	@GetMapping(SysBoticaConstant.RESOURCE_WHAREHOUSESUBSIDIARYS_WHAREHOUSESUBSIDIARY + SysBoticaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<Page<WharehouseSubsidiaryDTO>> findBySubsidiary(@PathVariable Long id,Pageable pageable) {
		log.info("crce controller findBySubsidiary -> {} "+id);
		return new ResponseEntity<>(this.wharehouseSubsidiaryService.findBySubsidiary(id,pageable), HttpStatus.OK);
	}
	
	@PostMapping(SysBoticaConstant.RESOURCE_WHAREHOUSESUBSIDIARYS_WHAREHOUSESUBSIDIARY)
	public ResponseEntity<HrefEntityDTO> save(@RequestBody WharehouseSubsidiaryDTORequest dto) {
		log.info("crce controller save -> {} "+dto);
		return new ResponseEntity<>(this.wharehouseSubsidiaryService.save(dto), HttpStatus.OK);
	}
	
	@PatchMapping(SysBoticaConstant.RESOURCE_WHAREHOUSESUBSIDIARYS_WHAREHOUSESUBSIDIARY + SysBoticaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<HrefEntityDTO> delete(@PathVariable Long id) {
		return new ResponseEntity<>(this.wharehouseSubsidiaryService.delete(id), HttpStatus.OK);
	}
	
}
