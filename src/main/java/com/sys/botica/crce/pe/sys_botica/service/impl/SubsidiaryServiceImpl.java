package com.sys.botica.crce.pe.sys_botica.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.dto.GenericDTO;
import com.sys.botica.crce.pe.sys_botica.dto.SubsidiaryDTO;
import com.sys.botica.crce.pe.sys_botica.model.Subsidiary;
import com.sys.botica.crce.pe.sys_botica.repository.SubsidiaryRepository;
import com.sys.botica.crce.pe.sys_botica.service.SubsidiaryService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SubsidiaryServiceImpl implements SubsidiaryService{

	final SubsidiaryRepository subsidiaryRepository;

	public SubsidiaryServiceImpl(SubsidiaryRepository subsidiaryRepository) {
		this.subsidiaryRepository = subsidiaryRepository;
	}

	@Override
	public Page<SubsidiaryDTO> findByName(String name, Pageable pageable) {
		return this.subsidiaryRepository.findByNameContainingIgnoreCaseAndState(name, SysBoticaConstant.STATE_ACTIVE, pageable)
		.map((bean)-> convertToDto(bean));
	}
	
	public SubsidiaryDTO convertToDto(Subsidiary subsidiary) {
		return SubsidiaryDTO.builder()
				.id(subsidiary.getId())
				.name(subsidiary.getName())
				.address(subsidiary.getAddress())
				.enterprise(GenericDTO.builder().id(subsidiary.getEnterprise().getId()).name(subsidiary.getEnterprise().getBussinesname()).build())
				.build();
	}

	@Override
	public List<SubsidiaryDTO> findAll() {
		return this.subsidiaryRepository.findByState(SysBoticaConstant.STATE_ACTIVE).stream()
				.map((bean)-> convertToDto(bean))
				.collect(Collectors.toList());
	}

	@Override
	public List<SubsidiaryDTO> findByName(String name) {
		log.info("crce service findByName -> {} "+name);
		return this.subsidiaryRepository.findByNameContainingIgnoreCaseAndState(name, SysBoticaConstant.STATE_ACTIVE)
				.stream()
				.filter(bean-> bean.getName().toUpperCase().contains(name.toUpperCase()))
				.limit(15)
				.map(subsidiary -> convertToDto(subsidiary))
				.collect(Collectors.toList());
	}
	
}
