package com.sys.botica.crce.pe.sys_botica.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sys.botica.crce.pe.sys_botica.model.Wharehouse;

@Repository
public interface WharehouseRepository extends JpaRepository<Wharehouse, Long>{

	public Optional<Wharehouse> findByIdAndState(Long id, String state);
	
	public Page<Wharehouse> findByNameLikeIgnoreCaseAndState(String name, String state, Pageable pageable);
	
	public Boolean existsByNameAndState(String name, String state);
	
	public List<Wharehouse> findByTypewharehouseAndState(String type,String state);
	
	public Boolean existsByIdAndState(Long id, String state);
}
