package com.sys.botica.crce.pe.sys_botica.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.model.Provider;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Long>{
	Page<Provider> findByBussinesnameLikeIgnoreCaseAndState(String name,String state,Pageable pageable);
	
	Optional<Provider> findByIdAndState(Long id, String state);
	
	@Query(value = SysBoticaConstant.SP_SEARCH_PROVIDER_KEY_WORD,countQuery = SysBoticaConstant.SP_SEARCH_PROVIDER_KEY_WORD_COUNT, nativeQuery = true)
	Page<Provider> findByBussinesnameLikeSql(String name,String state,Pageable pageable);
	
	@Query(value = SysBoticaConstant.SP_SEARCH_PROVIDER_KEY_WORD,nativeQuery = true)
	public List<Provider> findByKeyWordSQL(String name,String state);
	
	public Page<Provider> findByState(String state,Pageable pageable);
}
