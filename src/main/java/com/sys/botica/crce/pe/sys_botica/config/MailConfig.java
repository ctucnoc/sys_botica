package com.sys.botica.crce.pe.sys_botica.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class MailConfig {

	@Value("${spring.mail.username}")
	private String remitente;

}
