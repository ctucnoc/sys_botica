package com.sys.botica.crce.pe.sys_botica.dto.request;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleDTORequest {
	
	@NotNull
	private Long idCustomer;
	
	@NotNull
	private Long idProofpayment;
	
	@Valid
	List<DtSaleDTORequest> details;
}
