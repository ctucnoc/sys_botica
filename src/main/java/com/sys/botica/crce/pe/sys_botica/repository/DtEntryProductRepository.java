package com.sys.botica.crce.pe.sys_botica.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.sys.botica.crce.pe.sys_botica.model.DtEntryProduct;
import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;

@Repository
public interface DtEntryProductRepository extends JpaRepository<DtEntryProduct, Long>{

	@Query(value = SysBoticaConstant.SP_SEARCH_WORD_KEY_DT_ENTRY_PRODUCT,countQuery = SysBoticaConstant.SP_SEARCH_WORD_KEY_DT_ENTRY_PRODUCT_COUNT,nativeQuery = true)
	public Page<DtEntryProduct> findByDtEntryProductSQL(Integer idwharehouse,String word_key,String state,Pageable pageable);
	
	public Optional<DtEntryProduct> findByIdAndState(Long id, String state);
}
