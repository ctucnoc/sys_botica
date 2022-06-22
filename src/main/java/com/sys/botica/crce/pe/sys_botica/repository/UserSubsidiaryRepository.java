package com.sys.botica.crce.pe.sys_botica.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sys.botica.crce.pe.sys_botica.model.Subsidiary;
import com.sys.botica.crce.pe.sys_botica.model.User;
import com.sys.botica.crce.pe.sys_botica.model.UserSubsidiary;

@Repository
public interface UserSubsidiaryRepository extends JpaRepository<UserSubsidiary, Long>{

	@Query("select us from UserSubsidiary us where us.user.id=?1 and us.state=?2")
	public Page<UserSubsidiary> findByUserAllSubsidiary(Long id,String state,Pageable pageable);
	
	public Boolean existsByUserAndSubsidiaryAndState(User user, Subsidiary subsidiary, String state);
	
	public Optional<UserSubsidiary> findByIdAndState(Long id, String state);
	
	public List<UserSubsidiary> findByUserAndState(User user,String state);
}
