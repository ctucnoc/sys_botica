package com.sys.botica.crce.pe.sys_botica.service;

import com.sys.botica.crce.pe.sys_botica.dto.request.SendEmailRequestDTO;

public interface MailService {
	public void sendEmail(SendEmailRequestDTO dto);
}
