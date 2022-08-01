package com.sys.botica.crce.pe.sys_botica.dto.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SendEmailRequestDTO {
	private String from;
	private List<String> to;
	private String subject;
	private String htmlTemplate;
	private List<EmailDataRequestDTO> data;
}
