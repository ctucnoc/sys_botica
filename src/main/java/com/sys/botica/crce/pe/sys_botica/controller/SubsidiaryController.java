package com.sys.botica.crce.pe.sys_botica.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.dto.SubsidiaryDTO;
import com.sys.botica.crce.pe.sys_botica.service.SubsidiaryService;

@RestController
@RequestMapping(SysBoticaConstant.API_VERSION + SysBoticaConstant.RESOURCE_SUBSIDIARYS)
@CrossOrigin(origins = SysBoticaConstant.PATH_FROTEND_SYSCE)
public class SubsidiaryController {
	
	final 
	SubsidiaryService subsidiaryService;

	public SubsidiaryController(SubsidiaryService subsidiaryService) {
		this.subsidiaryService = subsidiaryService;
	}
	
	@GetMapping(SysBoticaConstant.RESOURCE_SUBSIDIARYS_SUBSIDIARY)
	public ResponseEntity<?> findByName(@RequestParam String name,Pageable pageable) {
		return new ResponseEntity<>(this.subsidiaryService.findByName(name,pageable), HttpStatus.OK);
	}
	
	@GetMapping(SysBoticaConstant.RESOURCE_SUBSIDIARYS_SUBSIDIARY + SysBoticaConstant.RESOURCE_FIND_ALL)
	public ResponseEntity<List<SubsidiaryDTO>> findAll(){
		return new ResponseEntity<>(this.subsidiaryService.findAll(),HttpStatus.OK);
	}
	
	@GetMapping(SysBoticaConstant.RESOURCE_SUBSIDIARYS_SUBSIDIARY + SysBoticaConstant.RESOURCE_GENERIC_AUTO_COMPLETE)
	public ResponseEntity<List<SubsidiaryDTO>> findByAutoCompleteName(@RequestParam String name){
		return new ResponseEntity<>(this.subsidiaryService.findByName(name),HttpStatus.OK);
	}

}
