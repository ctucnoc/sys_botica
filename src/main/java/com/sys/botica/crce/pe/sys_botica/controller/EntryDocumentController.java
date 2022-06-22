package com.sys.botica.crce.pe.sys_botica.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.dto.EntryDocumentDTO;
import com.sys.botica.crce.pe.sys_botica.service.EntryDocumentService;

@RestController
@RequestMapping(SysBoticaConstant.API_VERSION + SysBoticaConstant.RESOURCE_ENTRYDOCUMENTS)
@CrossOrigin(origins = SysBoticaConstant.PATH_FROTEND_SYSCE)
public class EntryDocumentController {
	
	final EntryDocumentService documentService;

	public EntryDocumentController(EntryDocumentService documentService) {
		this.documentService = documentService;
	}
	
	@GetMapping(SysBoticaConstant.RESOURCE_ENTRYDOCUMENTS_ENTRYDOCUMENT)
	public ResponseEntity<List<EntryDocumentDTO>> findAll() {
		return new ResponseEntity<>(this.documentService.findAll(), HttpStatus.OK);
	}
	

}
