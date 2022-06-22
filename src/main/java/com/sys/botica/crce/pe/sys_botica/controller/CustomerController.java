package com.sys.botica.crce.pe.sys_botica.controller;

import java.util.List;

import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import com.sys.botica.crce.pe.sys_botica.dto.CustomerDTO;
import com.sys.botica.crce.pe.sys_botica.dto.HrefEntityDTO;
import com.sys.botica.crce.pe.sys_botica.dto.request.CustomerDTORequest;
import com.sys.botica.crce.pe.sys_botica.service.CustomerService;

@RestController
@RequestMapping(SysBoticaConstant.API_VERSION + SysBoticaConstant.RESOURCE_CUSTOMERS)
@CrossOrigin(origins = SysBoticaConstant.PATH_FROTEND_SYSCE)
public class CustomerController {

	final 
	CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@GetMapping(SysBoticaConstant.RESOURCE_CUSTOMERS_CUSTOMER)
	public ResponseEntity<Page<CustomerDTO>> findByKeyWord(@RequestParam String key_word, Pageable pageable) {
		return ResponseEntity.ok(this.customerService.findByWordKey(key_word, pageable));
	}

	@PostMapping(SysBoticaConstant.RESOURCE_CUSTOMERS_CUSTOMER)
	public ResponseEntity<HrefEntityDTO> save(@RequestBody CustomerDTORequest dto) {
		return ResponseEntity.ok(this.customerService.save(dto));
	}

	@PutMapping(SysBoticaConstant.RESOURCE_CUSTOMERS_CUSTOMER + SysBoticaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<HrefEntityDTO> update(@Valid @RequestBody CustomerDTORequest dto, @PathVariable Long id) {
		return ResponseEntity.ok(this.customerService.update(dto, id));
	}

	@PatchMapping(SysBoticaConstant.RESOURCE_CUSTOMERS_CUSTOMER + SysBoticaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<HrefEntityDTO> delete(@PathVariable Long id) {
		return ResponseEntity.ok(this.customerService.delete(id));
	}
	
	@GetMapping(SysBoticaConstant.RESOURCE_CUSTOMERS_CUSTOMER + SysBoticaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<CustomerDTO> findById(@PathVariable Long id) {
		return ResponseEntity.ok(this.customerService.findById(id));
	}
	
	@GetMapping(SysBoticaConstant.RESOURCE_CUSTOMERS_CUSTOMER + SysBoticaConstant.RESOURCE_GENERIC_NRO_DOCUMENT)
	public ResponseEntity<CustomerDTO> findByNroDocument(@PathVariable String nro_document){
		return ResponseEntity.ok(this.customerService.findByNroDocument(nro_document));
	}
	
	@GetMapping(SysBoticaConstant.RESOURCE_CUSTOMERS_CUSTOMER + SysBoticaConstant.RESOURCE_GENERIC_FILTER)
	public ResponseEntity<List<CustomerDTO>> findByKeyWordSQL(@RequestParam String key_word) {
		return ResponseEntity.ok(this.customerService.findByWordKeySQL(key_word));
	}

}
