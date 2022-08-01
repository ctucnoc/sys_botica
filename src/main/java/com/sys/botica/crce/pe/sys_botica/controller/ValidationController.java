package com.sys.botica.crce.pe.sys_botica.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.service.ValidationService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(SysBoticaConstant.API_VERSION + SysBoticaConstant.RESOURCE_VALIDATIONS)
@CrossOrigin(origins = SysBoticaConstant.PATH_FROTEND_SYSCE)
public class ValidationController {

	final
	ValidationService validationService;

	public ValidationController(ValidationService validationService) {
		this.validationService = validationService;
	}
	
	@PostMapping(SysBoticaConstant.RESOURCE_VALIDATIONS_VALIDATION + SysBoticaConstant.RESOURCE_VALIDATIONS_VALIDATION_SENDCODE)
	public void codeSendMail(@RequestParam String mail) {
		log.info("crce send mail -> {} "+mail);
		this.validationService.codeSendMail(mail);
	}
}
