package com.sys.botica.crce.pe.sys_botica.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sys.botica.crce.pe.sys_botica.model.Subsidiary;

@Repository
public interface SubsidiaryRepository extends JpaRepository<Subsidiary, Long>{
	public Optional<Subsidiary> findByIdAndState(Long id, String state);
	public Page<Subsidiary> findByNameContainingIgnoreCaseAndState(String name,String state,Pageable pageable);
	public List<Subsidiary> findByState(String state);
	public List<Subsidiary> findByNameContainingIgnoreCaseAndState(String name,String state);
}
