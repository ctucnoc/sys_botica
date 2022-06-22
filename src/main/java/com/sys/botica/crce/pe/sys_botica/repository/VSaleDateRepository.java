package com.sys.botica.crce.pe.sys_botica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sys.botica.crce.pe.sys_botica.view.VSaleDate;

@Repository
public interface VSaleDateRepository extends JpaRepository<VSaleDate, String>{

}
