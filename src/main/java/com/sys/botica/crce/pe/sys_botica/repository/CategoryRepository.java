package com.sys.botica.crce.pe.sys_botica.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.sys.botica.crce.pe.sys_botica.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{
	public List<Category> findByNameLikeAndState(String key_word,String state);
	
	@Query("select c from Category c where c.name like CONCAT('%',UPPER(?1),'%') and c.state=?2")
	public Page<Category> findByNameAndState(String key_word,String state,Pageable pageable);
	
	public Optional<Category> findCategoryByIdAndState(Integer id, String state);
	
	public List<Category> findByState(String state);
}
