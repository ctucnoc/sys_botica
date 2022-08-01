package com.sys.botica.crce.pe.sys_botica.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@Configuration
@NoArgsConstructor
@AllArgsConstructor
public class MailConfig {

	@Value("${spring.mail.username}")
	private String remitente;

}
