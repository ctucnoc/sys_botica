package com.sys.botica.crce.pe.sys_botica.service.impl;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.dto.CloudinaryImgDTO;
import com.sys.botica.crce.pe.sys_botica.errorhandler.SysBoticaEntityNotFoundException;
import com.sys.botica.crce.pe.sys_botica.model.Product;
import com.sys.botica.crce.pe.sys_botica.model.ProductImg;
import com.sys.botica.crce.pe.sys_botica.repository.ProductImgRepository;
import com.sys.botica.crce.pe.sys_botica.repository.ProductRepository;
import com.sys.botica.crce.pe.sys_botica.service.CloudinaryService;
import com.sys.botica.crce.pe.sys_botica.service.FileService;

@Service
public class FileServiceImpl implements FileService{
	
	final
	CloudinaryService cloudinaryService;
	
	final
	ProductImgRepository productImgRepository;
	
	final ProductRepository productRepository;
	
	public FileServiceImpl(CloudinaryService cloudinaryService, ProductImgRepository productImgRepository,ProductRepository productRepository) {
		this.cloudinaryService = cloudinaryService;
		this.productImgRepository = productImgRepository;
		this.productRepository = productRepository;
	}

	@Override
	public CloudinaryImgDTO save(MultipartFile multipartFile,Long idProduct) throws IOException {
		Product product = this.productRepository.findProductByIdAndState(idProduct,SysBoticaConstant.STATE_ACTIVE).orElseThrow(()-> new SysBoticaEntityNotFoundException("product not found"));
		CloudinaryImgDTO dto = this.cloudinaryService.upload(multipartFile);
		ProductImg productImg = new ProductImg();
		productImg.setIdApi(dto.getPublicId());
		productImg.setName(multipartFile.getOriginalFilename());
		productImg.setType(multipartFile.getContentType());
		productImg.setUrl(dto.getUrl());
		productImg.setProduct(product);
		productImg.setState(SysBoticaConstant.STATE_ACTIVE);
		this.productImgRepository.save(productImg);
		return dto;
	}

}
