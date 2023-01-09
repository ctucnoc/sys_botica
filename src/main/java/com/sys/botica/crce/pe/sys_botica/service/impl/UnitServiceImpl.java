package com.sys.botica.crce.pe.sys_botica.service.impl;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.dto.HrefEntityDTO;
import com.sys.botica.crce.pe.sys_botica.dto.UnitDTO;
import com.sys.botica.crce.pe.sys_botica.dto.request.UnitDTORequest;
import com.sys.botica.crce.pe.sys_botica.errorhandler.SysBoticaEntityNotFoundException;
import com.sys.botica.crce.pe.sys_botica.errorhandler.SysBoticaGenericClientException;
import com.sys.botica.crce.pe.sys_botica.model.Unit;
import com.sys.botica.crce.pe.sys_botica.service.UnitService;
import com.sys.botica.crce.pe.sys_botica.util.SysBoticaResource;
import com.sys.botica.crce.pe.sys_botica.util.SysBoticaUtil;

import lombok.extern.slf4j.Slf4j;

import com.sys.botica.crce.pe.sys_botica.repository.UnitRespository;

@Slf4j
@Service
@Transactional
public class UnitServiceImpl implements UnitService{

	final UnitRespository unitRepository;

	public UnitServiceImpl(UnitRespository unitRepository) {
		this.unitRepository = unitRepository;
	}

	@Override
	public Page<UnitDTO> findByDescription(String key_word,Pageable pageable) {
		Page<Unit> params = this.unitRepository.findByDescriptionAndState(key_word,SysBoticaConstant.STATE_ACTIVE, pageable);
		return params.map((bean)->UnitDTO.builder().id(bean.getId()).description(bean.getDescription()).initials(bean.getInitials()).build());
	}

	@Override
	public HrefEntityDTO save(UnitDTORequest unit) {
		if(this.unitRepository.existsByInitialsAndDescription(unit.getInitials(), unit.getDescription()))
			throw new SysBoticaGenericClientException("unit already exists", HttpStatus.BAD_REQUEST);
		Unit bean = new Unit();
		bean.setDescription(unit.getDescription());
		bean.setState(SysBoticaConstant.STATE_ACTIVE);
		bean.setInitials(unit.getInitials());
		this.unitRepository.save(bean);
		return SysBoticaUtil.createHrefFromResource(bean.getId(), SysBoticaResource.UNIT);
	}

	@Override
	public HrefEntityDTO update(UnitDTORequest unit, Integer id) {
		log.info("crce update -> {} "+unit.toString());
		Unit bean = this.unitRepository.findUnitByIdAndState(id,SysBoticaConstant.STATE_ACTIVE).orElseThrow(()->new SysBoticaEntityNotFoundException("not found unit"));
		bean.setDescription(unit.getDescription());
		//bean.setInitials(unit.getInitials());
		this.unitRepository.save(bean);
		return SysBoticaUtil.createHrefFromResource(bean.getId(), SysBoticaResource.UNIT);
	}

	@Override
	public HrefEntityDTO delete(Integer id) {
		Unit bean = this.unitRepository.findUnitByIdAndState(id,SysBoticaConstant.STATE_ACTIVE).orElseThrow(()->new SysBoticaEntityNotFoundException("not found unit"));
		bean.setState(SysBoticaConstant.STATE_INACTIVE);
		this.unitRepository.save(bean);
		return SysBoticaUtil.createHrefFromResource(bean.getId(), SysBoticaResource.UNIT);
	}

	@Override
	public UnitDTO findById(Integer id) {
		Unit bean = this.unitRepository.findUnitByIdAndState(id,SysBoticaConstant.STATE_ACTIVE).orElseThrow(()->new SysBoticaEntityNotFoundException("not found unit"));
		return UnitDTO.builder().description(bean.getDescription()).id(bean.getId()).initials(bean.getInitials()).build();
	}
	
}
