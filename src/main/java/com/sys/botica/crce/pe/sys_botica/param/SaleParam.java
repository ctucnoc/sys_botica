package com.sys.botica.crce.pe.sys_botica.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleParam {
	private Integer idcustomer;
	private Integer idproofpayment;
	private String dateFrom;
	private String dateTo;
}
