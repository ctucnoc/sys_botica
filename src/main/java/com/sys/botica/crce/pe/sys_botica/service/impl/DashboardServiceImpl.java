package com.sys.botica.crce.pe.sys_botica.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.sys.botica.crce.pe.sys_botica.dto.DashboardDTO;
import com.sys.botica.crce.pe.sys_botica.dto.VSaleDateDTO;
import com.sys.botica.crce.pe.sys_botica.repository.DashboardRepository;
import com.sys.botica.crce.pe.sys_botica.repository.VSaleDateRepository;
import com.sys.botica.crce.pe.sys_botica.service.DashboardService;
import com.sys.botica.crce.pe.sys_botica.view.VDashboard;
import com.sys.botica.crce.pe.sys_botica.view.VSaleDate;

@Service
public class DashboardServiceImpl implements DashboardService{

	final
	DashboardRepository dashboardRepository;
	
	final
	VSaleDateRepository vSaleDateRepository;

	public DashboardServiceImpl(DashboardRepository dashboardRepository,VSaleDateRepository vSaleDateRepository) {
		this.dashboardRepository = dashboardRepository;
		this.vSaleDateRepository = vSaleDateRepository;
	}

	@Override
	public DashboardDTO findByQuantity() {
		VDashboard vDashboard = this.dashboardRepository.findAll().get(0);
		return DashboardDTO.builder()
		.quantityCategory(vDashboard.getQuantityCategory())
		.quantityMark(vDashboard.getQuantityMark())
		.quantityProduct(vDashboard.getQuantityProduct())
		.quantityProvider(vDashboard.getQuantityProvider())
		.quantityUnit(vDashboard.getQuantityUnit())
		.quantityCustomer(vDashboard.getQuantityCustomer())
		.lstSaleDate(findAllVSaleDate())
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
}
