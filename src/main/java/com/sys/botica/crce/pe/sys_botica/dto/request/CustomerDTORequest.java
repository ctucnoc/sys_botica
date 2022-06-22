package com.sys.botica.crce.pe.sys_botica.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerDTORequest {
	private String numberDocument;
	private String firstName;
	private String lastName;
	private String bussinesName;
	private String email;
	private String direcction;
	private Long idTypeDocument;
}
