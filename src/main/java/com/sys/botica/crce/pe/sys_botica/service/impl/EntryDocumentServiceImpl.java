package com.sys.botica.crce.pe.sys_botica.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.dto.EntryDocumentDTO;
import com.sys.botica.crce.pe.sys_botica.model.EntryDocument;
import com.sys.botica.crce.pe.sys_botica.repository.EntryDocumentRepository;
import com.sys.botica.crce.pe.sys_botica.service.EntryDocumentService;

@Service
@Transactional
public class EntryDocumentServiceImpl implements EntryDocumentService{

	final EntryDocumentRepository documentRepository;

	public EntryDocumentServiceImpl(EntryDocumentRepository documentRepository) {
		this.documentRepository = documentRepository;
	}

	@Override
	public List<EntryDocumentDTO> findAll() {
		return this.documentRepository.findByState(SysBoticaConstant.STATE_ACTIVE).stream()
				.map((bean)-> toEntryDocumentDto(bean))
				.collect(Collectors.toList());
	}
	
	protected EntryDocumentDTO toEntryDocumentDto(EntryDocument entryDocument) {
		return EntryDocumentDTO.builder().id(entryDocument.getId()).name(entryDocument.getName())
				.build();
	}
	
	
}
