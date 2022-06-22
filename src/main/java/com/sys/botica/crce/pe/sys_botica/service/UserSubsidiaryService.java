package com.sys.botica.crce.pe.sys_botica.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.sys.botica.crce.pe.sys_botica.dto.HrefEntityDTO;
import com.sys.botica.crce.pe.sys_botica.dto.SubsidiaryDTO;
import com.sys.botica.crce.pe.sys_botica.dto.UserSubsidiaryDTO;
import com.sys.botica.crce.pe.sys_botica.dto.request.UserSubsidiaryDTORequest;

public interface UserSubsidiaryService {
	public HrefEntityDTO save(UserSubsidiaryDTORequest dto);

	public HrefEntityDTO delete(Long id);

	public Page<UserSubsidiaryDTO> findByUserAllSubsidiary(Long id, Pageable pageable);
	
	public List<SubsidiaryDTO> findByUser(String username);
}
