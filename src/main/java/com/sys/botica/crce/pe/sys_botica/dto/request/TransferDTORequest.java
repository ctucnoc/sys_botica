package com.sys.botica.crce.pe.sys_botica.dto.request;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
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
public class TransferDTORequest {

	@NotNull
	@Min(1)
	private Long idwharehouseorigin;
	
	@NotNull
	@Min(1)
	private Long idwharehousedestination;
	
	@NotNull
	@Min(1)
	private Long idsubsidiary;
	
	@Valid
	@NotNull
	@NotEmpty
	private List<DtTransferDTORequest> detail;
}
