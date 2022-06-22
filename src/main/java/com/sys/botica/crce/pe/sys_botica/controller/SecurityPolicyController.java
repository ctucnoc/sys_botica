package com.sys.botica.crce.pe.sys_botica.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.dto.request.SecurityPolicyDTORequest;
import com.sys.botica.crce.pe.sys_botica.service.SecurityPolicyService;

@RestController
@RequestMapping(SysBoticaConstant.API_VERSION + SysBoticaConstant.RESOURCE_SECURITYPOLICYS)
@CrossOrigin(origins = SysBoticaConstant.PATH_FROTEND_SYSCE)
public class SecurityPolicyController {
	final SecurityPolicyService securityPolicyService;

	public SecurityPolicyController(SecurityPolicyService securityPolicyService) {
		this.securityPolicyService = securityPolicyService;
	}
	
	@GetMapping(SysBoticaConstant.RESOURCE_SECURITYPOLICYS_SECURITYPOLICY)
	public ResponseEntity<?> findByCode() {
		return new ResponseEntity<>(this.securityPolicyService.findByCode(), HttpStatus.OK);
	}
	
	@PutMapping(SysBoticaConstant.RESOURCE_SECURITYPOLICYS_SECURITYPOLICY + SysBoticaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<?> update(@RequestBody SecurityPolicyDTORequest dto,@PathVariable Long id){
		return new ResponseEntity<>(this.securityPolicyService.update(dto, id),HttpStatus.OK);
	}

}
