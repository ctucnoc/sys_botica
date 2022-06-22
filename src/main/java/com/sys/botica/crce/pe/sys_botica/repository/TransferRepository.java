package com.sys.botica.crce.pe.sys_botica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sys.botica.crce.pe.sys_botica.model.Transfer;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long>{

}
