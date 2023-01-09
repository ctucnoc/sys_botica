package com.sys.botica.crce.pe.sys_botica.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.dao.GenericDAO;
import com.sys.botica.crce.pe.sys_botica.errorhandler.SysBoticaEntityNotFoundException;
import com.sys.botica.crce.pe.sys_botica.model.Sale;
import com.sys.botica.crce.pe.sys_botica.report.ReportModel;
import com.sys.botica.crce.pe.sys_botica.repository.SaleRepository;
import com.sys.botica.crce.pe.sys_botica.repository.WharehouseRepository;
import com.sys.botica.crce.pe.sys_botica.security.JwtProvider;
import com.sys.botica.crce.pe.sys_botica.service.ReportService;
import com.sys.botica.crce.pe.sys_botica.util.SysBoticaUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

@Service
public class ReportServiceImpl implements ReportService{

	final
	ReportModel reportModel;
	
	final
	SaleRepository saleRepository;
	
	final
	GenericDAO genericDAO;
	
	final
	JwtProvider jwtProvider;
	
	final
	SysBoticaUtil sysBoticaUtil;
	
	final
	WharehouseRepository wharehouseRepository;

	public ReportServiceImpl(ReportModel reportModel,SaleRepository saleRepository,GenericDAO genericDAO,JwtProvider jwtProvider,SysBoticaUtil sysBoticaUtil, WharehouseRepository wharehouseRepository) {
		this.reportModel = reportModel;
		this.saleRepository = saleRepository;
		this.genericDAO = genericDAO;
		this.jwtProvider = jwtProvider;
		this.sysBoticaUtil = sysBoticaUtil;
		this.wharehouseRepository = wharehouseRepository;
	}

	@Override
	public void mkReportVoucher(String name,Long idsale,OutputStream outputStream)throws JRException,IOException {
		Sale sale = this.saleRepository.findByIdAndState(idsale, SysBoticaConstant.STATE_ACTIVE).orElseThrow(()-> new SysBoticaEntityNotFoundException("not found sale"));
		Float totalPrise = this.genericDAO.totalSale(SysBoticaUtil.toIntExact(idsale)).get();
		Map<String, Object> parametro = new HashMap<>();
		String nro_ticket = SysBoticaUtil.toDigitExtract(sale.getProofPayment().getDescription(),2)+""+SysBoticaUtil.zfill(String.valueOf(sale.getProofPayment().getId()), 2) +"-"+SysBoticaUtil.zfill(String.valueOf(sale.getId()), 8);
		parametro.put("p_nro_ticket", nro_ticket);
		parametro.put("p_name_drugstore", SysBoticaConstant.TICKET_CRCE_NAME_DRUGSTORE);
		parametro.put("p_district", SysBoticaConstant.TICKET_CRCE_DISTRICT_DRUGSTORE);
		parametro.put("p_address", SysBoticaConstant.TICKET_CRCE_ADDRESS_DRUGSTORE);
		parametro.put("p_nro_documento", SysBoticaConstant.VC_NRO_DOCUMENTO);
		parametro.put("p_nro_cel", SysBoticaConstant.VC_NR_TELEPHONE);
		parametro.put("p_forma_pago", SysBoticaConstant.VC_TYPE_SALE);
		parametro.put("p_idsale", idsale);
		parametro.put("p_vuelto", SysBoticaConstant.NR_VUELTO_DEFAULT);
		parametro.put("p_total_letras", SysBoticaUtil.saleTotal(totalPrise));
		JasperPrint jasperPrint=this.reportModel.mkReport(name,parametro);
		JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
	}

	@Override
	public void mkReportSaleDate(String dateFrom, String dateTo, String token, OutputStream outputStream) throws JRException, IOException {
		Map<String, Object> parametro = new HashMap<>();
		String[] userAndSubsidiary= StringUtils.split(this.jwtProvider.getUsername(this.sysBoticaUtil.resolveToken(token)),String.valueOf(Character.LINE_SEPARATOR));
		parametro.put("p_user", userAndSubsidiary[0]);
		parametro.put("p_date_created", new Date().toString());
		parametro.put("p_dateFrom", dateFrom);
		parametro.put("p_dateTo", dateTo);
		parametro.put("p_img", "classpath:img" + File.separator + "logo.png");
		JasperPrint jasperPrint=this.reportModel.mkReport(SysBoticaConstant.RESOURCE_REPORT_SALE_DATE,parametro);
		JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
	}

	@Override
	public byte[] mkReporteTicket(String name, Long idsale) throws JRException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void mkReportWharehouseproduct(Long idwharehouse, String token, OutputStream outputStream) throws JRException, IOException{
		if(!this.wharehouseRepository.existsByIdAndState(idwharehouse, SysBoticaConstant.STATE_ACTIVE))
			throw new SysBoticaEntityNotFoundException("the warehouse does not exist");
		Map<String, Object> parametro = new HashMap<>();
		String[] userAndSubsidiary= StringUtils.split(this.jwtProvider.getUsername(this.sysBoticaUtil.resolveToken(token)),String.valueOf(Character.LINE_SEPARATOR));
		parametro.put("p_idwharehouse", idwharehouse);
		parametro.put("p_user", userAndSubsidiary[0]);
		parametro.put("p_date", new Date().toString());
		parametro.put("p_img", "classpath:img" + File.separator + "logo.png");
		JasperPrint jasperPrint=this.reportModel.mkReport(SysBoticaConstant.RESOURCE_REPORT_TOTAL_PRODUCT_WHAREHOUSE,parametro);
		JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
	}

}
