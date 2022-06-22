package com.sys.botica.crce.pe.sys_botica.service;

import java.io.IOException;
import java.io.OutputStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.sys.botica.crce.pe.sys_botica.dto.SaleDTO;
import com.sys.botica.crce.pe.sys_botica.dto.request.SaleDTORequest;
import com.sys.botica.crce.pe.sys_botica.param.SaleParam;

import net.sf.jasperreports.engine.JRException;

public interface SaleService {
	public void save(SaleDTORequest dto, String bearerToken,OutputStream outputStream) throws IOException, JRException;
	public Page<SaleDTO> findByParameter(SaleParam params,Pageable pageable);
}
