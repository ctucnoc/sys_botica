package com.sys.botica.crce.pe.sys_botica.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WharehouseTranferDTO {
	public List<WharehouseDTO> distributions;
	public List<WharehouseDTO> subwarehouses;
}
