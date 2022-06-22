package com.sys.botica.crce.pe.sys_botica.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.sys.botica.crce.pe.sys_botica.model.Mark;

public interface MarkRepository extends JpaRepository<Mark, Integer>{
	
	public Page<Mark> findMarkByState(String state,Pageable pageable);
	
	@Query("select m from Mark m where m.name like CONCAT('%',UPPER(?1),'%') and m.state=?2")
	public Page<Mark> findByDescriptionAndState(String key_word,String state,Pageable pageable);
	
	public Optional<Mark> findMarkByIdAndState(Integer id, String state);

}
