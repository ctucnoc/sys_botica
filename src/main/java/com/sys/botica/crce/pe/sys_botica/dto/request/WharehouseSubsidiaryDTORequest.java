package com.sys.botica.crce.pe.sys_botica.dto.request;

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
public class WharehouseSubsidiaryDTORequest {
	private Long idWharehouse;
	private Long idSubsidiary;
}
