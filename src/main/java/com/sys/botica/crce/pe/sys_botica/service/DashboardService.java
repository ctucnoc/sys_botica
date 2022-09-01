package com.sys.botica.crce.pe.sys_botica.service;

import java.util.List;
import com.sys.botica.crce.pe.sys_botica.dto.DailySaleDTO;
import com.sys.botica.crce.pe.sys_botica.dto.DashboardDTO;
import com.sys.botica.crce.pe.sys_botica.dto.MonthSaleDTO;

public interface DashboardService {
	public DashboardDTO findByQuantity(String date, Long idSubsidiary);
	public List<DailySaleDTO> findByDailySale(String date, Integer idSubsidiary);
	public List<MonthSaleDTO> findByMonthSale(Integer month, Integer idSubsidiary);
}
