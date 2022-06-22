package com.sys.botica.crce.pe.sys_botica.controller;

import java.util.List;
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
import com.sys.botica.crce.pe.sys_botica.param.CategoryParam;
import com.sys.botica.crce.pe.sys_botica.service.CategoryService;

import lombok.extern.slf4j.Slf4j;

import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.dto.CategoryDTO;
import com.sys.botica.crce.pe.sys_botica.dto.request.CategoryDTORequest;

@Slf4j
@RestController
@RequestMapping(SysBoticaConstant.API_VERSION + SysBoticaConstant.RESOURCE_CATEGORYS)
@CrossOrigin(origins = SysBoticaConstant.PATH_FROTEND_SYSCE)
public class CategoryController {

	final CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@GetMapping(SysBoticaConstant.RESOURCE_CATEGORYS_CATEGORY)
	public ResponseEntity<?> findByDescription(CategoryParam params) {
		return new ResponseEntity<>(this.categoryService.findByDescription(params), HttpStatus.OK);
	}

	@GetMapping(SysBoticaConstant.RESOURCE_CATEGORYS_CATEGORY + SysBoticaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<?> findById(@PathVariable Integer id) {
		return new ResponseEntity<>(this.categoryService.findById(id), HttpStatus.OK);
	}

	@PostMapping(SysBoticaConstant.RESOURCE_CATEGORYS_CATEGORY)
	public ResponseEntity<?> save(@RequestBody CategoryDTORequest category) {
		return new ResponseEntity<>(this.categoryService.save(category), HttpStatus.OK);
	}

	@PutMapping(SysBoticaConstant.RESOURCE_CATEGORYS_CATEGORY + SysBoticaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<?> update(@RequestBody CategoryDTORequest category, @PathVariable Integer id) {
		return new ResponseEntity<>(this.categoryService.update(category, id), HttpStatus.OK);
	}

	@PatchMapping(SysBoticaConstant.RESOURCE_CATEGORYS_CATEGORY + SysBoticaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		return new ResponseEntity<>(this.categoryService.delete(id), HttpStatus.OK);
	}
	
	@GetMapping(SysBoticaConstant.RESOURCE_CATEGORYS_CATEGORY + SysBoticaConstant.RESOURCE_FIND_ALL)
	public ResponseEntity<List<CategoryDTO>> findAll(){
		log.info("crce controller findAll -> {} All Category");
		return new ResponseEntity<>(this.categoryService.findAll(),HttpStatus.OK);
	}

}
