package com.sys.botica.crce.pe.sys_botica.service.impl;

import java.util.List;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.sys.botica.crce.pe.sys_botica.dto.request.SendEmailRequestDTO;
import com.sys.botica.crce.pe.sys_botica.service.MailService;

@Service
public class MailServiceImpl implements MailService {

	final 
	JavaMailSender javaMailSender;

	public MailServiceImpl(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	@Override
	public void sendEmail(SendEmailRequestDTO dto) {
		MimeMessage message = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
			helper.setFrom(dto.getFrom());
			helper.setTo(getTo(dto.getTo()));
			String htmlContent = dto.getHtmlTemplate();
			if (dto.getData() != null) {
				for (int i = 0; i < dto.getData().size(); i++) {
					String key = dto.getData().get(i).getKey();
					String value = dto.getData().get(i).getValue();
					htmlContent = htmlContent.replace(key, value);
				}
			}
			helper.setSubject(dto.getSubject());
			helper.setText(htmlContent, true);
			this.javaMailSender.send(message);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public String[] getTo(List<String> list) {
		String[] array = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			array[i] = list.get(i);
		}
		return array;
	}
}
