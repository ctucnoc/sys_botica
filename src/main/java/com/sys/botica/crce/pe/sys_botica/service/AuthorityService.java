package com.sys.botica.crce.pe.sys_botica.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.sys.botica.crce.pe.sys_botica.dto.AuthorityDTO;
import com.sys.botica.crce.pe.sys_botica.dto.HrefEntityDTO;
import com.sys.botica.crce.pe.sys_botica.dto.request.AuthorityDTORequest;

public interface AuthorityService {
	public Page<AuthorityDTO> findByName(String name,Pageable pageable);
	
	public HrefEntityDTO update(AuthorityDTORequest dto,Long id);
	
	public HrefEntityDTO delete(Long id);
	
	public HrefEntityDTO save(AuthorityDTORequest dto);
	
	public AuthorityDTO findById(Long id);
	
	public Page<AuthorityDTO> findAllByUser(Long iduser,Pageable pageable);
}
