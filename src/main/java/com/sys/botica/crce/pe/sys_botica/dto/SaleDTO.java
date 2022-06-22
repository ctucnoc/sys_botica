package com.sys.botica.crce.pe.sys_botica.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleDTO {
	
	private Long id;

	private Long idCustomer;
	
	private String customer;
	
	private Long idProofpayment;
	
	private String proofpayment;
	
	private Date datecreated;
	
}
