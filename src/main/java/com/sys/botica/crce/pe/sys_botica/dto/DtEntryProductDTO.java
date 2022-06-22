package com.sys.botica.crce.pe.sys_botica.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DtEntryProductDTO {
	private Long id;
	private Long idProduct;
	private String productName;
	private Float purchaseprecio;
	private Integer amount;
	private String expiratedate;
	private String lotnumber;
}
