package com.sys.botica.crce.pe.sys_botica.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.dto.DtSaleDTO;
import com.sys.botica.crce.pe.sys_botica.dto.HrefEntityDTO;
import com.sys.botica.crce.pe.sys_botica.dto.request.DtSaleDTORequest;
import com.sys.botica.crce.pe.sys_botica.errorhandler.SysBoticaEntityNotFoundException;
import com.sys.botica.crce.pe.sys_botica.model.DtSale;
import com.sys.botica.crce.pe.sys_botica.model.DtTransfer;
import com.sys.botica.crce.pe.sys_botica.model.Sale;
import com.sys.botica.crce.pe.sys_botica.repository.DtSaleRepository;
import com.sys.botica.crce.pe.sys_botica.repository.DtTransferRepository;
import com.sys.botica.crce.pe.sys_botica.repository.SaleRepository;
import com.sys.botica.crce.pe.sys_botica.service.DtSaleService;
import com.sys.botica.crce.pe.sys_botica.util.SysBoticaResource;
import com.sys.botica.crce.pe.sys_botica.util.SysBoticaUtil;

@Service
public class DtSaleServiceImpl implements DtSaleService{

	final
	DtSaleRepository dtSaleRepository;
	
	final 
	DtTransferRepository dtTransferRepository;
	
	final
	SaleRepository saleRepository;

	public DtSaleServiceImpl(DtSaleRepository dtSaleRepository,DtTransferRepository dtTransferRepository,SaleRepository saleRepository) {
		this.dtSaleRepository = dtSaleRepository;
		this.dtTransferRepository = dtTransferRepository;
		this.saleRepository = saleRepository;
	}

	@Override
	public HrefEntityDTO saveAll(List<DtSaleDTORequest> details, Sale sale, String user) {
		List<DtSale> list = details.stream()
				.map(bean -> todtSale(bean, sale, user))
				.collect(Collectors.toList());
		this.dtSaleRepository.saveAll(list);
		return SysBoticaUtil.createHrefFromResource(sale.getId(), SysBoticaResource.SALE);
	}
	
	private DtSale todtSale(DtSaleDTORequest dto,Sale sale,String user) {
		DtTransfer dtTransfer = this.dtTransferRepository.findByIdAndState(dto.getIdDttransfer(), SysBoticaConstant.STATE_ACTIVE).orElseThrow(()-> new SysBoticaEntityNotFoundException("not found details transfer"));
		return DtSale.builder()
				.dtTransfer(dtTransfer)
				.sale(sale)
				.saleamount(dto.getSaleamount())
				.saleprice(dto.getSaleprice())
				.usercreated(user)
				.datecreated(new Date())
				.state(SysBoticaConstant.STATE_ACTIVE)
				.build();
	}
	
	private DtSaleDTO toDtSaleDto(DtSale dtSale) {
		return DtSaleDTO.builder()
				.dateExpiration(dtSale.getDtTransfer().getDtEntryProduct().getExpiratedate().toString())
				.productName(dtSale.getDtTransfer().getDtEntryProduct().getProduct().getName())
				.saleAmount(dtSale.getSaleamount())
				.salePrice(dtSale.getSaleprice())
				.id(dtSale.getId())
				.build();			
	}

	@Override
	public List<DtSaleDTO> findBySale(Long idSale) {
		Sale sale = this.saleRepository.findByIdAndState(idSale,SysBoticaConstant.STATE_ACTIVE).orElseThrow(()-> new SysBoticaEntityNotFoundException("not found sale"));
		return this.dtSaleRepository.findBySaleAndState(sale, SysBoticaConstant.STATE_ACTIVE).stream()
		.map(bean ->toDtSaleDto(bean))
		.collect(Collectors.toList());
	}	
	
}
