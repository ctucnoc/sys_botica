package com.sys.botica.crce.pe.sys_botica.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sys.botica.crce.pe.sys_botica.dto.CustomerDTO;
import com.sys.botica.crce.pe.sys_botica.dto.HrefEntityDTO;
import com.sys.botica.crce.pe.sys_botica.dto.request.CustomerDTORequest;

public interface CustomerService {

	public Page<CustomerDTO> findByWordKey(String word_key, Pageable pageable);
	
	public CustomerDTO findById(Long id);
	
	public HrefEntityDTO save(CustomerDTORequest dto);
	
	public HrefEntityDTO update(CustomerDTORequest dto,Long id);
	
	public HrefEntityDTO delete(Long id);
	
	public CustomerDTO findByNroDocument(String nro_document);
	
	public List<CustomerDTO> findByWordKeySQL(String word_key);
}
