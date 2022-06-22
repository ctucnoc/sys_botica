package com.sys.botica.crce.pe.sys_botica.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sys.botica.crce.pe.sys_botica.model.DtSale;
import com.sys.botica.crce.pe.sys_botica.model.Sale;

@Repository
public interface DtSaleRepository extends JpaRepository<DtSale, Long>{
	Optional<DtSale> findByIdAndState(Long id, String state);
	public List<DtSale> findBySaleAndState(Sale sale, String state);
}
