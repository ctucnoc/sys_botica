package com.sys.botica.crce.pe.sys_botica.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query("select count(p) > 0 from Product p where p.name=?1 and p.state=?2")
	public Boolean existsByProductNameAndProductStatus(String name, String state);

	@Query(value = SysBoticaConstant.SP_SEARCH_PRODUCT_KEY_WORD, countQuery = SysBoticaConstant.SP_SEARCH_PRODUCT_KEY_WORD_COUNT, nativeQuery = true)
	Page<Product> findByProductKeyWord(String key_word,String state, Pageable pageable);
	
	public Optional<Product> findProductByIdAndState(Long id,String state);
	
	@Query(value = SysBoticaConstant.SP_SEARCH_PRODUCT_KEY_WORD,nativeQuery = true)
	public List<Product> findByKeyWordSQL(String key_word, String state);

}
