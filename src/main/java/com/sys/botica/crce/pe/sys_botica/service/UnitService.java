package com.sys.botica.crce.pe.sys_botica.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.sys.botica.crce.pe.sys_botica.dto.HrefEntityDTO;
import com.sys.botica.crce.pe.sys_botica.dto.UnitDTO;
import com.sys.botica.crce.pe.sys_botica.dto.request.UnitDTORequest;

public interface UnitService {

	public Page<UnitDTO> findByDescription(String key_word,Pageable pageable);
	public HrefEntityDTO save(UnitDTORequest unit);
	public HrefEntityDTO update(UnitDTORequest unit,Integer id);
	public HrefEntityDTO delete(Integer id);
	public UnitDTO findById(Integer id);
}
