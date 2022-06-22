package com.sys.botica.crce.pe.sys_botica.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.dto.request.UserSubsidiaryDTORequest;
import com.sys.botica.crce.pe.sys_botica.service.UserSubsidiaryService;

@RestController
@RequestMapping(SysBoticaConstant.API_VERSION + SysBoticaConstant.RESOURCE_USERSUBSIDIARYS)
@CrossOrigin(origins = SysBoticaConstant.PATH_FROTEND_SYSCE)
public class UserSubsidiaryController {

	final UserSubsidiaryService userSubsidiaryService;

	public UserSubsidiaryController(UserSubsidiaryService userSubsidiaryService) {
		this.userSubsidiaryService = userSubsidiaryService;
	}

	@PatchMapping(SysBoticaConstant.RESOURCE_USERSUBSIDIARYS_USERSUBSIDIARY + SysBoticaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<?> delete(@PathVariable Long id) {
		return new ResponseEntity<>(this.userSubsidiaryService.delete(id), HttpStatus.OK);
	}

	@PostMapping(SysBoticaConstant.RESOURCE_USERSUBSIDIARYS_USERSUBSIDIARY)
	public ResponseEntity<?> save(@RequestBody UserSubsidiaryDTORequest userSubsidiary) {
		System.out.println(userSubsidiary.getIdSubsidiary());
		return new ResponseEntity<>(this.userSubsidiaryService.save(userSubsidiary), HttpStatus.OK);
	}

	@GetMapping(SysBoticaConstant.RESOURCE_USERSUBSIDIARYS_USERSUBSIDIARY + SysBoticaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<?> findByUserAllEnterprise(@PathVariable Long id, Pageable pageable) {
		return new ResponseEntity<>(this.userSubsidiaryService.findByUserAllSubsidiary(id, pageable), HttpStatus.OK);
	}
	
	@GetMapping(SysBoticaConstant.RESOURCE_USERSUBSIDIARYS_USERSUBSIDIARY)
	public ResponseEntity<?> findByUser(@RequestParam String username) {
		return new ResponseEntity<>(this.userSubsidiaryService.findByUser(username.trim()), HttpStatus.OK);
	}

}
