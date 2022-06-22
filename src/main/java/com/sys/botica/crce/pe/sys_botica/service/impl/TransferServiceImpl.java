package com.sys.botica.crce.pe.sys_botica.service.impl;

import java.util.Date;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.dto.HrefEntityDTO;
import com.sys.botica.crce.pe.sys_botica.dto.request.TransferDTORequest;
import com.sys.botica.crce.pe.sys_botica.errorhandler.SysBoticaEntityNotFoundException;
import com.sys.botica.crce.pe.sys_botica.model.Transfer;
import com.sys.botica.crce.pe.sys_botica.model.Wharehouse;
import com.sys.botica.crce.pe.sys_botica.repository.TransferRepository;
import com.sys.botica.crce.pe.sys_botica.repository.WharehouseRepository;
import com.sys.botica.crce.pe.sys_botica.security.JwtProvider;
import com.sys.botica.crce.pe.sys_botica.service.DtTransferService;
import com.sys.botica.crce.pe.sys_botica.service.TransferService;
import com.sys.botica.crce.pe.sys_botica.util.SysBoticaUtil;

@Service
public class TransferServiceImpl implements TransferService{

	final
	TransferRepository transferRepository;
	
	final
	SysBoticaUtil sysBoticaUtil;
	
	final
	WharehouseRepository wharehouseRepository;
	
	final 
	DtTransferService dtTransferService;
	
	final
	JwtProvider jwtProvider;

	public TransferServiceImpl(TransferRepository transferRepository,SysBoticaUtil sysBoticaUtil,WharehouseRepository wharehouseRepository,DtTransferService dtTransferService,JwtProvider jwtProvider) {
		this.transferRepository = transferRepository;
		this.sysBoticaUtil = sysBoticaUtil;
		this.wharehouseRepository = wharehouseRepository;
		this.dtTransferService = dtTransferService;
		this.jwtProvider = jwtProvider;
	}

	@Override
	public HrefEntityDTO save(TransferDTORequest transferDTORequest, String bearerToken) {
		Wharehouse wharehouse = this.wharehouseRepository.findByIdAndState(transferDTORequest.getIdwharehousedestination(), SysBoticaConstant.STATE_ACTIVE).orElseThrow(()-> new SysBoticaEntityNotFoundException("not found wharehouse"));
		String[] userAndSubsidiary= StringUtils.split(this.jwtProvider.getUsername(this.sysBoticaUtil.resolveToken(bearerToken)),String.valueOf(Character.LINE_SEPARATOR));
		String user = userAndSubsidiary[0];
		Transfer transfer = new Transfer();
		transfer.setWharehouse(wharehouse);
		transfer.setIdsubsidiary(transferDTORequest.getIdsubsidiary());
		transfer.setIdwharehouseorigin(transferDTORequest.getIdwharehouseorigin());
		transfer.setState(SysBoticaConstant.STATE_ACTIVE);
		transfer.setDatecreated(new Date());
		transfer.setUsercreated(user);
		this.transferRepository.save(transfer);
		return this.dtTransferService.saveAll(transferDTORequest.getDetail(), transfer, user);
	}
	
}
