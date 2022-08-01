package com.sys.botica.crce.pe.sys_botica.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.dto.ProductImgDTO;
import com.sys.botica.crce.pe.sys_botica.errorhandler.SysBoticaEntityNotFoundException;
import com.sys.botica.crce.pe.sys_botica.model.Product;
import com.sys.botica.crce.pe.sys_botica.model.ProductImg;
import com.sys.botica.crce.pe.sys_botica.repository.ProductImgRepository;
import com.sys.botica.crce.pe.sys_botica.repository.ProductRepository;
import com.sys.botica.crce.pe.sys_botica.service.ProductImgService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductImgServiceImpl implements ProductImgService{

	final
	ProductImgRepository productImgRepository;
	
	final
	ProductRepository productRepository;

	public ProductImgServiceImpl(ProductImgRepository productImgRepository,ProductRepository productRepository) {
		this.productImgRepository = productImgRepository;
		this.productRepository = productRepository;
	}

	@Override
	public List<ProductImgDTO> findByProduct(Long idProduct) {
		log.info("crce findByProduct -> {} "+idProduct);
		Product product = this.productRepository.findProductByIdAndState(idProduct, SysBoticaConstant.STATE_ACTIVE).orElseThrow(()-> new SysBoticaEntityNotFoundException("not found product"));
		return this.productImgRepository.findByProductAndState(product, SysBoticaConstant.STATE_ACTIVE)
		.stream()
		.map((bean) -> convertToProductImg(bean))
		.collect(Collectors.toList());
	}
	
	private ProductImgDTO convertToProductImg(ProductImg productImg) {
		return ProductImgDTO.builder()
				.idProduct(productImg.getProduct().getId())
				.name(productImg.getName())
				.type(productImg.getType())
				.url(productImg.getUrl())
				.build();
	}	
	
}
