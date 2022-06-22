package com.sys.botica.crce.pe.sys_botica.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.dto.TypeDocumentDTO;
import com.sys.botica.crce.pe.sys_botica.model.TypeDocument;
import com.sys.botica.crce.pe.sys_botica.repository.TypeDocumentRepository;
import com.sys.botica.crce.pe.sys_botica.service.TypeDocumentService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TypeDocumentServiceImpl implements TypeDocumentService{

	final
	TypeDocumentRepository documentRepository;

	public TypeDocumentServiceImpl(TypeDocumentRepository documentRepository) {
		this.documentRepository = documentRepository;
	}

	@Override
	public List<TypeDocumentDTO> findAll() {
		log.info("crce findAll -> {}");
		return this.documentRepository.findByState(SysBoticaConstant.STATE_ACTIVE).stream()
				.map(bean -> toTypeDocumentDto(bean)).collect(Collectors.toList());
	}
	
	private TypeDocumentDTO toTypeDocumentDto(TypeDocument typeDocument) {
		return TypeDocumentDTO.builder()
				.id(typeDocument.getId())
				.description(typeDocument.getDescription())
				.build();
	}
	
	
}
