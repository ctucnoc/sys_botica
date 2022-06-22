package com.sys.botica.crce.pe.sys_botica.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sys.botica.crce.pe.sys_botica.model.Subsidiary;
import com.sys.botica.crce.pe.sys_botica.model.Wharehouse;
import com.sys.botica.crce.pe.sys_botica.model.WharehouseSubsidiary;

@Repository
public interface WharehouseSubsidiaryRepository extends JpaRepository<WharehouseSubsidiary, Long>{

	public List<WharehouseSubsidiary> findByWharehouseAndState(Wharehouse wharehouse,String state);
	public List<WharehouseSubsidiary> findBySubsidiaryAndState(Subsidiary subsidiary,String state);
	public Page<WharehouseSubsidiary> findBySubsidiaryAndState(Subsidiary subsidiary,String state,Pageable pageable);
	public Boolean existsByWharehouseAndSubsidiaryAndState(Wharehouse wharehouse,Subsidiary subsidiary,String state);
	public Optional<WharehouseSubsidiary> findByIdAndState(Long id, String state);
}
