package com.sys.botica.crce.pe.sys_botica.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;

import com.sys.botica.crce.pe.sys_botica.dto.HrefEntityDTO;
import com.sys.botica.crce.pe.sys_botica.dto.UserAuthorityDTO;
import com.sys.botica.crce.pe.sys_botica.dto.request.UserAuthorityDTORequest;

public interface UserAuthorityService {
	public Page<UserAuthorityDTO> findByUser(Long idUser, Pageable pageable);

	public HrefEntityDTO delete(Long id);

	public HrefEntityDTO save(UserAuthorityDTORequest dto);
	
	public List<GrantedAuthority> findByUser(String username);
}
