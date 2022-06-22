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
import com.sys.botica.crce.pe.sys_botica.dto.request.UnitDTORequest;
import com.sys.botica.crce.pe.sys_botica.service.UnitService;

@RestController
@RequestMapping(SysBoticaConstant.API_VERSION + SysBoticaConstant.RESOURCE_UNITS)
@CrossOrigin(origins = SysBoticaConstant.PATH_FROTEND_SYSCE)
public class UnitController {

	final UnitService unitService;

	public UnitController(UnitService unitService) {
		this.unitService = unitService;
	}

	@GetMapping(SysBoticaConstant.RESOURCE_UNITS_UNIT)
	public ResponseEntity<?> findAll(@RequestParam String key_word, Pageable pageable) {
		return new ResponseEntity<>(this.unitService.findByDescription(key_word, pageable), HttpStatus.OK);
	}

	@GetMapping(SysBoticaConstant.RESOURCE_UNITS_UNIT + SysBoticaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<?> findById(@PathVariable Integer id) {
		return new ResponseEntity<>(this.unitService.findById(id), HttpStatus.OK);
	}

	@PostMapping(SysBoticaConstant.RESOURCE_UNITS_UNIT)
	public ResponseEntity<?> save(@RequestBody UnitDTORequest unit) {
		return new ResponseEntity<>(this.unitService.save(unit), HttpStatus.OK);
	}

	@PutMapping(SysBoticaConstant.RESOURCE_UNITS_UNIT + SysBoticaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<?> update(@RequestBody UnitDTORequest unit, @PathVariable Integer id) {
		return new ResponseEntity<>(this.unitService.update(unit, id), HttpStatus.OK);
	}

	@PatchMapping(SysBoticaConstant.RESOURCE_UNITS_UNIT + SysBoticaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		return new ResponseEntity<>(this.unitService.delete(id), HttpStatus.OK);
	}

}
