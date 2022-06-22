package com.sys.botica.crce.pe.sys_botica.service.impl;

import javax.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.dto.AuthorityDTO;
import com.sys.botica.crce.pe.sys_botica.dto.HrefEntityDTO;
import com.sys.botica.crce.pe.sys_botica.dto.request.AuthorityDTORequest;
import com.sys.botica.crce.pe.sys_botica.errorhandler.SysBoticaEntityNotFoundException;
import com.sys.botica.crce.pe.sys_botica.errorhandler.SysBoticaEntityUnprocessableException;
import com.sys.botica.crce.pe.sys_botica.model.Authority;
import com.sys.botica.crce.pe.sys_botica.model.User;
import com.sys.botica.crce.pe.sys_botica.repository.AuthorityRespository;
import com.sys.botica.crce.pe.sys_botica.repository.UserAuthorityRepository;
import com.sys.botica.crce.pe.sys_botica.repository.UserRepository;
import com.sys.botica.crce.pe.sys_botica.service.AuthorityService;
import com.sys.botica.crce.pe.sys_botica.util.SysBoticaResource;
import com.sys.botica.crce.pe.sys_botica.util.SysBoticaUtil;

@Service
@Transactional
public class AuthorityServiceImpl implements AuthorityService {

	final AuthorityRespository authorityRespository;
	final SysBoticaUtil util;
	final UserAuthorityRepository userAuthorityRepository;
	final UserRepository userRepository;

	public AuthorityServiceImpl(AuthorityRespository authorityRespository,SysBoticaUtil util,UserAuthorityRepository userAuthorityRepository,UserRepository userRepository) {
		this.authorityRespository = authorityRespository;
		this.util = util;
		this.userAuthorityRepository = userAuthorityRepository;
		this.userRepository = userRepository;
	}

	@Override
	public Page<AuthorityDTO> findByName(String name, Pageable pageable) {
		return this.authorityRespository
				.findByNameLikeIgnoreCaseAndState("%" + name + "%", SysBoticaConstant.STATE_ACTIVE, pageable)
				.map((bean) -> AuthorityDTO.builder().id(bean.getId()).name(this.util.remplace(bean.getName(), SysBoticaConstant.VN_PREFIX_AUTHORITY,SysBoticaConstant.VN_EMPTY))
						.description(bean.getDescription()).build());
	}

	@Override
	public HrefEntityDTO update(AuthorityDTORequest dto, Long id) {
		Authority authority = this.authorityRespository.findByIdAndState(id, SysBoticaConstant.STATE_ACTIVE)
				.orElseThrow(() -> new SysBoticaEntityNotFoundException("not found"));
		authority.setDescription(dto.getDescription());
		this.authorityRespository.save(authority);
		return SysBoticaUtil.createHrefFromResource(authority.getId(), SysBoticaResource.AUTHORITY);
	}

	@Override
	public HrefEntityDTO delete(Long id) {
		Authority authority = this.authorityRespository.findByIdAndState(id, SysBoticaConstant.STATE_ACTIVE)
				.orElseThrow(() -> new SysBoticaEntityNotFoundException("not found"));
		authority.setState(SysBoticaConstant.STATE_INACTIVE);
		this.authorityRespository.save(authority);
		return SysBoticaUtil.createHrefFromResource(authority.getId(), SysBoticaResource.AUTHORITY);
	}

	@Override
	public HrefEntityDTO save(AuthorityDTORequest dto) {
		if (this.authorityRespository.existsByNameAndState(
				SysBoticaConstant.VN_PREFIX_AUTHORITY + dto.getName().toUpperCase(), SysBoticaConstant.STATE_ACTIVE))
			throw new SysBoticaEntityUnprocessableException("it exists");
		Authority authority = new Authority();
		authority.setDescription(dto.getDescription());
		authority.setName(SysBoticaConstant.VN_PREFIX_AUTHORITY + dto.getName().toUpperCase());
		authority.setState(SysBoticaConstant.STATE_ACTIVE);
		this.authorityRespository.save(authority);
		return SysBoticaUtil.createHrefFromResource(authority.getId(), SysBoticaResource.AUTHORITY);
	}

	@Override
	public AuthorityDTO findById(Long id) {
		Authority authority = this.authorityRespository.findByIdAndState(id, SysBoticaConstant.STATE_ACTIVE)
				.orElseThrow(() -> new SysBoticaEntityNotFoundException("not found"));
		return AuthorityDTO.builder().description(authority.getDescription()).name(this.util.remplace(authority.getName(), SysBoticaConstant.VN_PREFIX_AUTHORITY,SysBoticaConstant.VN_EMPTY))
				.id(authority.getId()).build();
	}

	@Override
	public Page<AuthorityDTO> findAllByUser(Long iduser,Pageable pageable) {
		User user = this.userRepository.findByIdAndState(iduser, SysBoticaConstant.STATE_ACTIVE).orElseThrow(()-> new SysBoticaEntityNotFoundException("not found"));
		return this.authorityRespository.findByMinusAuthority(Math.toIntExact(user.getId()), SysBoticaConstant.STATE_ACTIVE, pageable)
				.map((bean)-> toAuthorityDto(bean));
	}
	
	public AuthorityDTO toAuthorityDto(Authority authority) {
		return AuthorityDTO.builder().description(authority.getDescription()).name(this.util.remplace(authority.getName(), SysBoticaConstant.VN_PREFIX_AUTHORITY,SysBoticaConstant.VN_EMPTY))
				.id(authority.getId()).build();
	}

}
