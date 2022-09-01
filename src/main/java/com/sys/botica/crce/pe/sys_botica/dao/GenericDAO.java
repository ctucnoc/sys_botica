package com.sys.botica.crce.pe.sys_botica.dao;

import java.util.List;
import java.util.Optional;

import com.sys.botica.crce.pe.sys_botica.dto.DailySaleDTO;
import com.sys.botica.crce.pe.sys_botica.dto.MonthSaleDTO;

public interface GenericDAO {
	public Optional<Float> totalSale(Integer id);
	public List<DailySaleDTO> findByDailySale(String date,Integer idsubsidiary);
	public List<MonthSaleDTO> findByMonthSale(Integer month,Integer idsubsidiary);
}
