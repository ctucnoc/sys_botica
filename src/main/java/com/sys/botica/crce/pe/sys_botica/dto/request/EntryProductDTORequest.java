package com.sys.botica.crce.pe.sys_botica.dto.request;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EntryProductDTORequest {

	@NotNull
	private Long idProvider;
	
	@NotNull
	private Long idWharehouse;
	
	@NotNull
	private Long idEntryDocument;
	
	@NotNull
	@Valid
	List<DtEntryProductDTORequest> details;
}
