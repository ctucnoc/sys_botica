package com.sys.botica.crce.pe.sys_botica.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.sys.botica.crce.pe.sys_botica.dto.HrefEntityDTO;
import com.sys.botica.crce.pe.sys_botica.dto.ProductDTO;
import com.sys.botica.crce.pe.sys_botica.dto.request.ProductDTORequest;

public interface ProductService {
	public HrefEntityDTO save(ProductDTORequest product);
	public HrefEntityDTO update(ProductDTORequest product,Long id);
	public HrefEntityDTO delete(Long id);
	public Page<ProductDTO> findByKeyWord(String key_word,Pageable pageable);
	public ProductDTO findByID(Long id);
	public List<ProductDTO> findByKeyWordSQL(String key_word);
}
