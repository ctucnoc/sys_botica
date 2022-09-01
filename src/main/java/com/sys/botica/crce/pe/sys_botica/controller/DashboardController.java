package com.sys.botica.crce.pe.sys_botica.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.dto.DashboardDTO;
import com.sys.botica.crce.pe.sys_botica.service.DashboardService;

@RestController
@RequestMapping(SysBoticaConstant.API_VERSION + SysBoticaConstant.RESOURCE_DASHBOARS)
@CrossOrigin(origins = SysBoticaConstant.PATH_FROTEND_SYSCE)
public class DashboardController {

	final
	DashboardService dashboardService;

	public DashboardController(DashboardService dashboardService) {
		this.dashboardService = dashboardService;
	}
	
	@GetMapping(SysBoticaConstant.RESOURCE_DASHBOARS_DASHBOAR)
	public ResponseEntity<DashboardDTO> findByQuantity(@RequestParam String date,@RequestParam Long idSubsidiary){
		return ResponseEntity.ok(this.dashboardService.findByQuantity(date,idSubsidiary));
	}	
	
}
