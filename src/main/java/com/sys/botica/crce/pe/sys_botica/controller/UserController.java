package com.sys.botica.crce.pe.sys_botica.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.dto.request.ChangePasswordDTORequest;
import com.sys.botica.crce.pe.sys_botica.dto.request.UserDTORequest;
import com.sys.botica.crce.pe.sys_botica.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(SysBoticaConstant.API_VERSION + SysBoticaConstant.RESOURCE_USERS)
@CrossOrigin(origins = SysBoticaConstant.PATH_FROTEND_SYSCE)
public class UserController {

	final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping(SysBoticaConstant.RESOURCE_USERS_USER)
	public ResponseEntity<?> save(@RequestBody UserDTORequest user) {
		return new ResponseEntity<>(this.userService.save(user), HttpStatus.OK);
	}

	@PutMapping(SysBoticaConstant.RESOURCE_USERS_USER + SysBoticaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<?> update(@RequestBody UserDTORequest user, @PathVariable Long id) {
		return new ResponseEntity<>(this.userService.update(user, id), HttpStatus.OK);
	}

	@GetMapping(SysBoticaConstant.RESOURCE_USERS_USER + SysBoticaConstant.RESOURCE_GENERIC_ID)
	public ResponseEntity<?> findById(@PathVariable Long id) {
		return new ResponseEntity<>(this.userService.findById(id), HttpStatus.OK);
	}

	@GetMapping(SysBoticaConstant.RESOURCE_USERS_USER)
	public ResponseEntity<?> findByFullNameAndUsername(@RequestParam String key_word, Pageable pageable) {
		return new ResponseEntity<>(this.userService.findByFullNameAndUsername(key_word, pageable), HttpStatus.OK);
	}

	@GetMapping(SysBoticaConstant.RESOURCE_USERS_USER + SysBoticaConstant.RESOURCE_GENERIC_AUTO_COMPLETE)
	public ResponseEntity<?> findByAutoCompleteFullName(@RequestParam String key_word) {
		return new ResponseEntity<>(this.userService.findAutoCompleteFullName(key_word), HttpStatus.OK);
	}
	
	@PostMapping(SysBoticaConstant.RESOURCE_USERS_USER + SysBoticaConstant.RESOURCE_GENERIC_CHANGE_PASSWORD)
	public ResponseEntity<HttpStatus> changePassword(@Valid @RequestBody ChangePasswordDTORequest dto){
		log.info("crce change Password -> {} "+dto);
		this.userService.changePassword(dto);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
