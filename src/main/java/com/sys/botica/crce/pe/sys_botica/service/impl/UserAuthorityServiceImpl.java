package com.sys.botica.crce.pe.sys_botica.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.dto.GenericDTO;
import com.sys.botica.crce.pe.sys_botica.dto.HrefEntityDTO;
import com.sys.botica.crce.pe.sys_botica.dto.UserAuthorityDTO;
import com.sys.botica.crce.pe.sys_botica.dto.request.UserAuthorityDTORequest;
import com.sys.botica.crce.pe.sys_botica.errorhandler.SysBoticaEntityNotFoundException;
import com.sys.botica.crce.pe.sys_botica.errorhandler.SysBoticaEntityUnprocessableException;
import com.sys.botica.crce.pe.sys_botica.model.User;
import com.sys.botica.crce.pe.sys_botica.model.UserAuthority;
import com.sys.botica.crce.pe.sys_botica.repository.UserAuthorityRepository;
import com.sys.botica.crce.pe.sys_botica.repository.UserRepository;
import com.sys.botica.crce.pe.sys_botica.service.UserAuthorityService;
import com.sys.botica.crce.pe.sys_botica.util.SysBoticaResource;
import com.sys.botica.crce.pe.sys_botica.util.SysBoticaUtil;

@Service
@Transactional
public class UserAuthorityServiceImpl implements UserAuthorityService{

	final UserAuthorityRepository userAuthorityRepository;
	final UserRepository userRepository;
	final SysBoticaUtil util;

	public UserAuthorityServiceImpl(UserAuthorityRepository userAuthorityRepository,UserRepository userRepository,SysBoticaUtil util) {
		this.userAuthorityRepository = userAuthorityRepository;
		this.userRepository = userRepository;
		this.util = util;
	}

	@Override
	public Page<UserAuthorityDTO> findByUser(Long iduser, Pageable pageable) {
		User user = this.userRepository.findByIdAndState(iduser, SysBoticaConstant.STATE_ACTIVE).orElseThrow(()-> new SysBoticaEntityNotFoundException("not found user"));
		return this.userAuthorityRepository.findByUserAndState(user, SysBoticaConstant.STATE_ACTIVE, pageable).map((bean)-> toUserAuthorityDto(bean));
	}
	
	public UserAuthorityDTO toUserAuthorityDto(UserAuthority userAuthority) {
		return UserAuthorityDTO.builder().id(userAuthority.getId())
				.user(GenericDTO.builder().id(userAuthority.getUser().getId()).name(userAuthority.getUser().getFullName()).build())
				.authority(GenericDTO.builder().id(userAuthority.getAuthority().getId()).name(util.remplace(userAuthority.getAuthority().getName(), SysBoticaConstant.VN_PREFIX_AUTHORITY, SysBoticaConstant.VN_EMPTY)).build())
				.build();
	}

	@Override
	public HrefEntityDTO delete(Long id) {
		UserAuthority userAuthority = this.userAuthorityRepository.findByIdAndState(id, SysBoticaConstant.STATE_ACTIVE).orElseThrow(()-> new SysBoticaEntityNotFoundException("not found"));
		userAuthority.setState(SysBoticaConstant.STATE_INACTIVE);
		return SysBoticaUtil.createHrefFromResource(userAuthority.getId(), SysBoticaResource.USERAUTHORITY);
	}

	@Override
	public HrefEntityDTO save(UserAuthorityDTORequest dto) {
		if(this.userAuthorityRepository.existsByUserAndAuthorityAndStateSql(Math.toIntExact(dto.getIdUser()), Math.toIntExact(dto.getIdAuthority()), SysBoticaConstant.STATE_ACTIVE))
			throw new SysBoticaEntityUnprocessableException("user and authority exists");
		UserAuthority userAuthority = this.userAuthorityRepository.saveSql(Math.toIntExact(dto.getIdUser()), Math.toIntExact(dto.getIdAuthority()),SysBoticaConstant.STATE_ACTIVE);
		return SysBoticaUtil.createHrefFromResource(userAuthority.getId(), SysBoticaResource.USERAUTHORITY);
	}

	@Override
	public List<GrantedAuthority> findByUser(String username) {
		User user = this.userRepository.findByUsername(username.toUpperCase()).orElseThrow(()->new SysBoticaEntityNotFoundException("user not found"));
		List<GrantedAuthority> authorities = this.userAuthorityRepository.findByUser(user).stream()
				.map((userAutority)->new SimpleGrantedAuthority(userAutority.getAuthority().getName()))
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
		if(authorities.size()==0 || authorities==null)
			throw new SysBoticaEntityUnprocessableException("no role assigned to the user");
		return authorities;
	}
	
	
}
