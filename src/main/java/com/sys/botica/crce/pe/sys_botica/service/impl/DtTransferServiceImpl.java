package com.sys.botica.crce.pe.sys_botica.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.dto.HrefEntityDTO;
import com.sys.botica.crce.pe.sys_botica.dto.ProductSaleDTO;
import com.sys.botica.crce.pe.sys_botica.dto.request.DtTransferDTORequest;
import com.sys.botica.crce.pe.sys_botica.errorhandler.SysBoticaEntityNotFoundException;
import com.sys.botica.crce.pe.sys_botica.model.DtEntryProduct;
import com.sys.botica.crce.pe.sys_botica.model.DtTransfer;
import com.sys.botica.crce.pe.sys_botica.model.Transfer;
import com.sys.botica.crce.pe.sys_botica.repository.DtEntryProductRepository;
import com.sys.botica.crce.pe.sys_botica.repository.DtTransferRepository;
import com.sys.botica.crce.pe.sys_botica.service.DtTransferService;
import com.sys.botica.crce.pe.sys_botica.util.SysBoticaResource;
import com.sys.botica.crce.pe.sys_botica.util.SysBoticaUtil;

@Service
public class DtTransferServiceImpl implements DtTransferService{

	final
	DtTransferRepository dtTransferRepository;
	
	final 
	DtEntryProductRepository dtEntryProductRepository;
	
	final
	SysBoticaUtil util;

	public DtTransferServiceImpl(DtTransferRepository dtTransferRepository, DtEntryProductRepository dtEntryProductRepository,SysBoticaUtil util) {
		this.dtTransferRepository = dtTransferRepository;
		this.dtEntryProductRepository = dtEntryProductRepository;
		this.util = util;
	}

	@Override
	public HrefEntityDTO saveAll(List<DtTransferDTORequest> details,Transfer transfer, String user) {
		List<DtTransfer> dttransfers = details.stream()
				.map(bean -> toTransfer(bean, transfer, user))
				.collect(Collectors.toList());
		this.dtTransferRepository.saveAll(dttransfers);
		return SysBoticaUtil.createHrefFromResource(transfer.getId(), SysBoticaResource.TRANSFER);
	}

	@Override
	public List<ProductSaleDTO> findByKeyWord(Long idsubsidiary, String keyWord) {
		return this.dtTransferRepository.findByKeyWordSQL(SysBoticaUtil.toIntExact(idsubsidiary), keyWord, SysBoticaConstant.STATE_ACTIVE)
				.stream()
				.map(bean -> this.toProductSaleDto(bean))
				.collect(Collectors.toList());
	}
	
	private ProductSaleDTO toProductSaleDto(DtTransfer dttransfer) {
		return ProductSaleDTO.builder()
				.id(dttransfer.getId())
				.amount(dttransfer.getAmount())
				.dateExpiration(dttransfer.getDtEntryProduct().getExpiratedate())
				.productName(dttransfer.getDtEntryProduct().getProduct().getName())
				.salePrice(dttransfer.getSaleprice())
				.build();				
	}
	
	private DtTransfer toTransfer(DtTransferDTORequest dtTransferDTORequest,Transfer transfer,String user) {
		DtEntryProduct dtEntryProduct = this.dtEntryProductRepository.findByIdAndState(dtTransferDTORequest.getIddtentryproduct(), SysBoticaConstant.STATE_ACTIVE).orElseThrow(()-> new SysBoticaEntityNotFoundException("not found details entre product"));
		return DtTransfer.builder()
				.dtEntryProduct(dtEntryProduct)
				.amount(dtTransferDTORequest.getAmount())
				.saleprice(dtTransferDTORequest.getSaleprice())
				.state(SysBoticaConstant.STATE_ACTIVE)
				.datecreated(new Date())
				.usercreated(user)
				.transfer(transfer)
				.build();
	}
	
}
