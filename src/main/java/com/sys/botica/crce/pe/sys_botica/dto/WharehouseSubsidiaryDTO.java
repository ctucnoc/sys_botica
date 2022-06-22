package com.sys.botica.crce.pe.sys_botica.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WharehouseSubsidiaryDTO {
	private Long id;
	private GenericDTO subsidiary;
	private WharehouseDTO wharehouseDTO;
}
