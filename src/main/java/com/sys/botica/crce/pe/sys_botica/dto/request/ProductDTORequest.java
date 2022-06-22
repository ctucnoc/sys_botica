package com.sys.botica.crce.pe.sys_botica.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDTORequest {
	
	@NotNull
	@NotEmpty
	@NotBlank
	private String name;
	
	@NotNull
	@NotEmpty
	@NotBlank
	private String summaryname;
	
	@NotNull
	@NotEmpty
	@NotBlank
	private String isexpiratedate;
	
	@NotNull
	@NotEmpty
	@NotBlank
	private String isrefrigeration;
	
	@NotNull
	@NotEmpty
	@NotBlank
	private String isbatch;
	
	@NotNull
	@NotEmpty
	@NotBlank
	private String isgeneric;
	
	@NotNull
	@NotEmpty
	@NotBlank
	private String iskit;
	
	@NotNull
	@NotEmpty
	@NotBlank
	private String ismedicine;
	
	@NotNull
	@NotEmpty
	@NotBlank
	private String iscontrolled;
	
	@NotNull
	private String barcode;
	
	@NotNull
	private Integer idcategory;
	
	@NotNull
	private Integer idmark;
	
	@NotNull
	private Integer idunit;
}
