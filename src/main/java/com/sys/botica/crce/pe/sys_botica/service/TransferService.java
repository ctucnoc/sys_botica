package com.sys.botica.crce.pe.sys_botica.service;

import com.sys.botica.crce.pe.sys_botica.dto.HrefEntityDTO;
import com.sys.botica.crce.pe.sys_botica.dto.request.TransferDTORequest;

public interface TransferService {
	public HrefEntityDTO save(TransferDTORequest transferDTORequest,String token);
}
