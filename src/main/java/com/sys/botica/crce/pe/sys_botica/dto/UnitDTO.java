package com.sys.botica.crce.pe.sys_botica.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UnitDTO {

	private Integer id;
	private String initials;
	private String description;
}
