package com.sys.botica.crce.pe.sys_botica.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.sys.botica.crce.pe.sys_botica.dto.SubsidiaryDTO;

public interface SubsidiaryService {
	public Page<SubsidiaryDTO> findByName(String name, Pageable pageable);
	public List<SubsidiaryDTO> findAll();
	public List<SubsidiaryDTO> findByName(String name);
}
