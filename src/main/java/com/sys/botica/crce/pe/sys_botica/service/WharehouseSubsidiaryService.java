package com.sys.botica.crce.pe.sys_botica.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sys.botica.crce.pe.sys_botica.dto.HrefEntityDTO;
import com.sys.botica.crce.pe.sys_botica.dto.WharehouseDTO;
import com.sys.botica.crce.pe.sys_botica.dto.WharehouseSubsidiaryDTO;
import com.sys.botica.crce.pe.sys_botica.dto.WharehouseTranferDTO;
import com.sys.botica.crce.pe.sys_botica.dto.request.WharehouseSubsidiaryDTORequest;

public interface WharehouseSubsidiaryService {
	public List<WharehouseDTO> findAllWharehouse(Long idsubsidiary);
	public WharehouseTranferDTO findAllWharehouseTransfer(Long idsubsidiary);
	public Page<WharehouseSubsidiaryDTO> findBySubsidiary(Long idsubsidiary,Pageable pageable);
	public HrefEntityDTO save(WharehouseSubsidiaryDTORequest dto);
	public HrefEntityDTO delete(Long id);
}
