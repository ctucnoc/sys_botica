package com.sys.botica.crce.pe.sys_botica.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.dto.GenericDTO;
import com.sys.botica.crce.pe.sys_botica.dto.HrefEntityDTO;
import com.sys.botica.crce.pe.sys_botica.dto.WharehouseDTO;
import com.sys.botica.crce.pe.sys_botica.dto.WharehouseSubsidiaryDTO;
import com.sys.botica.crce.pe.sys_botica.dto.WharehouseTranferDTO;
import com.sys.botica.crce.pe.sys_botica.dto.request.WharehouseSubsidiaryDTORequest;
import com.sys.botica.crce.pe.sys_botica.errorhandler.SysBoticaEntityNotFoundException;
import com.sys.botica.crce.pe.sys_botica.errorhandler.SysBoticaEntityUnprocessableException;
import com.sys.botica.crce.pe.sys_botica.model.Subsidiary;
import com.sys.botica.crce.pe.sys_botica.model.Wharehouse;
import com.sys.botica.crce.pe.sys_botica.model.WharehouseSubsidiary;
import com.sys.botica.crce.pe.sys_botica.repository.SubsidiaryRepository;
import com.sys.botica.crce.pe.sys_botica.repository.WharehouseRepository;
import com.sys.botica.crce.pe.sys_botica.repository.WharehouseSubsidiaryRepository;
import com.sys.botica.crce.pe.sys_botica.service.WharehouseSubsidiaryService;
import com.sys.botica.crce.pe.sys_botica.util.SysBoticaResource;
import com.sys.botica.crce.pe.sys_botica.util.SysBoticaUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class WharehouseSubsidiaryServiceImpl implements WharehouseSubsidiaryService{
	
	final WharehouseSubsidiaryRepository wharehouseSubsidiaryRepository;
	final WharehouseRepository wharehouseRepository;
	final SubsidiaryRepository subsidiaryRepository;

	public WharehouseSubsidiaryServiceImpl(WharehouseSubsidiaryRepository wharehouseSubsidiaryRepository,WharehouseRepository wharehouseRepository,SubsidiaryRepository subsidiaryRepository) {
		this.wharehouseSubsidiaryRepository = wharehouseSubsidiaryRepository;
		this.wharehouseRepository = wharehouseRepository;
		this.subsidiaryRepository = subsidiaryRepository;
	}

	@Override
	public List<WharehouseDTO> findAllWharehouse(Long idsubsidiary) {
		Subsidiary subsidiary = this.subsidiaryRepository.findByIdAndState(idsubsidiary, SysBoticaConstant.STATE_ACTIVE).orElseThrow(()-> new SysBoticaEntityNotFoundException("not found"));
		return this.wharehouseSubsidiaryRepository.findBySubsidiaryAndState(subsidiary, SysBoticaConstant.STATE_ACTIVE)
				.stream()
				.filter(bean->bean.getWharehouse().getTypewharehouse().equals(SysBoticaConstant.TYPE_DISTRIBUTION_WHAREHOUSE))
				.map((bean)-> toWharehouseDto(bean.getWharehouse()))
				.collect(Collectors.toList());
	}
	
	public WharehouseDTO toWharehouseDto(Wharehouse wharehouse) {
		return WharehouseDTO.builder().id(wharehouse.getId()).name(wharehouse.getName()).description(wharehouse.getDescription()).type(wharehouse.getTypewharehouse()).build();
	}

	@Override
	public WharehouseTranferDTO findAllWharehouseTransfer(Long idsubsidiary) {
		log.info("service findAllWharehouseTransfer -> {} "+idsubsidiary);
		Subsidiary subsidiary = this.subsidiaryRepository.findByIdAndState(idsubsidiary, SysBoticaConstant.STATE_ACTIVE).orElseThrow(()-> new SysBoticaEntityNotFoundException("not found"));
		List<WharehouseDTO> list = this.wharehouseSubsidiaryRepository.findBySubsidiaryAndState(subsidiary, SysBoticaConstant.STATE_ACTIVE)
				.stream()
				.map((bean)-> toWharehouseDto(bean.getWharehouse()))
				.collect(Collectors.toList());
		log.info("service listAllType -> {} "+list.size());
		List<WharehouseDTO> distributions = list.stream().filter(bean->bean.getType().equals(SysBoticaConstant.TYPE_DISTRIBUTION_WHAREHOUSE)).collect(Collectors.toList());
		List<WharehouseDTO> subwarehouses = list.stream().filter(bean->bean.getType().equals(SysBoticaConstant.TYPE_SUB_WHAREHOUSE)).collect(Collectors.toList());
		return WharehouseTranferDTO.builder()
				.distributions(distributions)
				.subwarehouses(subwarehouses)
				.build();
	}

	@Override
	public Page<WharehouseSubsidiaryDTO> findBySubsidiary(Long idsubsidiary,Pageable pageable) {
		Subsidiary subsidiary = this.subsidiaryRepository.findByIdAndState(idsubsidiary, SysBoticaConstant.STATE_ACTIVE).orElseThrow(()-> new SysBoticaEntityNotFoundException("not found subsidiary"));
		return this.wharehouseSubsidiaryRepository.findBySubsidiaryAndState(subsidiary, SysBoticaConstant.STATE_ACTIVE,pageable)
				.map(bean -> toWharehouseSubsidiaryDto(bean));
	}
	
	private WharehouseSubsidiaryDTO toWharehouseSubsidiaryDto(WharehouseSubsidiary bean) {
		return WharehouseSubsidiaryDTO.builder()
				.id(bean.getId())
				.wharehouseDTO(WharehouseDTO.builder().id(bean.getWharehouse().getId()).name(bean.getWharehouse().getName()).type(bean.getWharehouse().getTypewharehouse()).build())
				.subsidiary(GenericDTO.builder().id(bean.getSubsidiary().getId()).name(bean.getSubsidiary().getName()).build())
				.build();
	}

	@Override
	public HrefEntityDTO save(WharehouseSubsidiaryDTORequest dto) {
		log.info("crce save -> {} "+dto);
		Wharehouse wharehouse = this.wharehouseRepository.findByIdAndState(dto.getIdWharehouse(), SysBoticaConstant.STATE_ACTIVE).orElseThrow(()-> new SysBoticaEntityNotFoundException("not found wharehouse"));
		Subsidiary subsidiary = this.subsidiaryRepository.findByIdAndState(dto.getIdSubsidiary(), SysBoticaConstant.STATE_ACTIVE).orElseThrow(()-> new SysBoticaEntityNotFoundException("not found subsidiary"));
		if(this.wharehouseSubsidiaryRepository.existsByWharehouseAndSubsidiaryAndState(wharehouse, subsidiary, SysBoticaConstant.STATE_ACTIVE))
			throw new SysBoticaEntityUnprocessableException("warehouse is already associated");
		WharehouseSubsidiary wharehouseSubsidiary = new WharehouseSubsidiary();
		wharehouseSubsidiary.setSubsidiary(subsidiary);
		wharehouseSubsidiary.setWharehouse(wharehouse);
		wharehouseSubsidiary.setState(SysBoticaConstant.STATE_ACTIVE);
		this.wharehouseSubsidiaryRepository.save(wharehouseSubsidiary);
		log.info("correctly save -> {} "+wharehouseSubsidiary);
		return SysBoticaUtil.createHrefFromResource(wharehouseSubsidiary.getId(), SysBoticaResource.WHAREHOUSESUBSIDIARY);
	}

	@Override
	public HrefEntityDTO delete(Long id) {
		WharehouseSubsidiary bean = this.wharehouseSubsidiaryRepository
				.findByIdAndState(id, SysBoticaConstant.STATE_ACTIVE)
				.orElseThrow(() -> new SysBoticaEntityNotFoundException("not found wherehouseSubsidiary"));
		bean.setState(SysBoticaConstant.STATE_INACTIVE);
		this.wharehouseSubsidiaryRepository.save(bean);
		return SysBoticaUtil.createHrefFromResource(bean.getId(), SysBoticaResource.WHAREHOUSESUBSIDIARY);
	}

}
