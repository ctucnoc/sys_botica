package com.sys.botica.crce.pe.sys_botica.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProviderDTORequest {
	private String bussinesname;
	private String tradename;
	private String ruc;
	private String address;
	private String cellphone;
	private String email;
	private String webpage;
	private String representative;
}
