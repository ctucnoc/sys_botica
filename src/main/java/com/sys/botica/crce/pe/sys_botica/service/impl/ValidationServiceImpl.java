package com.sys.botica.crce.pe.sys_botica.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.sys.botica.crce.pe.sys_botica.config.MailConfig;
import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.dto.request.EmailDataRequestDTO;
import com.sys.botica.crce.pe.sys_botica.dto.request.SendEmailRequestDTO;
import com.sys.botica.crce.pe.sys_botica.errorhandler.SysBoticaEntityNotFoundException;
import com.sys.botica.crce.pe.sys_botica.errorhandler.SysBoticaGenericClientException;
import com.sys.botica.crce.pe.sys_botica.model.HtmlTemplate;
import com.sys.botica.crce.pe.sys_botica.repository.HtmlTemplateRepository;
import com.sys.botica.crce.pe.sys_botica.service.MailService;
import com.sys.botica.crce.pe.sys_botica.service.ValidationService;
import com.sys.botica.crce.pe.sys_botica.util.SysBoticaUtil;

@Service
public class ValidationServiceImpl implements ValidationService{

	final
	HtmlTemplateRepository htmlTemplateRepository;
	
	final
	MailService mailService;
	
	final 
	SysBoticaUtil utilBoticaUtil;
	
	final
	MailConfig mailConfig;

	public ValidationServiceImpl(HtmlTemplateRepository htmlTemplateRepository, MailService mailService,SysBoticaUtil utilBoticaUtil,MailConfig mailConfig) {
		this.htmlTemplateRepository = htmlTemplateRepository;
		this.mailService = mailService;
		this.utilBoticaUtil = utilBoticaUtil;
		this.mailConfig = mailConfig;
	}

	@Override
	public void codeSendMail(String mail) {
		if(!utilBoticaUtil.validateEmail(mail.trim()))
			throw new SysBoticaGenericClientException("mail no valid", HttpStatus.BAD_REQUEST);
		HtmlTemplate bHtmlTemplate=this.htmlTemplateRepository.findByCodeAndState(SysBoticaConstant.HTML_SEND_EMAIL, SysBoticaConstant.STATE_ACTIVE).orElseThrow(()->new SysBoticaEntityNotFoundException("not found code"));
		SendEmailRequestDTO dto=new SendEmailRequestDTO();
		List<String> to=new ArrayList<>();
		to.add(mail);
		dto.setFrom(mailConfig.getRemitente());
		dto.setTo(to);
		dto.setSubject("CÓDIGO DE VERIFICACIÓN");
		dto.setHtmlTemplate(bHtmlTemplate.getContent());
		dto.setData(this.dataEmail("54321","CRISTIAN TUCNO CONDE"));
		this.mailService.sendEmail(dto);
		
	}
	
	private List<EmailDataRequestDTO> dataEmail(String codigoVerificacion,String name){
		List<EmailDataRequestDTO> data=new ArrayList<>();
		EmailDataRequestDTO dto=new EmailDataRequestDTO();
		dto.setKey("$P[CODE]");
		dto.setValue(codigoVerificacion);
		data.add(dto);
		
		EmailDataRequestDTO dto1=new EmailDataRequestDTO();
		dto1.setKey("$P[EMTERPRISE]");
		dto1.setValue(SysBoticaConstant.TITLE_NAME_ENTERPRISE);
		data.add(dto1);
		
		EmailDataRequestDTO dto2=new EmailDataRequestDTO();
		dto2.setKey("$P[FIRST_NAME]");
		dto2.setValue(name.toUpperCase());
		data.add(dto2);
		return data;
	}
	
	
}
