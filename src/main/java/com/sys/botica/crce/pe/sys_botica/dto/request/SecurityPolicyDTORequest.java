package com.sys.botica.crce.pe.sys_botica.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SecurityPolicyDTORequest {
	
	@NotNull
	private Integer minpasswordlength;
	
	@NotNull
	private Integer maxpasswordlength;
	
	@NotNull
	private Integer maxnumberattempts;
	
	@NotNull
	private Integer maxidletime;
	
	@NotNull
	@NotBlank
	@NotEmpty
	private String passwordchangefirstlogin;
	
}
