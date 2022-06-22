package com.sys.botica.crce.pe.sys_botica.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.dto.TypeDocumentDTO;
import com.sys.botica.crce.pe.sys_botica.service.TypeDocumentService;

@RestController
@RequestMapping(SysBoticaConstant.API_VERSION + SysBoticaConstant.RESOURCE_TYPEDOCUMENTS)
@CrossOrigin(origins = SysBoticaConstant.PATH_FROTEND_SYSCE)
public class TypeDocumentController {

	final TypeDocumentService documentService;

	public TypeDocumentController(TypeDocumentService documentService) {
		this.documentService = documentService;
	}

	@GetMapping(SysBoticaConstant.RESOURCE_TYPEDOCUMENTS_TYPEDOCUMENT)
	public ResponseEntity<List<TypeDocumentDTO>> findAll() {
		return ResponseEntity.ok(this.documentService.findAll());
	}

}
