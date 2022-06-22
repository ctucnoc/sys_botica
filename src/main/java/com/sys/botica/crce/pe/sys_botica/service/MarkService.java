package com.sys.botica.crce.pe.sys_botica.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.sys.botica.crce.pe.sys_botica.dto.HrefEntityDTO;
import com.sys.botica.crce.pe.sys_botica.dto.MarkDTO;
import com.sys.botica.crce.pe.sys_botica.dto.request.MarkDTORequest;

public interface MarkService {
	public Page<MarkDTO> findAll(String key_word,Pageable pageable);
	public HrefEntityDTO save(MarkDTORequest mark);
	public HrefEntityDTO update(MarkDTORequest mark,Integer id);
	public HrefEntityDTO delete(Integer id);
	public MarkDTO findById(Integer id);
}
