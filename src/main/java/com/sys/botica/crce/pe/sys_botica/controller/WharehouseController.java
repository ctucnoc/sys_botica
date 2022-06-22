package com.sys.botica.crce.pe.sys_botica.controller;

import java.util.List;

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
import com.sys.botica.crce.pe.sys_botica.dto.WharehouseDTO;
import com.sys.botica.crce.pe.sys_botica.dto.WharehouseTranferDTO;
import com.sys.botica.crce.pe.sys_botica.dto.request.WharehouseDTORequest;
import com.sys.botica.crce.pe.sys_botica.service.WharehouseService;
import com.sys.botica.crce.pe.sys_botica.service.WharehouseSubsidiaryService;

@RestController
@RequestMapping(SysBoticaConstant.API_VERSION + SysBoticaConstant.RESOURCE_WHAREHOUSES)
@CrossOrigin(origins = SysBoticaConstant.PATH_FROTEND_SYSCE)
public class WharehouseController {
	
	final WharehouseService wharehouseService;
	final WharehouseSubsidiaryService wharehouseSubsidiaryService;

	public WharehouseController(WharehouseService wharehouseService,WharehouseSubsidiaryService wharehouseSubsidiaryService) {
		this.wharehouseService = wharehouseService;
		this.wharehouseSubsidiaryService = wharehouseSubsidiaryService;
	}
	
	@GetMapping(SysBoticaConstant.RESOURCE_WHAREHOUSES_WHAREHOUSE)
	public ResponseEntity<?> findByDescription(@RequestParam String name, Pageable pageable) {
		return new ResponseEntity<>(this.wharehouseService.findByname(name,pageable), HttpStatus.OK);
	}

	@GetMapping(SysBoticaConstant.RESOURCE_WHAREHOUSES_WHAREHOUSE + SysBoticaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<?> findById(@PathVariable Long id) {
		return new ResponseEntity<>(this.wharehouseService.findById(id), HttpStatus.OK);
	}

	@PostMapping(SysBoticaConstant.RESOURCE_WHAREHOUSES_WHAREHOUSE)
	public ResponseEntity<?> save(@RequestBody WharehouseDTORequest dto) {
		return new ResponseEntity<>(this.wharehouseService.save(dto), HttpStatus.OK);
	}

	@PutMapping(SysBoticaConstant.RESOURCE_WHAREHOUSES_WHAREHOUSE + SysBoticaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<?> update(@RequestBody WharehouseDTORequest dto, @PathVariable Long id) {
		return new ResponseEntity<>(this.wharehouseService.update(dto, id), HttpStatus.OK);
	}

	@PatchMapping(SysBoticaConstant.RESOURCE_WHAREHOUSES_WHAREHOUSE + SysBoticaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<?> delete(@PathVariable Long id) {
		return new ResponseEntity<>(this.wharehouseService.delete(id), HttpStatus.OK);
	}	
	
	@GetMapping(SysBoticaConstant.RESOURCE_WHAREHOUSES_WHAREHOUSE + SysBoticaConstant.RESOURCE_FIND_ALL_DISTRIBUTION)
	public ResponseEntity<List<WharehouseDTO>> findByAllTypeDistribution(@PathVariable Long id){
		return ResponseEntity.ok(this.wharehouseSubsidiaryService.findAllWharehouse(id));
	}
	
	@GetMapping(SysBoticaConstant.RESOURCE_WHAREHOUSES_WHAREHOUSE + SysBoticaConstant.RESOURCE_FIND_ALL_TYPE)
	public ResponseEntity<WharehouseTranferDTO> findAllWharehouseTransfer(@PathVariable Long id){
		return ResponseEntity.ok(this.wharehouseSubsidiaryService.findAllWharehouseTransfer(id));
	}

}
