package com.sys.botica.crce.pe.sys_botica.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.dto.ProofPaymentDTO;
import com.sys.botica.crce.pe.sys_botica.model.ProofPayment;
import com.sys.botica.crce.pe.sys_botica.repository.ProofPaymentRespository;
import com.sys.botica.crce.pe.sys_botica.service.ProofPaymentService;

@Service
public class ProofPaymentServiceImpl implements ProofPaymentService{

	final
	ProofPaymentRespository proofPaymentRespository;

	public ProofPaymentServiceImpl(ProofPaymentRespository proofPaymentRespository) {
		this.proofPaymentRespository = proofPaymentRespository;
	}

	@Override
	public List<ProofPaymentDTO> findAll() {
		return this.proofPaymentRespository.findByState(SysBoticaConstant.STATE_ACTIVE).stream()
				.map(bean -> toProofPaymentDto(bean))
				.collect(Collectors.toList());
	}
	
	private ProofPaymentDTO toProofPaymentDto(ProofPayment payment) {
		return ProofPaymentDTO.builder()
				.id(payment.getId())
				.description(payment.getDescription())
				.code(payment.getCod())
				.build();
	}
	
	
}
