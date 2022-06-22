package com.sys.botica.crce.pe.sys_botica.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.dto.request.AuthorityDTORequest;
import com.sys.botica.crce.pe.sys_botica.service.AuthorityService;

@RestController
@RequestMapping(SysBoticaConstant.API_VERSION + SysBoticaConstant.RESOURCE_AUTHORITYS)
@CrossOrigin(origins = SysBoticaConstant.PATH_FROTEND_SYSCE)
public class AuthorityController {

	final AuthorityService authorityService;

	public AuthorityController(AuthorityService authorityService) {
		this.authorityService = authorityService;
	}

	@GetMapping(SysBoticaConstant.RESOURCE_AUTHORITYS_AUTHORITY)
	public ResponseEntity<?> findByName(@RequestParam String name, Pageable pageable) {
		return new ResponseEntity<>(this.authorityService.findByName(name, pageable), HttpStatus.OK);
	}

	@GetMapping(SysBoticaConstant.RESOURCE_AUTHORITYS_AUTHORITY + SysBoticaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<?> findById(@PathVariable Long id) {
		return new ResponseEntity<>(this.authorityService.findById(id), HttpStatus.OK);
	}

	@PostMapping(SysBoticaConstant.RESOURCE_AUTHORITYS_AUTHORITY)
	public ResponseEntity<?> save(@RequestBody AuthorityDTORequest dto) {
		return new ResponseEntity<>(this.authorityService.save(dto), HttpStatus.OK);
	}

	@PutMapping(SysBoticaConstant.RESOURCE_AUTHORITYS_AUTHORITY + SysBoticaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<?> update(@RequestBody AuthorityDTORequest dto, @PathVariable Long id) {
		return new ResponseEntity<>(this.authorityService.update(dto, id), HttpStatus.OK);
	}

	@PatchMapping(SysBoticaConstant.RESOURCE_AUTHORITYS_AUTHORITY + SysBoticaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<?> delete(@PathVariable Long id) {
		return new ResponseEntity<>(this.authorityService.delete(id), HttpStatus.OK);
	}

	@GetMapping(SysBoticaConstant.RESOURCE_AUTHORITYS_AUTHORITY +SysBoticaConstant.RESOURCE_AUTHORITYS_AUTHORITY_USER+ SysBoticaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<?> findAllByUser(@PathVariable Long id,Pageable pageable) {
		return ResponseEntity.ok(this.authorityService.findAllByUser(id,pageable));
	}

}
