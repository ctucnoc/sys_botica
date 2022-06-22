package com.sys.botica.crce.pe.sys_botica.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DtEntryProductDTORequest {

	@NotNull
	private Long idProduct;
	
	@NotNull
	@NotBlank
	@NotEmpty
	private String productName;
	
	@NotNull
	private Float purchaseprecio;
	
	@NotNull
	private Integer amount;
	private String expiratedate;
	private String lotnumber;
}
