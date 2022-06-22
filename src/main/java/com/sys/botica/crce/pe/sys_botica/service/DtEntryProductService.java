package com.sys.botica.crce.pe.sys_botica.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.sys.botica.crce.pe.sys_botica.dto.DtEntryProductDTO;

public interface DtEntryProductService {
	public Page<DtEntryProductDTO> findByIdWharehouse(Long idwharehouse,String word_key,Pageable pageable);
}
