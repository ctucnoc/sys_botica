package com.sys.botica.crce.pe.sys_botica.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.sys.botica.crce.pe.sys_botica.model.Unit;

public interface UnitRespository extends JpaRepository<Unit, Integer>{

	@Query("select u from Unit u where u.description like CONCAT('%',UPPER(?1),'%') and u.state=?2")
	public Page<Unit> findByDescriptionAndState(String key_word,String state,Pageable pageable);
	
	public Optional<Unit> findUnitByIdAndState(Integer id, String state);
	
	@Query("select count(u) > 0 from Unit u where u.initials=?1 or u.description=?2")
	public Boolean existsByInitialsAndDescription(String initials,String description);
}
