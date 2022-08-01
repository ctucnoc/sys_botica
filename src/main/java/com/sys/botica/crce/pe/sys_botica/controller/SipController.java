package com.sys.botica.crce.pe.sys_botica.controller;

import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.dto.UserDetailDTO;
import com.sys.botica.crce.pe.sys_botica.dto.UserResponseDTO;
import com.sys.botica.crce.pe.sys_botica.dto.request.UserLoinDTORequest;
import com.sys.botica.crce.pe.sys_botica.security.JwtProvider;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(SysBoticaConstant.API_VERSION + SysBoticaConstant.RESOURCE_SIP)
@CrossOrigin(origins = SysBoticaConstant.PATH_FROTEND_SYSCE)
public class SipController {

    final AuthenticationManager authenticationManager;
    final JwtProvider jwtProvider;
    
	public SipController(AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
		this.authenticationManager = authenticationManager;
		this.jwtProvider = jwtProvider;
	}
	
    @PostMapping(SysBoticaConstant.RESOURCE_LOGIN)
    public ResponseEntity<UserResponseDTO> login(@Valid @RequestBody UserLoinDTORequest dto){
    	log.info("crce controller login -> {} " + dto.toString());
        final String userAndRuc=String.format("%s%s%s",dto.getUsername().trim(),String.valueOf(Character.LINE_SEPARATOR),dto.getIdSubsidiary());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userAndRuc, dto.getPassword()));
        UserDetailDTO user = (UserDetailDTO) authentication.getPrincipal();
        final String accessToken = jwtProvider.createToken(userAndRuc,user);
        UserResponseDTO responseDTO=new UserResponseDTO(accessToken,user.getUsername(),user.getFullname(),(long) 1,user.getAuthorities());        
        return ResponseEntity.ok(responseDTO);
    }
}
