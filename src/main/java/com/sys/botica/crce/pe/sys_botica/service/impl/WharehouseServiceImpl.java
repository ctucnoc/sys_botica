package com.sys.botica.crce.pe.sys_botica.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.dto.HrefEntityDTO;
import com.sys.botica.crce.pe.sys_botica.dto.WharehouseDTO;
import com.sys.botica.crce.pe.sys_botica.dto.request.WharehouseDTORequest;
import com.sys.botica.crce.pe.sys_botica.errorhandler.SysBoticaEntityNotFoundException;
import com.sys.botica.crce.pe.sys_botica.errorhandler.SysBoticaEntityUnprocessableException;
import com.sys.botica.crce.pe.sys_botica.model.Wharehouse;
import com.sys.botica.crce.pe.sys_botica.repository.WharehouseRepository;
import com.sys.botica.crce.pe.sys_botica.service.WharehouseService;
import com.sys.botica.crce.pe.sys_botica.util.SysBoticaResource;
import com.sys.botica.crce.pe.sys_botica.util.SysBoticaUtil;

@Service
public class WharehouseServiceImpl implements WharehouseService{

	final WharehouseRepository wharehouseRepository;

	public WharehouseServiceImpl(WharehouseRepository wharehouseRepository) {
		this.wharehouseRepository = wharehouseRepository;
	}

	@Override
	public Page<WharehouseDTO> findByname(String name, Pageable pageable) {
		return this.wharehouseRepository.findByNameLikeIgnoreCaseAndState("%"+name+"%", SysBoticaConstant.STATE_ACTIVE, pageable)
				.map(bean->toWharehouseDto(bean));
	}

	@Override
	public WharehouseDTO findById(Long id) {
		Wharehouse wharehouse = this.wharehouseRepository.findByIdAndState(id, SysBoticaConstant.STATE_ACTIVE).orElseThrow(()-> new SysBoticaEntityNotFoundException("not found"));
		return toWharehouseDto(wharehouse);
	}

	@Override
	public HrefEntityDTO save(WharehouseDTORequest dto) {
		if (this.wharehouseRepository.existsByNameAndState(dto.getName().toUpperCase(), SysBoticaConstant.STATE_ACTIVE))
			throw new SysBoticaEntityUnprocessableException("wharehouse exists");
		Wharehouse wharehouse = new Wharehouse();
		wharehouse.setDescription(dto.getDescription());
		wharehouse.setName(dto.getName());
		wharehouse.setTypewharehouse(dto.getType());
		wharehouse.setState(SysBoticaConstant.STATE_ACTIVE);
		this.wharehouseRepository.save(wharehouse);
		return SysBoticaUtil.createHrefFromResource(wharehouse.getId(), SysBoticaResource.WHAREHOUSE);
	}

	@Override
	public HrefEntityDTO update(WharehouseDTORequest dto, Long id) {
		Wharehouse wharehouse = this.wharehouseRepository.findByIdAndState(id, SysBoticaConstant.STATE_ACTIVE).orElseThrow(()-> new SysBoticaEntityNotFoundException("not found"));
		wharehouse.setDescription(dto.getDescription());
		wharehouse.setName(dto.getName());
		wharehouse.setTypewharehouse(dto.getType());
		this.wharehouseRepository.save(wharehouse);
		return SysBoticaUtil.createHrefFromResource(wharehouse.getId(), SysBoticaResource.WHAREHOUSE);
	}

	@Override
	public HrefEntityDTO delete(Long id) {
		Wharehouse wharehouse = this.wharehouseRepository.findByIdAndState(id, SysBoticaConstant.STATE_ACTIVE).orElseThrow(()-> new SysBoticaEntityNotFoundException("not found"));
		wharehouse.setState(SysBoticaConstant.STATE_INACTIVE);
		this.wharehouseRepository.save(wharehouse);
		return SysBoticaUtil.createHrefFromResource(wharehouse.getId(), SysBoticaResource.WHAREHOUSE);
	}
	
	public WharehouseDTO toWharehouseDto(Wharehouse wharehouse) {
		return WharehouseDTO.builder().id(wharehouse.getId()).name(wharehouse.getName()).description(wharehouse.getDescription()).type(wharehouse.getTypewharehouse()).build();
	}

	@Override
	public List<WharehouseDTO> findByAllTypeDistribution() {
		return this.wharehouseRepository.findByTypewharehouseAndState(SysBoticaConstant.TYPE_DISTRIBUTION_WHAREHOUSE, SysBoticaConstant.STATE_ACTIVE).stream()
				.map((bean)-> toWharehouseDto(bean))
				.collect(Collectors.toList());
	}
	
}
