package com.sys.botica.crce.pe.sys_botica.service;

import com.sys.botica.crce.pe.sys_botica.dto.HrefEntityDTO;
import com.sys.botica.crce.pe.sys_botica.dto.request.EntryProductDTORequest;

public interface EntryProductService {
	public HrefEntityDTO save(EntryProductDTORequest dto,String username);
}
