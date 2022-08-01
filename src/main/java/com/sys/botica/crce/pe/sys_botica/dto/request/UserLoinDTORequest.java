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
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
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