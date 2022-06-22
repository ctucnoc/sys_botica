package com.sys.botica.crce.pe.sys_botica.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{

	@Query(value = SysBoticaConstant.SP_SEARCH_CUSTOMER_KEY_WORD,countQuery = SysBoticaConstant.SP_SEARCH_CUSTOMER_KEY_WORD_COUNT,nativeQuery = true)
	public Page<Customer> findByWordKey(String key_word, String state,Pageable pageable);
	
	public Optional<Customer> findByIdAndState(Long id, String state);
	
	public Optional<Customer> findByNumberdocumentAndState(String nro_document,String state);
	
	public Boolean existsByNumberdocumentAndState(String numberdocument,String state);
	
	@Query(value = SysBoticaConstant.SP_SEARCH_CUSTOMER_KEY_WORD,nativeQuery = true)
	public List<Customer> findByWordKeySQL(String key_word, String state);
}
