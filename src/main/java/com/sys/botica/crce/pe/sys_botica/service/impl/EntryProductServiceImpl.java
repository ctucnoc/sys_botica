package com.sys.botica.crce.pe.sys_botica.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.dto.HrefEntityDTO;
import com.sys.botica.crce.pe.sys_botica.dto.request.DtEntryProductDTORequest;
import com.sys.botica.crce.pe.sys_botica.dto.request.EntryProductDTORequest;
import com.sys.botica.crce.pe.sys_botica.errorhandler.SysBoticaEntityNotFoundException;
import com.sys.botica.crce.pe.sys_botica.model.DtEntryProduct;
import com.sys.botica.crce.pe.sys_botica.model.EntryDocument;
import com.sys.botica.crce.pe.sys_botica.model.EntryProduct;
import com.sys.botica.crce.pe.sys_botica.model.Product;
import com.sys.botica.crce.pe.sys_botica.model.Provider;
import com.sys.botica.crce.pe.sys_botica.model.Wharehouse;
import com.sys.botica.crce.pe.sys_botica.repository.DtEntryProductRepository;
import com.sys.botica.crce.pe.sys_botica.repository.EntryDocumentRepository;
import com.sys.botica.crce.pe.sys_botica.repository.EntryProductRepository;
import com.sys.botica.crce.pe.sys_botica.repository.ProductRepository;
import com.sys.botica.crce.pe.sys_botica.repository.ProviderRepository;
import com.sys.botica.crce.pe.sys_botica.repository.WharehouseRepository;
import com.sys.botica.crce.pe.sys_botica.security.JwtProvider;
import com.sys.botica.crce.pe.sys_botica.service.EntryProductService;
import com.sys.botica.crce.pe.sys_botica.util.SysBoticaResource;
import com.sys.botica.crce.pe.sys_botica.util.SysBoticaUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EntryProductServiceImpl implements EntryProductService{

	final EntryProductRepository entryProductRepository;
	final ProviderRepository providerRepository;
	final WharehouseRepository wharehouseRepository;
	final JwtProvider jwtProvider;
	final EntryDocumentRepository entryDocumentRepository;
	final SysBoticaUtil sysBoticaUtil;
	final DtEntryProductRepository dtEntryProductRepository;
	final ProductRepository productRepository;

	public EntryProductServiceImpl(EntryProductRepository entryProductRepository,ProviderRepository providerRepository,WharehouseRepository wharehouseRepository,JwtProvider jwtProvider,
			EntryDocumentRepository entryDocumentRepository,SysBoticaUtil sysBoticaUtil,DtEntryProductRepository dtEntryProductRepository,ProductRepository productRepository) {
		this.entryProductRepository = entryProductRepository;
		this.providerRepository = providerRepository;
		this.wharehouseRepository = wharehouseRepository;
		this.jwtProvider = jwtProvider;
		this.entryDocumentRepository = entryDocumentRepository;
		this.sysBoticaUtil = sysBoticaUtil;
		this.dtEntryProductRepository = dtEntryProductRepository;
		this.productRepository = productRepository;
	}

	@Override
	public HrefEntityDTO save(EntryProductDTORequest dto, String bearerToken) {
		log.info("dto -> {} " + dto);
		String[] userAndSubsidiary= StringUtils.split(this.jwtProvider.getUsername(this.sysBoticaUtil.resolveToken(bearerToken)),String.valueOf(Character.LINE_SEPARATOR));
		String username = userAndSubsidiary[0];
		EntryDocument entryDocument = this.entryDocumentRepository.findByIdAndState(dto.getIdEntryDocument(), SysBoticaConstant.STATE_ACTIVE).orElseThrow(()-> new SysBoticaEntityNotFoundException("not found document"));
		Provider provider = this.providerRepository.findByIdAndState(dto.getIdProvider(), SysBoticaConstant.STATE_ACTIVE).orElseThrow(()-> new SysBoticaEntityNotFoundException("not found provider"));
		Wharehouse wharehouse = this.wharehouseRepository.findByIdAndState(dto.getIdWharehouse(), SysBoticaConstant.STATE_ACTIVE).orElseThrow(()-> new SysBoticaEntityNotFoundException("not found wharehouse"));
		EntryProduct entryProduct = new EntryProduct();
		entryProduct.setProvider(provider);
		entryProduct.setWharehouse(wharehouse);
		entryProduct.setDateCreated(new Date());
		entryProduct.setEntryDocument(entryDocument);
		entryProduct.setState(SysBoticaConstant.STATE_ACTIVE);
		entryProduct.setUserCreated(username);
		this.entryProductRepository.save(entryProduct);
		log.info("dtEntryProducts size dto -> {} " + dto.getDetails().size());
		List<DtEntryProduct> dtEntryProducts = dto.getDetails().stream().map(bean-> toDtEntryProduct(bean, entryProduct)).collect(Collectors.toList());
		log.info("dtEntryProducts size -> {} " + dtEntryProducts.size());
		this.dtEntryProductRepository.saveAll(dtEntryProducts);
		return SysBoticaUtil.createHrefFromResource(entryProduct.getId(), SysBoticaResource.AUTHORITY);
	}
	
	public DtEntryProduct toDtEntryProduct(DtEntryProductDTORequest dto,EntryProduct entryProduct) {
		Product product = this.productRepository.findProductByIdAndState(dto.getIdProduct(), SysBoticaConstant.STATE_ACTIVE).orElseThrow(()-> new SysBoticaEntityNotFoundException("not found product"));
		DtEntryProduct dtEntryProduct = new DtEntryProduct();
		dtEntryProduct.setAmount(dto.getAmount());
		dtEntryProduct.setDatecreated(new Date());
		dtEntryProduct.setEntryProduct(entryProduct);
		dtEntryProduct.setProduct(product);
		dtEntryProduct.setExpiratedate(SysBoticaUtil.parseDate(dto.getExpiratedate()));
		dtEntryProduct.setLotnumber(dto.getLotnumber());
		dtEntryProduct.setPurchaseprecio(SysBoticaUtil.roundTwoDecimal(dto.getPurchaseprecio()));
		dtEntryProduct.setState(SysBoticaConstant.STATE_ACTIVE);
		dtEntryProduct.setUsercreated(entryProduct.getUserCreated());
		return dtEntryProduct;
	}
	
}
