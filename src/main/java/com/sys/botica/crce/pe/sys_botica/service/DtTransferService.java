package com.sys.botica.crce.pe.sys_botica.service;

import java.util.List;
import com.sys.botica.crce.pe.sys_botica.dto.HrefEntityDTO;
import com.sys.botica.crce.pe.sys_botica.dto.ProductSaleDTO;
import com.sys.botica.crce.pe.sys_botica.dto.request.DtTransferDTORequest;
import com.sys.botica.crce.pe.sys_botica.model.Transfer;

public interface DtTransferService {
	public HrefEntityDTO saveAll(List<DtTransferDTORequest> details,Transfer transfer, String user);
	public List<ProductSaleDTO> findByKeyWord(Long idsubsidiary,String keyWord);
}
