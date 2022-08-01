package com.sys.botica.crce.pe.sys_botica.service;

import java.util.List;
import com.sys.botica.crce.pe.sys_botica.dto.ProductImgDTO;

public interface ProductImgService {
	public List<ProductImgDTO> findByProduct(Long idProduct);
}
