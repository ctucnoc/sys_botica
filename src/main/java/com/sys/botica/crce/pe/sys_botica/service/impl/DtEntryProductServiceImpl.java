package com.sys.botica.crce.pe.sys_botica.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.dto.DtEntryProductDTO;
import com.sys.botica.crce.pe.sys_botica.model.DtEntryProduct;
import com.sys.botica.crce.pe.sys_botica.repository.DtEntryProductRepository;
import com.sys.botica.crce.pe.sys_botica.service.DtEntryProductService;
import com.sys.botica.crce.pe.sys_botica.util.SysBoticaUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DtEntryProductServiceImpl implements DtEntryProductService{

	final
	DtEntryProductRepository dtEntryProductRepository;
	
	public DtEntryProductServiceImpl(DtEntryProductRepository dtEntryProductRepository) {
		this.dtEntryProductRepository = dtEntryProductRepository;
	}

	@Override
	public Page<DtEntryProductDTO> findByIdWharehouse(Long idwharehouse,String word_key,Pageable pageable) {
		log.info("service findByIdWharehouse -> {} "+idwharehouse);
		return this.dtEntryProductRepository.findByDtEntryProductSQL(SysBoticaUtil.toIntExact(idwharehouse), word_key, SysBoticaConstant.STATE_ACTIVE,pageable)
				.map(bean -> toDtEntryProductDto(bean));
	}
	
	private DtEntryProductDTO toDtEntryProductDto(DtEntryProduct dtEntryProduct) {
		return DtEntryProductDTO.builder()
				.id(dtEntryProduct.getId())
				.idProduct(dtEntryProduct.getProduct().getId())
				.productName(dtEntryProduct.getProduct().getName())
				.lotnumber(dtEntryProduct.getLotnumber())
				.expiratedate(dtEntryProduct.getExpiratedate().toString())
				.amount(dtEntryProduct.getAmount())
				.purchaseprecio(dtEntryProduct.getPurchaseprecio())
				.build();
	}
}
