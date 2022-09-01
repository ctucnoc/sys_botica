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
public class DashboardDTO {
	private Integer quantityProduct;
	private Integer quantityCategory;
	private Integer quantityMark;
	private Integer quantityProvider;
	private Integer quantityUnit;
	private Integer quantityCustomer;
	private List<VSaleDateDTO> lstSaleDate;
	private List<DailySaleDTO> lstDailySale;
	private List<MonthSaleDTO> lstMonthSale;
}
