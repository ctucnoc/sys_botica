package com.sys.botica.crce.pe.sys_botica.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.sys.botica.crce.pe.sys_botica.dto.HrefEntityDTO;
import com.sys.botica.crce.pe.sys_botica.dto.WharehouseDTO;
import com.sys.botica.crce.pe.sys_botica.dto.request.WharehouseDTORequest;

public interface WharehouseService {
	public Page<WharehouseDTO> findByname(String name, Pageable pageable);
	public WharehouseDTO findById(Long id);
	public HrefEntityDTO save(WharehouseDTORequest dto);
	public HrefEntityDTO update(WharehouseDTORequest dto, Long id);
	public HrefEntityDTO delete(Long id);
	public List<WharehouseDTO> findByAllTypeDistribution();
}
