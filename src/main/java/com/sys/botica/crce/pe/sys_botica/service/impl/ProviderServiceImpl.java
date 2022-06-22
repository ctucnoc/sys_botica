package com.sys.botica.crce.pe.sys_botica.service.impl;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.dto.HrefEntityDTO;
import com.sys.botica.crce.pe.sys_botica.dto.ProviderDTO;
import com.sys.botica.crce.pe.sys_botica.dto.request.ProviderDTORequest;
import com.sys.botica.crce.pe.sys_botica.errorhandler.SysBoticaEntityNotFoundException;
import com.sys.botica.crce.pe.sys_botica.model.Provider;
import com.sys.botica.crce.pe.sys_botica.repository.ProviderRepository;
import com.sys.botica.crce.pe.sys_botica.service.ProviderService;
import com.sys.botica.crce.pe.sys_botica.util.SysBoticaResource;
import com.sys.botica.crce.pe.sys_botica.util.SysBoticaUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class ProviderServiceImpl implements ProviderService{

	final ProviderRepository providerRepository;

	public ProviderServiceImpl(ProviderRepository providerRepository) {
		this.providerRepository = providerRepository;
	}

	@Override
	public Page<ProviderDTO> findByDescription(String name,Pageable pageable) {
		return this.providerRepository.findByBussinesnameLikeSql(name, SysBoticaConstant.STATE_ACTIVE,pageable)
				.map((bean)-> toProviderDto(bean));
	}

	@Override
	public HrefEntityDTO update(ProviderDTORequest dto, Long id) {
		Provider provider = this.providerRepository.findByIdAndState(id, SysBoticaConstant.STATE_ACTIVE).orElseThrow(()->new SysBoticaEntityNotFoundException("not found"));
		provider.setAddress(dto.getAddress());
		provider.setBussinesname(dto.getBussinesname());
		provider.setCellphone(dto.getCellphone());
		provider.setEmail(dto.getEmail());
		provider.setRepresentative(dto.getRepresentative());
		provider.setRuc(dto.getRuc());
		provider.setState(SysBoticaConstant.STATE_ACTIVE);
		provider.setTradename(dto.getTradename());
		provider.setWebpage(dto.getWebpage());
		this.providerRepository.save(provider);
		log.info("update provaider -> {} " +provider);
		return SysBoticaUtil.createHrefFromResource(provider.getId(), SysBoticaResource.PROVIDER);

	}

	@Override
	public HrefEntityDTO delete(Long id) {
		Provider provider = this.providerRepository.findByIdAndState(id, SysBoticaConstant.STATE_ACTIVE).orElseThrow(()->new SysBoticaEntityNotFoundException("not found"));
		provider.setState(SysBoticaConstant.STATE_INACTIVE);
		this.providerRepository.save(provider);
		return SysBoticaUtil.createHrefFromResource(provider.getId(), SysBoticaResource.PROVIDER);
	}

	@Override
	public HrefEntityDTO save(ProviderDTORequest dto) {
		Provider provider = new Provider();
		provider.setAddress(dto.getAddress());
		provider.setBussinesname(dto.getBussinesname());
		provider.setCellphone(dto.getCellphone());
		provider.setEmail(dto.getEmail());
		provider.setRepresentative(dto.getRepresentative());
		provider.setRuc(dto.getRuc());
		provider.setState(SysBoticaConstant.STATE_ACTIVE);
		provider.setTradename(dto.getTradename());
		provider.setWebpage(dto.getWebpage());
		this.providerRepository.save(provider);
		log.info("save provaider -> {} " +provider);
		return SysBoticaUtil.createHrefFromResource(provider.getId(), SysBoticaResource.PROVIDER);
	}

	@Override
	public ProviderDTO findById(Long id) {
		Provider provider = this.providerRepository.findByIdAndState(id, SysBoticaConstant.STATE_ACTIVE).orElseThrow(()->new SysBoticaEntityNotFoundException("not found"));
		return toProviderDto(provider);
	}
	
	public ProviderDTO toProviderDto(Provider provider) {
		return ProviderDTO.builder()
				.id(provider.getId())
				.bussinesname(provider.getBussinesname())
				.cellphone(provider.getCellphone())
				.email(provider.getEmail())
				.representative(provider.getRepresentative())
				.ruc(provider.getRuc())
				.tradename(provider.getTradename())
				.address(provider.getAddress())
				.webpage(provider.getWebpage())
				.build();
	}

	@Override
	public Page<ProviderDTO> findAll(Pageable pageable) {
		return this.providerRepository.findByState(SysBoticaConstant.STATE_ACTIVE, pageable)
				.map((bean)-> toProviderDto(bean));
	}
	
	
}
