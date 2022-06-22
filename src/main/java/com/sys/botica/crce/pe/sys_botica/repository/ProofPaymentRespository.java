package com.sys.botica.crce.pe.sys_botica.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sys.botica.crce.pe.sys_botica.model.ProofPayment;

@Repository
public interface ProofPaymentRespository extends JpaRepository<ProofPayment, Long>{
	public List<ProofPayment> findByState(String state);
	public Optional<ProofPayment> findByIdAndState(Long id, String state);
}
