package com.sys.botica.crce.pe.sys_botica.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DtTransferDTORequest {
	@NotNull
	private Long iddtentryproduct;
	
	@NotNull
	@Min(1)
	private Long idproduct;
	
	@NotNull
	@NotEmpty
	@NotBlank
	private String productname;
	
	@NotNull
	@Min(1)
	private Integer amount;
	
	private String expiratedate;
	
	private String lotnumber;
	
	@NotNull
	private Float saleprice;
}
