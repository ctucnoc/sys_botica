package com.sys.botica.crce.pe.sys_botica.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTORequest {
	
	@NotNull
	@NotBlank
	@NotEmpty
	private String username;
	
	@NotNull
	@NotBlank
	@NotEmpty
	private String password;
	
	@NotNull
	@NotBlank
	@NotEmpty
	private String email;
	
	@NotNull
	@NotBlank
	@NotEmpty
	private String fullname;
	
	@NotNull
	@NotBlank
	@NotEmpty
	private String state;
}
