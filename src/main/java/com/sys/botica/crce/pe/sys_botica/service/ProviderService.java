package com.sys.botica.crce.pe.sys_botica.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.sys.botica.crce.pe.sys_botica.dto.HrefEntityDTO;
import com.sys.botica.crce.pe.sys_botica.dto.ProviderDTO;
import com.sys.botica.crce.pe.sys_botica.dto.request.ProviderDTORequest;

public interface ProviderService {

	public Page<ProviderDTO> findByDescription(String name,Pageable pageable);
	
	public HrefEntityDTO update(ProviderDTORequest dto,Long id);
	
	public HrefEntityDTO delete(Long id);
	
	public HrefEntityDTO save(ProviderDTORequest dto);
	
	public ProviderDTO findById(Long id);
	
	public Page<ProviderDTO> findAll(Pageable pageable);
}
