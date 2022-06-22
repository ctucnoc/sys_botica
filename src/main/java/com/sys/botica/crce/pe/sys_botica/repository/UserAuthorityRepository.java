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
import com.sys.botica.crce.pe.sys_botica.model.UserAuthority;

@Repository
public interface UserAuthorityRepository extends JpaRepository<UserAuthority, Long> {
	public Page<UserAuthority> findByUserAndState(User user, String state, Pageable pageable);

	public List<UserAuthority> findByUserAndState(User user, String state);
	
	@Query(value = SysBoticaConstant.SP_EXIST_USER_AUTHORITY,nativeQuery = true)
	public Boolean existsByUserAndAuthorityAndStateSql(Integer iduser,Integer idauthority,String state);
	
	@Query(value = SysBoticaConstant.SP_SAVE_USER_AUTHORITY,nativeQuery = true)
	public UserAuthority saveSql(Integer iduser, Integer idauthority, String state);
	
	public Optional<UserAuthority> findByIdAndState(Long id, String state);
	
	public List<UserAuthority> findByUser(User user);
}
