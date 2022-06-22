package com.sys.botica.crce.pe.sys_botica.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.dto.GenericDTO;
import com.sys.botica.crce.pe.sys_botica.dto.HrefEntityDTO;
import com.sys.botica.crce.pe.sys_botica.dto.ProductDTO;
import com.sys.botica.crce.pe.sys_botica.dto.request.ProductDTORequest;
import com.sys.botica.crce.pe.sys_botica.errorhandler.SysBoticaEntityNotFoundException;
import com.sys.botica.crce.pe.sys_botica.errorhandler.SysBoticaEntityUnprocessableException;
import com.sys.botica.crce.pe.sys_botica.model.Category;
import com.sys.botica.crce.pe.sys_botica.model.Mark;
import com.sys.botica.crce.pe.sys_botica.model.Product;
import com.sys.botica.crce.pe.sys_botica.model.Unit;
import com.sys.botica.crce.pe.sys_botica.repository.CategoryRepository;
import com.sys.botica.crce.pe.sys_botica.repository.MarkRepository;
import com.sys.botica.crce.pe.sys_botica.repository.ProductRepository;
import com.sys.botica.crce.pe.sys_botica.repository.UnitRespository;
import com.sys.botica.crce.pe.sys_botica.service.ProductService;
import com.sys.botica.crce.pe.sys_botica.util.SysBoticaResource;
import com.sys.botica.crce.pe.sys_botica.util.SysBoticaUtil;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

	final ProductRepository productRepository;
	final CategoryRepository categoryRepository;
	final MarkRepository markRepository;
	final UnitRespository unitRespository;
	final SysBoticaUtil sysBoticaUtil;

	public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository,
			MarkRepository markRepository, UnitRespository unitRespository,SysBoticaUtil sysBoticaUtil) {
		this.productRepository = productRepository;
		this.categoryRepository = categoryRepository;
		this.markRepository = markRepository;
		this.unitRespository = unitRespository;
		this.sysBoticaUtil = sysBoticaUtil;
	}

	@Override
	public HrefEntityDTO save(ProductDTORequest product) {
		if (this.productRepository.existsByProductNameAndProductStatus(product.getName(),
				SysBoticaConstant.STATE_ACTIVE))
			throw new SysBoticaEntityUnprocessableException("product alredy exists");
		Category category = this.categoryRepository
				.findCategoryByIdAndState(product.getIdcategory(), SysBoticaConstant.STATE_ACTIVE)
				.orElseThrow(() -> new SysBoticaEntityNotFoundException("not found category"));
		Mark mark = this.markRepository.findMarkByIdAndState(product.getIdmark(), SysBoticaConstant.STATE_ACTIVE)
				.orElseThrow(() -> new SysBoticaEntityNotFoundException("not found mark"));
		Unit unit = this.unitRespository.findUnitByIdAndState(product.getIdunit(), SysBoticaConstant.STATE_ACTIVE)
				.orElseThrow(() -> new SysBoticaEntityNotFoundException("not found unit"));
		Product bean = new Product();
		bean.setCategory(category);
		bean.setMark(mark);
		bean.setUnit(unit);
		bean.setName(product.getName());
		bean.setIsbatch(product.getIsbatch());
		bean.setIsexpiratedate(product.getIsexpiratedate());
		bean.setIsgeneric(product.getIsgeneric());
		bean.setIskit(product.getIskit());
		bean.setBarcode(product.getBarcode());
		bean.setIscontrolled(product.getIscontrolled());
		bean.setIsmedicine(product.getIsmedicine());
		bean.setIsrefrigeration(product.getIsrefrigeration());
		bean.setSummaryname(product.getSummaryname());
		bean.setState(SysBoticaConstant.STATE_ACTIVE);
		this.productRepository.save(bean);
		return SysBoticaUtil.createHrefFromResource(bean.getId(), SysBoticaResource.PRODUCT);
	}

	@Override
	public Page<ProductDTO> findByKeyWord(String key_word, Pageable pageable) {
		Page<Product> params = this.productRepository.findByProductKeyWord(key_word, SysBoticaConstant.STATE_ACTIVE,
				pageable);
		return params.map((product) -> converToDto(product));
	}

	@Override
	public ProductDTO findByID(Long id) {
		Product product = this.productRepository.findProductByIdAndState(id, SysBoticaConstant.STATE_ACTIVE)
				.orElseThrow(() -> new SysBoticaEntityNotFoundException("not found product"));
		return converToDto(product);
	}

	public ProductDTO converToDto(Product product) {
		return ProductDTO.builder().id(product.getId()).summaryname(product.getSummaryname())
				.isbatch(product.getIsbatch()).isexpiratedate(product.getIsexpiratedate())
				.isgeneric(product.getIsgeneric()).iskit(product.getIskit())
				.isrefrigeration(product.getIsrefrigeration())
				.iscontrolled(product.getIscontrolled())
				.ismedicine(product.getIsmedicine())
				.barcode(product.getBarcode())
				.category(GenericDTO.builder().id(product.getCategory().getId()).name(product.getCategory().getName())
						.build())
				.mark(GenericDTO.builder().id(product.getMark().getId()).name(product.getMark().getName()).build())
				.unit(GenericDTO.builder().id(product.getUnit().getId()).name(product.getUnit().getDescription())
						.build())
				.name(product.getName()).build();
	}

	@Override
	public HrefEntityDTO update(ProductDTORequest product, Long id) {
		Product bean = this.productRepository.findProductByIdAndState(id, SysBoticaConstant.STATE_ACTIVE)
				.orElseThrow(() -> new SysBoticaEntityNotFoundException("not found product"));
		Category category = this.categoryRepository
				.findCategoryByIdAndState(product.getIdcategory(), SysBoticaConstant.STATE_ACTIVE)
				.orElseThrow(() -> new SysBoticaEntityNotFoundException("not found category"));
		Mark mark = this.markRepository.findMarkByIdAndState(product.getIdmark(), SysBoticaConstant.STATE_ACTIVE)
				.orElseThrow(() -> new SysBoticaEntityNotFoundException("not found mark"));
		Unit unit = this.unitRespository.findUnitByIdAndState(product.getIdunit(), SysBoticaConstant.STATE_ACTIVE)
				.orElseThrow(() -> new SysBoticaEntityNotFoundException("not found unit"));
		bean.setName(product.getName());
		bean.setSummaryname(product.getSummaryname());
		bean.setIsbatch(product.getIsbatch());
		bean.setIsexpiratedate(product.getIsexpiratedate());
		bean.setIsgeneric(product.getIsgeneric());
		bean.setIskit(product.getIskit());
		bean.setIsrefrigeration(product.getIsrefrigeration());
		bean.setBarcode(product.getBarcode());
		bean.setIscontrolled(product.getIscontrolled());
		bean.setIsmedicine(product.getIsmedicine());
		bean.setMark(mark);
		bean.setCategory(category);
		bean.setUnit(unit);
		this.productRepository.save(bean);
		return SysBoticaUtil.createHrefFromResource(bean.getId(), SysBoticaResource.PRODUCT);
	}

	@Override
	public HrefEntityDTO delete(Long id) {
		Product bean = this.productRepository.findProductByIdAndState(id, SysBoticaConstant.STATE_ACTIVE)
				.orElseThrow(() -> new SysBoticaEntityNotFoundException("not found product"));
		bean.setState(SysBoticaConstant.STATE_INACTIVE);
		this.productRepository.save(bean);
		return SysBoticaUtil.createHrefFromResource(bean.getId(), SysBoticaResource.PRODUCT);	}

	@Override
	public List<ProductDTO> findByKeyWordSQL(String key_word) {
		return this.productRepository.findByKeyWordSQL(key_word, SysBoticaConstant.STATE_ACTIVE).stream()
				.limit(15)
				.map((bean)-> converToDto(bean))
				.collect(Collectors.toList());
	}

}
