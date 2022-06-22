package com.sys.botica.crce.pe.sys_botica.service.impl;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.dto.HrefEntityDTO;
import com.sys.botica.crce.pe.sys_botica.dto.SecurityPolicyDTO;
import com.sys.botica.crce.pe.sys_botica.dto.request.SecurityPolicyDTORequest;
import com.sys.botica.crce.pe.sys_botica.errorhandler.SysBoticaEntityNotFoundException;
import com.sys.botica.crce.pe.sys_botica.model.SegurityPolicy;
import com.sys.botica.crce.pe.sys_botica.repository.SecurityPolicyRepository;
import com.sys.botica.crce.pe.sys_botica.service.SecurityPolicyService;
import com.sys.botica.crce.pe.sys_botica.util.SysBoticaResource;
import com.sys.botica.crce.pe.sys_botica.util.SysBoticaUtil;

@Service
@Transactional
public class SecurityPolicyServiceImpl implements SecurityPolicyService {
	final SecurityPolicyRepository securityPolicyRepository;

	public SecurityPolicyServiceImpl(SecurityPolicyRepository securityPolicyRepository) {
		this.securityPolicyRepository = securityPolicyRepository;
	}

	@Override
	public SecurityPolicyDTO findByCode() {
		SegurityPolicy segurityPolicy = this.securityPolicyRepository.findByCodesecuritypolicy(SysBoticaConstant.VN_CODE_SECURITY_POLICY).orElseThrow(()-> new SysBoticaEntityNotFoundException("not found security policy"));
		return this.toSecurityPolicyDTO(segurityPolicy);
	}
	
	public SecurityPolicyDTO toSecurityPolicyDTO(SegurityPolicy secuPolicy) {
		return SecurityPolicyDTO.builder().id(secuPolicy.getId())
				.maxidletime(secuPolicy.getMaxidletime())
				.maxnumberattempts(secuPolicy.getMaxnumberattempts())
				.maxpasswordlength(secuPolicy.getMaxpasswordlength())
				.minpasswordlength(secuPolicy.getMinpasswordlength())
				.passwordchangefirstlogin(secuPolicy.getPasswordchangefirstlogin())
				.build();
	}

	@Override
	public HrefEntityDTO update(SecurityPolicyDTORequest dto, Long id) {
		SegurityPolicy segurityPolicy = this.securityPolicyRepository.findById(id).orElseThrow(()-> new SysBoticaEntityNotFoundException("nor found security policy"));
		segurityPolicy.setMaxidletime(dto.getMaxidletime());
		segurityPolicy.setMaxnumberattempts(dto.getMaxnumberattempts());
		segurityPolicy.setMaxpasswordlength(dto.getMaxpasswordlength());
		segurityPolicy.setMinpasswordlength(dto.getMinpasswordlength());
		segurityPolicy.setPasswordchangefirstlogin(dto.getPasswordchangefirstlogin());
		this.securityPolicyRepository.save(segurityPolicy);
		return SysBoticaUtil.createHrefFromResource(segurityPolicy.getId(), SysBoticaResource.SECURITYPOLICY);
	}

}
