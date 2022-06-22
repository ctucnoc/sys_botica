package com.sys.botica.crce.pe.sys_botica.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WharehouseDTORequest {

	@NotNull
	@NotBlank
	@NotEmpty
	private String name;
	
	@NotNull
	@NotBlank
	@NotEmpty
	private String description;
	
	@NotNull
	@NotBlank
	@NotEmpty
	private String type;
}
