package com.sys.botica.crce.pe.sys_botica.controller;

import java.util.List;

import javax.validation.Valid;

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
import com.sys.botica.crce.pe.sys_botica.dto.ProductDTO;
import com.sys.botica.crce.pe.sys_botica.dto.request.ProductDTORequest;
import com.sys.botica.crce.pe.sys_botica.service.ProductService;

@RestController
@RequestMapping(SysBoticaConstant.API_VERSION + SysBoticaConstant.RESOURCE_PRODUCTS)
@CrossOrigin(origins = SysBoticaConstant.PATH_FROTEND_SYSCE)
public class ProductController {

	final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping(SysBoticaConstant.RESOURCE_PRODUCTS_PRODUCT)
	public ResponseEntity<?> findByKeyWord(String key_word, Pageable pageable) {
		return new ResponseEntity<>(this.productService.findByKeyWord(key_word, pageable), HttpStatus.OK);
	}

	@GetMapping(SysBoticaConstant.RESOURCE_PRODUCTS_PRODUCT + SysBoticaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<?> findByKeyWord(@PathVariable Long id) {
		return new ResponseEntity<>(this.productService.findByID(id), HttpStatus.OK);
	}

	@PostMapping(SysBoticaConstant.RESOURCE_PRODUCTS_PRODUCT)
	public ResponseEntity<?> save(@Valid @RequestBody ProductDTORequest product) {
		return new ResponseEntity<>(this.productService.save(product), HttpStatus.OK);
	}

	@PutMapping(SysBoticaConstant.RESOURCE_PRODUCTS_PRODUCT + SysBoticaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<?> update(@Valid @RequestBody ProductDTORequest product, @PathVariable Long id) {
		return new ResponseEntity<>(this.productService.update(product, id), HttpStatus.OK);
	}

	@PatchMapping(SysBoticaConstant.RESOURCE_PRODUCTS_PRODUCT + SysBoticaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<?> delete(@PathVariable Long id) {
		return new ResponseEntity<>(this.productService.delete(id), HttpStatus.OK);
	}

	@GetMapping(SysBoticaConstant.RESOURCE_PRODUCTS_PRODUCT + SysBoticaConstant.RESOURCE_GENERIC_FILTER)
	public ResponseEntity<List<ProductDTO>> findByKeyWordSQL(@RequestParam String key_word) {
		return new ResponseEntity<>(this.productService.findByKeyWordSQL(key_word), HttpStatus.OK);
	}

}
