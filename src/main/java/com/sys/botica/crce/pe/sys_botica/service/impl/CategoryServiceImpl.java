package com.sys.botica.crce.pe.sys_botica.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.dto.CategoryDTO;
import com.sys.botica.crce.pe.sys_botica.dto.HrefEntityDTO;
import com.sys.botica.crce.pe.sys_botica.dto.request.CategoryDTORequest;
import com.sys.botica.crce.pe.sys_botica.errorhandler.SysBoticaEntityNotFoundException;
import com.sys.botica.crce.pe.sys_botica.model.Category;
import com.sys.botica.crce.pe.sys_botica.param.CategoryParam;
import com.sys.botica.crce.pe.sys_botica.repository.CategoryRepository;
import com.sys.botica.crce.pe.sys_botica.service.CategoryService;
import com.sys.botica.crce.pe.sys_botica.util.SysBoticaResource;
import com.sys.botica.crce.pe.sys_botica.util.SysBoticaUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service
public class CategoryServiceImpl implements CategoryService {

	final 
	CategoryRepository categoryRepository;
	
	final 
	SysBoticaUtil sysBoticaUtil;

	public CategoryServiceImpl(CategoryRepository categoryRepository, SysBoticaUtil sysBoticaUtil) {
		this.categoryRepository = categoryRepository;
		this.sysBoticaUtil = sysBoticaUtil;
	}

	@Override
	public Page<CategoryDTO> findByDescription(CategoryParam params) {
		Pageable pageable = this.sysBoticaUtil.getPageable(params.getPageNumber(), params.getPerPage(),
				params.getSortOrder(), params.getSortField());
		Page<Category> param1 = this.categoryRepository.findByNameAndState(params.getKey_word(),
				SysBoticaConstant.STATE_ACTIVE, pageable);
		return param1.map((bean) -> new CategoryDTO(bean.getId(), bean.getName(), bean.getDescription()));
	}

	@Override
	public HrefEntityDTO update(CategoryDTORequest dto, Integer id) {
		Category category = this.categoryRepository.findCategoryByIdAndState(id, SysBoticaConstant.STATE_ACTIVE)
				.orElseThrow(() -> new SysBoticaEntityNotFoundException("not found"));
		category.setDescription(dto.getDescription());
		category.setName(dto.getName());
		this.categoryRepository.save(category);
		return SysBoticaUtil.createHrefFromResource(category.getId(), SysBoticaResource.CATEGORY);
	}

	@Override
	public HrefEntityDTO save(CategoryDTORequest dto) {
		Category category = new Category();
		category.setDescription(dto.getDescription());
		category.setName(dto.getName());
		category.setState(SysBoticaConstant.STATE_ACTIVE);
		this.categoryRepository.save(category);
		return SysBoticaUtil.createHrefFromResource(category.getId(), SysBoticaResource.CATEGORY);
	}

	@Override
	public HrefEntityDTO delete(Integer id) {
		Category category = this.categoryRepository.findCategoryByIdAndState(id, SysBoticaConstant.STATE_ACTIVE)
				.orElseThrow(() -> new SysBoticaEntityNotFoundException("not found"));
		category.setState(SysBoticaConstant.STATE_INACTIVE);
		this.categoryRepository.save(category);
		return SysBoticaUtil.createHrefFromResource(category.getId(), SysBoticaResource.CATEGORY);
	}

	@Override
	public CategoryDTO findById(Integer id) {
		Category category = this.categoryRepository.findCategoryByIdAndState(id, SysBoticaConstant.STATE_ACTIVE)
				.orElseThrow(() -> new SysBoticaEntityNotFoundException("not found"));
		return CategoryDTO.builder().id(category.getId()).name(category.getName())
				.description(category.getDescription()).build();
	}
	
	public CategoryDTO toCategoryDto(Category category) {
		return CategoryDTO.builder()
				.id(category.getId())
				.name(category.getName())
				.description(category.getDescription())
				.build();
	}

	@Override
	public List<CategoryDTO> findAll() {
		log.info("crce service findAll -> {} findAll category");
		return this.categoryRepository.findByState(SysBoticaConstant.STATE_ACTIVE).stream()
				.map(bean -> toCategoryDto(bean))
				.collect(Collectors.toList());
	}

}
