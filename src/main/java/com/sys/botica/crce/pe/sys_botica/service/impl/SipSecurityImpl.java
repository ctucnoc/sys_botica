package com.sys.botica.crce.pe.sys_botica.service.impl;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.sys.botica.crce.pe.sys_botica.dto.UserDetailDTO;
import com.sys.botica.crce.pe.sys_botica.dto.UserLoginDTO;
import com.sys.botica.crce.pe.sys_botica.errorhandler.SysBoticaEntityNotFoundException;
import com.sys.botica.crce.pe.sys_botica.security.JwtProvider;
import com.sys.botica.crce.pe.sys_botica.service.UserAuthorityService;
import com.sys.botica.crce.pe.sys_botica.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SipSecurityImpl implements UserDetailsService{

	final UserService userService;
	final UserAuthorityService userAuthorityService;

	public SipSecurityImpl(UserService userService,UserAuthorityService userAuthorityService,JwtProvider jwtProvider) {
		this.userService = userService;
		this.userAuthorityService = userAuthorityService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws SysBoticaEntityNotFoundException {
		String[] userAndSubsidiary= StringUtils.split(username,String.valueOf(Character.LINE_SEPARATOR));
		log.info("user and enterprise -> {} " + userAndSubsidiary[0] + " " + userAndSubsidiary[1]);
		UserLoginDTO loginDTO = this.userService.findByUsername(userAndSubsidiary[0]);
		List<GrantedAuthority> authorities = this.userAuthorityService.findByUser(userAndSubsidiary[0]);
		UserDetailDTO user = new UserDetailDTO(loginDTO.getUsername(), loginDTO.getPassword(), loginDTO.getFullname(), loginDTO.getState(), authorities, null);
		return user;
	}	
	
}
