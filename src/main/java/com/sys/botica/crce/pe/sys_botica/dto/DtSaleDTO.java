package com.sys.botica.crce.pe.sys_botica.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DtSaleDTO {
	private Long id;
	private Integer saleAmount;
	private Float salePrice;
	private String dateExpiration;
	private String productName;
}
