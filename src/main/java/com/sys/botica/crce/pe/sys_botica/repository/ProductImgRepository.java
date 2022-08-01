package com.sys.botica.crce.pe.sys_botica.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sys.botica.crce.pe.sys_botica.model.Product;
import com.sys.botica.crce.pe.sys_botica.model.ProductImg;

@Repository
public interface ProductImgRepository extends JpaRepository<ProductImg, Long> {

	List<ProductImg> findByProductAndState(Product product, String state);
}
