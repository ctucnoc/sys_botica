package com.sys.botica.crce.pe.sys_botica.service.impl;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

import com.sys.botica.crce.pe.sys_botica.dao.GenericDAO;
import com.sys.botica.crce.pe.sys_botica.dto.DailySaleDTO;
import com.sys.botica.crce.pe.sys_botica.dto.DashboardDTO;
import com.sys.botica.crce.pe.sys_botica.dto.MonthSaleDTO;
import com.sys.botica.crce.pe.sys_botica.dto.VSaleDateDTO;
import com.sys.botica.crce.pe.sys_botica.repository.DashboardRepository;
import com.sys.botica.crce.pe.sys_botica.repository.VSaleDateRepository;
import com.sys.botica.crce.pe.sys_botica.service.DashboardService;
import com.sys.botica.crce.pe.sys_botica.util.SysBoticaUtil;
import com.sys.botica.crce.pe.sys_botica.view.VDashboard;
import com.sys.botica.crce.pe.sys_botica.view.VSaleDate;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.util.NoWriteFieldHandler;

@Slf4j
@Service
public class DashboardServiceImpl implements DashboardService{

	final
	DashboardRepository dashboardRepository;
	
	final
	VSaleDateRepository vSaleDateRepository;
	
	final
	GenericDAO genericDAO;

	public DashboardServiceImpl(DashboardRepository dashboardRepository,VSaleDateRepository vSaleDateRepository,GenericDAO genericDAO) {
		this.dashboardRepository = dashboardRepository;
		this.vSaleDateRepository = vSaleDateRepository;
		this.genericDAO = genericDAO;
	}

	@Override
	public DashboardDTO findByQuantity(String date, Long idSubsidiary) {
		Date temDate = SysBoticaUtil.parseDate(date);
		log.info("crce findByQuantity temorary month -> {} "+temDate);
		VDashboard vDashboard = this.dashboardRepository.findAll().get(0);
		return DashboardDTO.builder()
		.quantityCategory(vDashboard.getQuantityCategory())
		.quantityMark(vDashboard.getQuantityMark())
		.quantityProduct(vDashboard.getQuantityProduct())
		.quantityProvider(vDashboard.getQuantityProvider())
		.quantityUnit(vDashboard.getQuantityUnit())
		.quantityCustomer(vDashboard.getQuantityCustomer())
		.lstSaleDate(findAllVSaleDate())
		.lstDailySale(findByDailySale(date, SysBoticaUtil.toIntExact(idSubsidiary)))
		.lstMonthSale(findByMonthSale(LocalDate.now().getMonthValue(), SysBoticaUtil.toIntExact(idSubsidiary)))
		.build();
	}
	
	private List<VSaleDateDTO> findAllVSaleDate(){
		return this.vSaleDateRepository.findAll().stream()
				.map(bean -> toVSaleDate(bean))
				.collect(Collectors.toList());
	}
	
	private VSaleDateDTO toVSaleDate(VSaleDate vSaleDate) {
		return VSaleDateDTO
				.builder()
				.date(vSaleDate.getDate().toString())
				.totalPrice(vSaleDate.getTotalPrice())
				.build();
	}

	@Override
	public List<DailySaleDTO> findByDailySale(String date, Integer idSubsidiary) {
		return this.genericDAO.findByDailySale(date, idSubsidiary);
	}

	@Override
	public List<MonthSaleDTO> findByMonthSale(Integer month, Integer idSubsidiary) {
		log.info("crce findByMonthSale month -> {} "+month);
		return this.genericDAO.findByMonthSale(month, idSubsidiary);
	}
}
