package com.sys.botica.crce.pe.sys_botica.service;

import com.sys.botica.crce.pe.sys_botica.dto.HrefEntityDTO;
import com.sys.botica.crce.pe.sys_botica.dto.SecurityPolicyDTO;
import com.sys.botica.crce.pe.sys_botica.dto.request.SecurityPolicyDTORequest;

public interface SecurityPolicyService {
	public HrefEntityDTO update(SecurityPolicyDTORequest dto,Long id);
	public SecurityPolicyDTO findByCode();
}
