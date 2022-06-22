package com.sys.botica.crce.pe.sys_botica.service.impl;

import javax.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.dto.HrefEntityDTO;
import com.sys.botica.crce.pe.sys_botica.dto.MarkDTO;
import com.sys.botica.crce.pe.sys_botica.dto.request.MarkDTORequest;
import com.sys.botica.crce.pe.sys_botica.errorhandler.SysBoticaEntityNotFoundException;
import com.sys.botica.crce.pe.sys_botica.model.Mark;
import com.sys.botica.crce.pe.sys_botica.repository.MarkRepository;
import com.sys.botica.crce.pe.sys_botica.service.MarkService;
import com.sys.botica.crce.pe.sys_botica.util.SysBoticaResource;
import com.sys.botica.crce.pe.sys_botica.util.SysBoticaUtil;

@Service
@Transactional
public class MarkServiceImpl implements MarkService {

	final MarkRepository markRepository;

	public MarkServiceImpl(MarkRepository markRepository) {
		this.markRepository = markRepository;
	}

	@Override
	public Page<MarkDTO> findAll(String key_word,Pageable pageable) {
		Page<Mark> params = this.markRepository.findByDescriptionAndState(key_word,SysBoticaConstant.STATE_ACTIVE, pageable);
		return params.map((bean) -> MarkDTO.builder().id(bean.getId()).name(bean.getName()).build());
	}

	@Override
	public HrefEntityDTO save(MarkDTORequest mark) {
		Mark bean = new Mark();
		bean.setName(mark.getName());
		bean.setState(SysBoticaConstant.STATE_ACTIVE);
		this.markRepository.save(bean);
		return SysBoticaUtil.createHrefFromResource(bean.getId(), SysBoticaResource.MARK);
	}

	@Override
	public HrefEntityDTO update(MarkDTORequest mark, Integer id) {
		Mark bean = this.markRepository.findMarkByIdAndState(id,SysBoticaConstant.STATE_ACTIVE).orElseThrow(()-> new SysBoticaEntityNotFoundException("not found mark"));
		bean.setName(mark.getName());
		this.markRepository.save(bean);
		return SysBoticaUtil.createHrefFromResource(bean.getId(), SysBoticaResource.MARK);
	}

	@Override
	public HrefEntityDTO delete(Integer id) {
		Mark bean = this.markRepository.findMarkByIdAndState(id,SysBoticaConstant.STATE_ACTIVE).orElseThrow(()-> new SysBoticaEntityNotFoundException("not found mark"));
		bean.setState(SysBoticaConstant.STATE_INACTIVE);
		this.markRepository.save(bean);
		return SysBoticaUtil.createHrefFromResource(bean.getId(), SysBoticaResource.MARK);
	}

	@Override
	public MarkDTO findById(Integer id) {
		Mark bean = this.markRepository.findMarkByIdAndState(id,SysBoticaConstant.STATE_ACTIVE).orElseThrow(()-> new SysBoticaEntityNotFoundException("not found mark"));
		return MarkDTO.builder().id(bean.getId()).name(bean.getName()).build();
	}

}
