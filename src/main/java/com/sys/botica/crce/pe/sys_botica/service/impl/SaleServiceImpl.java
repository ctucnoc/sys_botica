package com.sys.botica.crce.pe.sys_botica.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.dto.SaleDTO;
import com.sys.botica.crce.pe.sys_botica.dto.request.SaleDTORequest;
import com.sys.botica.crce.pe.sys_botica.errorhandler.SysBoticaEntityNotFoundException;
import com.sys.botica.crce.pe.sys_botica.model.Customer;
import com.sys.botica.crce.pe.sys_botica.model.ProofPayment;
import com.sys.botica.crce.pe.sys_botica.model.Sale;
import com.sys.botica.crce.pe.sys_botica.param.SaleParam;
import com.sys.botica.crce.pe.sys_botica.repository.CustomerRepository;
import com.sys.botica.crce.pe.sys_botica.repository.ProofPaymentRespository;
import com.sys.botica.crce.pe.sys_botica.repository.SaleRepository;
import com.sys.botica.crce.pe.sys_botica.security.JwtProvider;
import com.sys.botica.crce.pe.sys_botica.service.DtSaleService;
import com.sys.botica.crce.pe.sys_botica.service.ReportService;
import com.sys.botica.crce.pe.sys_botica.service.SaleService;
import com.sys.botica.crce.pe.sys_botica.util.SysBoticaUtil;
import net.sf.jasperreports.engine.JRException;

@Service
public class SaleServiceImpl implements SaleService{

	final 
	SaleRepository saleRepository;
	
	final
	CustomerRepository customerRepository;
	
	final
	ProofPaymentRespository proofPaymentRespository;
	
	final
	JwtProvider jwtProvider;
	
	final
	SysBoticaUtil sysBoticaUtil;
	
	final 
	DtSaleService dtSaleService;
	
	final
	ReportService reportService;
	
	public SaleServiceImpl(SaleRepository saleRepository,CustomerRepository customerRepository,ProofPaymentRespository proofPaymentRespository,JwtProvider jwtProvider,
			SysBoticaUtil sysBoticaUtil,DtSaleService dtSaleService,ReportService reportService) {
		this.saleRepository = saleRepository;
		this.customerRepository = customerRepository;
		this.proofPaymentRespository = proofPaymentRespository;
		this.jwtProvider = jwtProvider;
		this.sysBoticaUtil = sysBoticaUtil;
		this.dtSaleService = dtSaleService;
		this.reportService = reportService;
	}

	@Override
	public void save(SaleDTORequest dto, String bearerToken,OutputStream outputStream) throws IOException, JRException{
		Customer customer = this.customerRepository.findByIdAndState(dto.getIdCustomer(), SysBoticaConstant.STATE_ACTIVE).orElseThrow(()->new SysBoticaEntityNotFoundException("not found customer"));
		ProofPayment proofPayment = this.proofPaymentRespository.findByIdAndState(dto.getIdProofpayment(), SysBoticaConstant.STATE_ACTIVE).orElseThrow(()-> new SysBoticaEntityNotFoundException("not found proof payment"));
		String[] userAndSubsidiary= StringUtils.split(this.jwtProvider.getUsername(this.sysBoticaUtil.resolveToken(bearerToken)),String.valueOf(Character.LINE_SEPARATOR));
		String user = userAndSubsidiary[0];
		Sale sale = new Sale();
		sale.setCustomer(customer);
		sale.setProofPayment(proofPayment);
		sale.setState(SysBoticaConstant.STATE_ACTIVE);
		sale.setUsercreated(user);
		sale.setDatecreated(new Date());
		this.saleRepository.save(sale);
		this.dtSaleService.saveAll(dto.getDetails(), sale, user);
		this.reportService.mkReportVoucher(SysBoticaConstant.RESOURCE_VAUCHER_TICKET, sale.getId(), outputStream);
	}

	@Override
	public Page<SaleDTO> findByParameter(SaleParam params, Pageable pageable) {
		return this.saleRepository.findByAllParameter(params.getIdcustomer() == null?0:params.getIdcustomer(),params.getIdproofpayment() == null?0:params.getIdproofpayment(), params.getDateFrom(), params.getDateTo(), SysBoticaConstant.STATE_ACTIVE, pageable)
			.map((bean) -> this.toSaleDto(bean));
	}	
	
	private SaleDTO toSaleDto(Sale sale) {
		return SaleDTO.builder()
				.id(sale.getId())
				.idCustomer(sale.getCustomer().getId())
				.customer(sale.getCustomer().getBussinesname() == ""?sale.getCustomer().getBussinesname():sale.getCustomer().getFirstname() + " " +sale.getCustomer().getLastname())
				.idProofpayment(sale.getProofPayment().getId())
				.proofpayment(sale.getProofPayment().getDescription())
				.datecreated(sale.getDatecreated())
				.build();
	}
	
}
