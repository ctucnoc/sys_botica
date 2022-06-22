package com.sys.botica.crce.pe.sys_botica.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnterpriseDTO {
	private Long id;
	private String bussinesname;
	private String tradename;
	private String ruc;
	private String address;
	private String email;
	private String webpage;
}
