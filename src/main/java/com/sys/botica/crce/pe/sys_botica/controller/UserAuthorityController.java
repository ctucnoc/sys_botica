package com.sys.botica.crce.pe.sys_botica.controller;

import org.springframework.data.domain.Pageable;
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
import com.sys.botica.crce.pe.sys_botica.dto.request.UserAuthorityDTORequest;
import com.sys.botica.crce.pe.sys_botica.service.UserAuthorityService;

@RestController
@RequestMapping(SysBoticaConstant.API_VERSION + SysBoticaConstant.RESOURCE_USERAUTHORITYS)
@CrossOrigin(origins = SysBoticaConstant.PATH_FROTEND_SYSCE)
public class UserAuthorityController {
	final UserAuthorityService userAuthorityService;

	public UserAuthorityController(UserAuthorityService userAuthorityService) {
		this.userAuthorityService = userAuthorityService;
	}
	
	@GetMapping(SysBoticaConstant.RESOURCE_USERAUTHORITYS_USERAUTHORITY + SysBoticaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<?> findByUser(@PathVariable Long id, Pageable pageable) {
		return ResponseEntity.ok(this.userAuthorityService.findByUser(id, pageable));
	}
	
	@PostMapping(SysBoticaConstant.RESOURCE_USERAUTHORITYS_USERAUTHORITY)
	public ResponseEntity<?> save(@RequestBody UserAuthorityDTORequest dto){
		return ResponseEntity.ok(this.userAuthorityService.save(dto));
	}
	
	@PatchMapping(SysBoticaConstant.RESOURCE_USERAUTHORITYS_USERAUTHORITY + SysBoticaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<?> delete(@PathVariable Long id) {
		return ResponseEntity.ok(this.userAuthorityService.delete(id));
	}
	
}
