package com.sys.botica.crce.pe.sys_botica.service;

import java.util.List;
import com.sys.botica.crce.pe.sys_botica.dto.DtSaleDTO;
import com.sys.botica.crce.pe.sys_botica.dto.HrefEntityDTO;
import com.sys.botica.crce.pe.sys_botica.dto.request.DtSaleDTORequest;
import com.sys.botica.crce.pe.sys_botica.model.Sale;

public interface DtSaleService {
	public HrefEntityDTO saveAll(List<DtSaleDTORequest> details, Sale sale, String user);
	public List<DtSaleDTO> findBySale(Long idSale);
}
