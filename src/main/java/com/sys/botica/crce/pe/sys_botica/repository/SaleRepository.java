package com.sys.botica.crce.pe.sys_botica.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.model.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long>{

	@Query(value = SysBoticaConstant.SP_SEARCH_PRODUCT_SALE, countQuery = SysBoticaConstant.SP_SEARCH_PRODUCT_SALE_COUNT, nativeQuery = true)
	Page<Sale> findByAllParameter(Integer idcustomer,Integer idproofpayment,String dataFrom,String dataTo,String state, Pageable pageable);
	
	public Optional<Sale> findByIdAndState(Long id, String state);
}
