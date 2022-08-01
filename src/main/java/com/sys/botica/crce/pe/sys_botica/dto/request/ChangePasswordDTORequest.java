package com.sys.botica.crce.pe.sys_botica.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ChangePasswordDTORequest {
	
	@NotNull
	@NotBlank
	@NotEmpty
	public String userName;

	@NotNull
	@NotBlank
	@NotEmpty
	private String password;
	
	@NotNull
	@NotBlank
	@NotEmpty
	private String newPassword;
	
	@NotNull
	@NotBlank
	@NotEmpty
	private String confirmNewPassword;
}
