package com.sys.botica.crce.pe.sys_botica.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.dto.GenericDTO;
import com.sys.botica.crce.pe.sys_botica.dto.HrefEntityDTO;
import com.sys.botica.crce.pe.sys_botica.dto.SubsidiaryDTO;
import com.sys.botica.crce.pe.sys_botica.dto.UserSubsidiaryDTO;
import com.sys.botica.crce.pe.sys_botica.dto.request.UserSubsidiaryDTORequest;
import com.sys.botica.crce.pe.sys_botica.errorhandler.SysBoticaEntityNotFoundException;
import com.sys.botica.crce.pe.sys_botica.errorhandler.SysBoticaEntityUnprocessableException;
import com.sys.botica.crce.pe.sys_botica.model.Subsidiary;
import com.sys.botica.crce.pe.sys_botica.model.User;
import com.sys.botica.crce.pe.sys_botica.model.UserSubsidiary;
import com.sys.botica.crce.pe.sys_botica.repository.SubsidiaryRepository;
import com.sys.botica.crce.pe.sys_botica.repository.UserRepository;
import com.sys.botica.crce.pe.sys_botica.repository.UserSubsidiaryRepository;
import com.sys.botica.crce.pe.sys_botica.service.UserSubsidiaryService;
import com.sys.botica.crce.pe.sys_botica.util.SysBoticaResource;
import com.sys.botica.crce.pe.sys_botica.util.SysBoticaUtil;

@Service
public class UserSubsidiaryServiceImpl implements UserSubsidiaryService {

	final UserSubsidiaryRepository userSubsidiaryRepository;
	final UserRepository userRepository;
	final SubsidiaryRepository subsidiaryRepository;

	public UserSubsidiaryServiceImpl(UserSubsidiaryRepository userSubsidiaryRepository,UserRepository userRepository,SubsidiaryRepository subsidiaryRepository) {
		this.userSubsidiaryRepository = userSubsidiaryRepository;
		this.userRepository = userRepository;
		this.subsidiaryRepository = subsidiaryRepository;
	}

	@Override
	public Page<UserSubsidiaryDTO> findByUserAllSubsidiary(Long id, Pageable pageable) {
		return this.userSubsidiaryRepository.findByUserAllSubsidiary(id, SysBoticaConstant.STATE_ACTIVE, pageable).map((bean)-> converToDto(bean));
	}

	public UserSubsidiaryDTO converToDto(UserSubsidiary userSubsidiary) {
		return UserSubsidiaryDTO.builder().id(userSubsidiary.getId()).user(GenericDTO.builder()
				.id(userSubsidiary.getUser().getId()).name(userSubsidiary.getUser().getFullName()).build())
				.subsidiary(GenericDTO.builder().id(userSubsidiary.getSubsidiary().getId()).name(userSubsidiary.getSubsidiary().getName()).build())
				.build();
	}

	@Override
	public HrefEntityDTO save(UserSubsidiaryDTORequest dto) {
		User user = this.userRepository.findByIdAndState(dto.getIdUser(), SysBoticaConstant.STATE_ACTIVE).orElseThrow(()-> new SysBoticaEntityNotFoundException("not found user"));
		Subsidiary subsidiary = this.subsidiaryRepository.findByIdAndState(dto.getIdSubsidiary(), SysBoticaConstant.STATE_ACTIVE).orElseThrow(()-> new SysBoticaEntityNotFoundException("not found subsidiary"));
		if(this.userSubsidiaryRepository.existsByUserAndSubsidiaryAndState(user, subsidiary, SysBoticaConstant.STATE_ACTIVE))
			throw new SysBoticaEntityUnprocessableException("sucursal ya asignado");
		UserSubsidiary userSubsidiary = new UserSubsidiary();
		userSubsidiary.setSubsidiary(subsidiary);
		userSubsidiary.setUser(user);
		userSubsidiary.setState(SysBoticaConstant.STATE_ACTIVE);
		this.userSubsidiaryRepository.save(userSubsidiary);
		return SysBoticaUtil.createHrefFromResource(userSubsidiary.getId(), SysBoticaResource.USERSUBSIDIARY);
	}

	@Override
	public HrefEntityDTO delete(Long id) {
		UserSubsidiary userSubsidiary = this.userSubsidiaryRepository.findByIdAndState(id, SysBoticaConstant.STATE_ACTIVE).orElseThrow(()-> new SysBoticaEntityNotFoundException("not found"));
		userSubsidiary.setState(SysBoticaConstant.STATE_INACTIVE);
		this.userSubsidiaryRepository.save(userSubsidiary);
		return SysBoticaUtil.createHrefFromResource(userSubsidiary.getId(), SysBoticaResource.USERSUBSIDIARY);
	}

	@Override
	public List<SubsidiaryDTO> findByUser(String username) {
		User user = this.userRepository.findByUsernameAndState(username.toUpperCase(), SysBoticaConstant.STATE_ACTIVE).orElseThrow(()-> new SysBoticaEntityNotFoundException("not found user"));
		return this.userSubsidiaryRepository.findByUserAndState(user, SysBoticaConstant.STATE_ACTIVE)
		.stream()
		.map((bean)->this.toSubsidiaryDto(bean.getSubsidiary())).collect(Collectors.toList());
	}
	
	public SubsidiaryDTO toSubsidiaryDto(Subsidiary subsidiary) {
		return SubsidiaryDTO.builder().id(subsidiary.getId()).name(subsidiary.getName()).build();
	}
	


}
