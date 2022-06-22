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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.dto.ProviderDTO;
import com.sys.botica.crce.pe.sys_botica.dto.request.ProviderDTORequest;
import com.sys.botica.crce.pe.sys_botica.service.ProviderService;

@RestController
@RequestMapping(SysBoticaConstant.API_VERSION + SysBoticaConstant.RESOURCE_PROVIDERS)
@CrossOrigin(origins = SysBoticaConstant.PATH_FROTEND_SYSCE)
public class ProviderController {

	final ProviderService providerService;

	public ProviderController(ProviderService providerService) {
		this.providerService = providerService;
	}
	
	@GetMapping(SysBoticaConstant.RESOURCE_PROVIDERS_PROVIDER)
	public ResponseEntity<?> findAll(@RequestParam String name, Pageable pageable) {
		return new ResponseEntity<>(this.providerService.findByDescription(name, pageable), HttpStatus.OK);
	}

	@GetMapping(SysBoticaConstant.RESOURCE_PROVIDERS_PROVIDER + SysBoticaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<?> findById(@PathVariable Long id) {
		return new ResponseEntity<>(this.providerService.findById(id), HttpStatus.OK);
	}

	@PostMapping(SysBoticaConstant.RESOURCE_PROVIDERS_PROVIDER)
	public ResponseEntity<?> save(@RequestBody ProviderDTORequest dto) {
		return new ResponseEntity<>(this.providerService.save(dto), HttpStatus.OK);
	}

	@PutMapping(SysBoticaConstant.RESOURCE_PROVIDERS_PROVIDER + SysBoticaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<?> update(@RequestBody ProviderDTORequest dto, @PathVariable Long id) {
		return new ResponseEntity<>(this.providerService.update(dto, id), HttpStatus.OK);
	}

	@PatchMapping(SysBoticaConstant.RESOURCE_PROVIDERS_PROVIDER + SysBoticaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<?> delete(@PathVariable Long id) {
		return new ResponseEntity<>(this.providerService.delete(id), HttpStatus.OK);
	}
	
	@GetMapping(SysBoticaConstant.RESOURCE_PROVIDERS_PROVIDER + SysBoticaConstant.RESOURCE_FIND_ALL)
	public ResponseEntity<Page<ProviderDTO>> findAll(Pageable pageable){
		return new ResponseEntity<>(this.providerService.findAll(pageable),HttpStatus.OK);
	}
	
}
