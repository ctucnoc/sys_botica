package com.sys.botica.crce.pe.sys_botica.service;


import java.util.List;

import org.springframework.data.domain.Page;
import com.sys.botica.crce.pe.sys_botica.dto.CategoryDTO;
import com.sys.botica.crce.pe.sys_botica.dto.HrefEntityDTO;
import com.sys.botica.crce.pe.sys_botica.dto.request.CategoryDTORequest;
import com.sys.botica.crce.pe.sys_botica.param.CategoryParam;

public interface CategoryService {

	public Page<CategoryDTO> findByDescription(CategoryParam params);
	
	public HrefEntityDTO update(CategoryDTORequest dto,Integer id);
	
	public HrefEntityDTO delete(Integer id);
	
	public HrefEntityDTO save(CategoryDTORequest dto);
	
	public CategoryDTO findById(Integer id);
	
	public List<CategoryDTO> findAll();
}
