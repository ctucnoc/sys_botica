package com.sys.botica.crce.pe.sys_botica.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.model.Authority;

@Repository
public interface AuthorityRespository extends JpaRepository<Authority, Long> {

	public Page<Authority> findByNameLikeIgnoreCaseAndState(String key_word, String state, Pageable pageable);

	public Optional<Authority> findByIdAndState(Long id, String state);

	public Boolean existsByNameAndState(String name, String state);

	public List<Authority> findByState(String state);
	
	@Query(value = SysBoticaConstant.SP_SEARCH_AUTHORITY_MINUS, countQuery = SysBoticaConstant.SP_SEARCH_AUTHORITY_MINUS_COUNT,nativeQuery = true)
	public Page<Authority> findByMinusAuthority(Integer id,String state,Pageable pageable);
}
