package com.sys.botica.crce.pe.sys_botica.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.dto.ProofPaymentDTO;
import com.sys.botica.crce.pe.sys_botica.service.ProofPaymentService;

@RestController
@RequestMapping(SysBoticaConstant.API_VERSION + SysBoticaConstant.RESOURCE_PROOFPAYMENTS)
@CrossOrigin(origins = SysBoticaConstant.PATH_FROTEND_SYSCE)
public class ProofPaymentController {

	final
	ProofPaymentService proofPaymentService;

	public ProofPaymentController(ProofPaymentService proofPaymentService) {
		this.proofPaymentService = proofPaymentService;
	}
	
	@GetMapping(SysBoticaConstant.RESOURCE_PROOFPAYMENTS_PROOFPAYMENT)
	public ResponseEntity<List<ProofPaymentDTO>> findAll() {
		return new ResponseEntity<>(this.proofPaymentService.findAll(), HttpStatus.OK);
	}	
	
}
