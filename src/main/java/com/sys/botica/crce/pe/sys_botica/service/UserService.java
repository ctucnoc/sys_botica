package com.sys.botica.crce.pe.sys_botica.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.sys.botica.crce.pe.sys_botica.dto.HrefEntityDTO;
import com.sys.botica.crce.pe.sys_botica.dto.UserDTO;
import com.sys.botica.crce.pe.sys_botica.dto.UserLoginDTO;
import com.sys.botica.crce.pe.sys_botica.dto.request.ChangePasswordDTORequest;
import com.sys.botica.crce.pe.sys_botica.dto.request.UserDTORequest;

public interface UserService {
	public HrefEntityDTO save(UserDTORequest user);
	public HrefEntityDTO update(UserDTORequest user,Long id);
	public HrefEntityDTO delete(Long id);
	public UserDTO findById(Long id);
	public Page<UserDTO> findByFullNameAndUsername(String key_word, Pageable pageable);
	public List<UserDTO> findAutoCompleteFullName(String key_word);
	public UserLoginDTO findByUsername(String username);
	public void changePassword(ChangePasswordDTORequest bean);
}
