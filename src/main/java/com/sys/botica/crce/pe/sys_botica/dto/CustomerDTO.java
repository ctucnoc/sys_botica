package com.sys.botica.crce.pe.sys_botica.dto;

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
public class CustomerDTO {
	private Long id;
	private String numberDocument;
	private String firstName;
	private String lastName;
	private String bussinesName;
	private String email;
	private String direcction;
	private Long idTypeDocument;
}
