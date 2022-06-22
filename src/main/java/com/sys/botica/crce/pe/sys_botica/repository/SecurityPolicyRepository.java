package com.sys.botica.crce.pe.sys_botica.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sys.botica.crce.pe.sys_botica.model.SegurityPolicy;

@Repository
public interface SecurityPolicyRepository extends JpaRepository<SegurityPolicy, Long>{
	public Optional<SegurityPolicy> findByCodesecuritypolicy(String code);
}
