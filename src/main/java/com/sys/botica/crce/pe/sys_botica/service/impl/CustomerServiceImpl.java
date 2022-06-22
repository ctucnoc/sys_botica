package com.sys.botica.crce.pe.sys_botica.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.dto.CustomerDTO;
import com.sys.botica.crce.pe.sys_botica.dto.HrefEntityDTO;
import com.sys.botica.crce.pe.sys_botica.dto.request.CustomerDTORequest;
import com.sys.botica.crce.pe.sys_botica.errorhandler.SysBoticaEntityNotFoundException;
import com.sys.botica.crce.pe.sys_botica.errorhandler.SysBoticaEntityUnprocessableException;
import com.sys.botica.crce.pe.sys_botica.model.Customer;
import com.sys.botica.crce.pe.sys_botica.model.TypeDocument;
import com.sys.botica.crce.pe.sys_botica.repository.CustomerRepository;
import com.sys.botica.crce.pe.sys_botica.repository.TypeDocumentRepository;
import com.sys.botica.crce.pe.sys_botica.service.CustomerService;
import com.sys.botica.crce.pe.sys_botica.util.SysBoticaResource;
import com.sys.botica.crce.pe.sys_botica.util.SysBoticaUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService{

	final
	CustomerRepository customerRepository;
	
	final 
	TypeDocumentRepository typeDocumentRepository;

	public CustomerServiceImpl(CustomerRepository customerRepository, TypeDocumentRepository typeDocumentRepository) {
		this.customerRepository = customerRepository;
		this.typeDocumentRepository = typeDocumentRepository;
	}

	@Override
	public Page<CustomerDTO> findByWordKey(String word_key, Pageable pageable) {
		return this.customerRepository.findByWordKey(word_key, SysBoticaConstant.STATE_ACTIVE, pageable)
				.map(bean -> toCustomerDto(bean));
	}

	@Override
	public CustomerDTO findById(Long id) {
		Customer customer = this.customerRepository.findByIdAndState(id, SysBoticaConstant.STATE_ACTIVE).orElseThrow(()-> new SysBoticaEntityNotFoundException("not found customer"));
		return toCustomerDto(customer);
	}

	@Override
	public HrefEntityDTO save(CustomerDTORequest dto) {
		if(this.customerRepository.existsByNumberdocumentAndState(dto.getNumberDocument(), SysBoticaConstant.STATE_ACTIVE)) {
			throw new SysBoticaEntityUnprocessableException(String.format("document Number %s", dto.getNumberDocument()));
		}
		TypeDocument typeDocument = this.typeDocumentRepository.findByIdAndState(dto.getIdTypeDocument(), SysBoticaConstant.STATE_ACTIVE).orElseThrow(()-> new SysBoticaEntityNotFoundException("not found type document"));
		Customer customer = new Customer();
		customer.setBussinesname(dto.getBussinesName());
		customer.setDirection(dto.getDirecction());
		customer.setEmail(dto.getEmail());
		customer.setFirstname(dto.getFirstName());
		customer.setLastname(dto.getLastName());
		customer.setTypeDocument(typeDocument);
		customer.setState(SysBoticaConstant.STATE_ACTIVE);
		customer.setNumberdocument(dto.getNumberDocument());
		this.customerRepository.save(customer);
		log.info("save customer -> {} " + customer);
		return SysBoticaUtil.createHrefFromResource(customer.getId(), SysBoticaResource.CUSTOMER);
	}

	@Override
	public HrefEntityDTO update(CustomerDTORequest dto, Long id) {
		TypeDocument typeDocument = this.typeDocumentRepository.findByIdAndState(dto.getIdTypeDocument(), SysBoticaConstant.STATE_ACTIVE).orElseThrow(()-> new SysBoticaEntityNotFoundException("not found type document"));
		Customer customer = this.customerRepository.findByIdAndState(id, SysBoticaConstant.STATE_ACTIVE).orElseThrow(()-> new SysBoticaEntityNotFoundException("not found customer"));
		customer.setBussinesname(dto.getBussinesName());
		customer.setDirection(dto.getDirecction());
		customer.setEmail(dto.getEmail());
		customer.setFirstname(dto.getFirstName());
		customer.setLastname(dto.getLastName());
		customer.setTypeDocument(typeDocument);
		this.customerRepository.save(customer);
		log.info("update customer -> {} " + customer);
		return SysBoticaUtil.createHrefFromResource(customer.getId(), SysBoticaResource.CUSTOMER);
	}

	@Override
	public HrefEntityDTO delete(Long id) {
		Customer customer = this.customerRepository.findByIdAndState(id, SysBoticaConstant.STATE_ACTIVE).orElseThrow(()-> new SysBoticaEntityNotFoundException("not found customer"));
		customer.setState(SysBoticaConstant.STATE_INACTIVE);
		this.customerRepository.save(customer);
		return SysBoticaUtil.createHrefFromResource(customer.getId(), SysBoticaResource.CUSTOMER);
	}
	
	private CustomerDTO toCustomerDto(Customer customer) {
		return CustomerDTO.builder()
				.id(customer.getId())
				.numberDocument(customer.getNumberdocument())
				.firstName(customer.getFirstname())
				.lastName(customer.getLastname())
				.bussinesName(customer.getBussinesname())
				.email(customer.getEmail())
				.direcction(customer.getDirection())
				.idTypeDocument(customer.getTypeDocument().getId())
				.build();
	}

	@Override
	public CustomerDTO findByNroDocument(String nro_document) {
		Customer customer = this.customerRepository.findByNumberdocumentAndState(nro_document, SysBoticaConstant.STATE_ACTIVE).orElseThrow(()-> new SysBoticaEntityNotFoundException("not found customer"));
		return this.toCustomerDto(customer);
	}

	@Override
	public List<CustomerDTO> findByWordKeySQL(String word_key) {
		return this.customerRepository.findByWordKeySQL(word_key, SysBoticaConstant.STATE_ACTIVE)
			.stream()
			.limit(15)
			.map(bean -> toCustomerDto(bean))
			.collect(Collectors.toList());
	}	
	
}
