package com.sys.botica.crce.pe.sys_botica.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserLoinDTORequest {

	@NotNull(message = "no not null")
	@NotBlank
	@NotEmpty
    private String username;
	
	@NotNull
    private Long idSubsidiary;
	
	@NotNull
	@NotBlank
	@NotEmpty
    private String password;
}
