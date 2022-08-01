package com.sys.botica.crce.pe.sys_botica.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.dto.ProductImgDTO;
import com.sys.botica.crce.pe.sys_botica.service.ProductImgService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(SysBoticaConstant.API_VERSION + SysBoticaConstant.RESOURCE_PRODUCTIMGS)
@CrossOrigin(origins = SysBoticaConstant.PATH_FROTEND_SYSCE)
public class ProductoImgController {

	final
	ProductImgService productImgService;

	public ProductoImgController(ProductImgService productImgService) {
		this.productImgService = productImgService;
	}
	
	@GetMapping(SysBoticaConstant.RESOURCE_PRODUCTIMGS_PRODUCTIMG + SysBoticaConstant.RESOURCE_PRODUCT_ID)
	public ResponseEntity<List<ProductImgDTO>> findByProduct(@PathVariable Long idProduct){
		log.info("crce findByProduct -> {} "+idProduct);
		return ResponseEntity.ok(this.productImgService.findByProduct(idProduct));
	}
		
}
