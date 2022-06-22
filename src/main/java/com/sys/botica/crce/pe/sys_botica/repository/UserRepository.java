package com.sys.botica.crce.pe.sys_botica.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	public Boolean existsUserByUsername(String username);
	//public Page<User> findByFullNameIsContaining(String key_word,Pageable pageable);
	public Page<User> findByFullNameLikeIgnoreCase(String key_word,Pageable pageable);
	
	public List<User> findByFullNameContainingIgnoreCase(String fullname);
	public Optional<User> findByIdAndState(Long id, String state);
	
	@Query(value = SysBoticaConstant.SP_SEARCH_USER_KEY_WORD,countQuery = SysBoticaConstant.SP_SEARCH_USER_KEY_WORD_COUNT,nativeQuery = true)
	public Page<User> findByFullNameLikeIgnoreCaseSql(String key_word,Pageable pageable);
	
	public Optional<User> findByUsernameAndState(String username,String state);
	
	public Optional<User> findByUsername(String username);

}
