package com.sys.botica.crce.pe.sys_botica.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.dto.HrefEntityDTO;
import com.sys.botica.crce.pe.sys_botica.dto.UserDTO;
import com.sys.botica.crce.pe.sys_botica.dto.UserLoginDTO;
import com.sys.botica.crce.pe.sys_botica.dto.request.UserDTORequest;
import com.sys.botica.crce.pe.sys_botica.errorhandler.SysBoticaEntityNotFoundException;
import com.sys.botica.crce.pe.sys_botica.errorhandler.SysBoticaEntityUnprocessableException;
import com.sys.botica.crce.pe.sys_botica.errorhandler.SysBoticaGenericClientException;
import com.sys.botica.crce.pe.sys_botica.model.SegurityPolicy;
import com.sys.botica.crce.pe.sys_botica.model.User;
import com.sys.botica.crce.pe.sys_botica.repository.SecurityPolicyRepository;
import com.sys.botica.crce.pe.sys_botica.repository.UserRepository;
import com.sys.botica.crce.pe.sys_botica.service.UserService;
import com.sys.botica.crce.pe.sys_botica.util.SysBoticaResource;
import com.sys.botica.crce.pe.sys_botica.util.SysBoticaUtil;

@Service
public class UserServiceImpl implements UserService{

	final UserRepository userRepository;
	final SysBoticaUtil sysBoticaUtil;
	final SecurityPolicyRepository securityPolicyRepository;

	public UserServiceImpl(UserRepository userRepository,SysBoticaUtil sysBoticaUtil,SecurityPolicyRepository securityPolicyRepository) {
		this.userRepository = userRepository;
		this.sysBoticaUtil = sysBoticaUtil;
		this.securityPolicyRepository = securityPolicyRepository;
	}

	@Override
	public HrefEntityDTO save(UserDTORequest user) {
		if (!sysBoticaUtil.validateEmail(user.getEmail()))
			throw new SysBoticaGenericClientException("no valid email", HttpStatus.BAD_REQUEST);
		if (this.userRepository.existsUserByUsername(user.getUsername()))
			throw new SysBoticaEntityUnprocessableException("exists username");
		SegurityPolicy policy = this.securityPolicyRepository
				.findByCodesecuritypolicy(SysBoticaConstant.VN_CODE_SECURITY_POLICY)
				.orElseThrow(() -> new SysBoticaEntityNotFoundException("not found"));
		User bean = User.builder().username(user.getUsername()).password(user.getUsername())
				.fullName(user.getFullname()).state(user.getState()).email(user.getEmail())
				.numberattempts(SysBoticaConstant.QT_NUMBER_ATTEMPTS_DEFAULT)
				.passwordchangefirstlogin(
						policy.getPasswordchangefirstlogin().equals(SysBoticaConstant.VN_PASSWORDCHANGEFIRSTLOGIN)
								? SysBoticaConstant.VN_PASSWORDCHANGEFIRSTLOGIN
								: SysBoticaConstant.VN_NOT_PASSWORDCHANGEFIRSTLOGIN)
				.build();
		this.userRepository.save(bean);
		return SysBoticaUtil.createHrefFromResource(bean.getId(), SysBoticaResource.USER);
	}

	@Override
	public UserDTO findById(Long id) {
		User user = this.userRepository.findById(id).orElseThrow(()-> new SysBoticaEntityNotFoundException("not found user"));
		return UserDTO.builder().id(user.getId()).username(user.getUsername()).fullname(user.getFullName()).state(user.getState()).email(user.getEmail()).build();
	}

	@Override
	public Page<UserDTO> findByFullNameAndUsername(String key_word, Pageable pageable) {
		return this.userRepository.findByFullNameLikeIgnoreCaseSql(key_word, pageable)
				.map((user) -> UserDTO.builder().id(user.getId()).username(user.getUsername())
						.fullname(user.getFullName()).state(user.getState()).email(user.getEmail()).build());
	}

	@Override
	public HrefEntityDTO update(UserDTORequest user, Long id) {
		User bean = this.userRepository.findById(id).orElseThrow(()-> new SysBoticaEntityNotFoundException("not found user"));
		bean.setEmail(user.getEmail());
		bean.setFullName(user.getFullname());
		bean.setState(user.getState());
		this.userRepository.save(bean);
		return SysBoticaUtil.createHrefFromResource(bean.getId(), SysBoticaResource.USER);
	}

	@Override
	public HrefEntityDTO delete(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserDTO> findAutoCompleteFullName(String key_word) {
		return this.userRepository.findByFullNameContainingIgnoreCase(key_word).stream()
		.filter(bean->bean.getFullName().toUpperCase().contains(key_word.toUpperCase()))
		.limit(15)
		.map(user -> convertToDTo(user))
		.collect(Collectors.toList());
	}
	
	public UserDTO convertToDTo(User user) {
		return UserDTO.builder().id(user.getId()).username(user.getUsername()).fullname(user.getFullName())
				.state(user.getState()).email(user.getEmail()).build();
	}

	@Override
	public UserLoginDTO findByUsername(String username) {
		User bean = this.userRepository.findByUsername(username)
				.orElseThrow(() -> new SysBoticaEntityNotFoundException("not found user"));
		return UserLoginDTO.builder().email(bean.getEmail()).fullname(bean.getFullName()).password(bean.getPassword())
				.state(bean.getState() == SysBoticaConstant.STATE_ACTIVE ? true : false).username(bean.getUsername())
				.build();
	}
	
}
