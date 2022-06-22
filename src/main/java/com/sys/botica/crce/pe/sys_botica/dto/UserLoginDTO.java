package com.sys.botica.crce.pe.sys_botica.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {
	private Long id;
	private String username;
	private String password;
	private String fullname;
	private String email;
	private Boolean state;
}
