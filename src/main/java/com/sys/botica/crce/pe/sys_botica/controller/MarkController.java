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
import org.springframework.web.bind.annotation.RestController;
import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.dto.request.MarkDTORequest;
import com.sys.botica.crce.pe.sys_botica.service.MarkService;

@RestController
@RequestMapping(SysBoticaConstant.API_VERSION + SysBoticaConstant.RESOURCE_MARKS)
@CrossOrigin(origins = SysBoticaConstant.PATH_FROTEND_SYSCE)
public class MarkController {

	final MarkService markService;

	public MarkController(MarkService markService) {
		this.markService = markService;
	}

	@GetMapping(SysBoticaConstant.RESOURCE_MARKS_MARK)
	public ResponseEntity<?> findAll(String key_word,Pageable pageable) {
		return new ResponseEntity<>(this.markService.findAll(key_word,pageable), HttpStatus.OK);
	}
	
	@GetMapping(SysBoticaConstant.RESOURCE_MARKS_MARK + SysBoticaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<?> findById(@PathVariable Integer id) {
		return new ResponseEntity<>(this.markService.findById(id), HttpStatus.OK);
	}

	@PostMapping(SysBoticaConstant.RESOURCE_MARKS_MARK)
	public ResponseEntity<?> save(@RequestBody MarkDTORequest mark) {
		return new ResponseEntity<>(this.markService.save(mark), HttpStatus.OK);
	}

	@PutMapping(SysBoticaConstant.RESOURCE_MARKS_MARK + SysBoticaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<?> update(@RequestBody MarkDTORequest mark, @PathVariable Integer id) {
		return new ResponseEntity<>(this.markService.update(mark, id), HttpStatus.OK);
	}

	@PatchMapping(SysBoticaConstant.RESOURCE_MARKS_MARK + SysBoticaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		return new ResponseEntity<>(this.markService.delete(id), HttpStatus.OK);
	}

}
