package com.sys.botica.crce.pe.sys_botica.security;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtTokenFilterConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	private JwtProvider jwtTokenProvider;
	private UserDetailsService sysceDetailsService; 

	public JwtTokenFilterConfigurer(JwtProvider jwtTokenProvider,UserDetailsService sysceDetailsService) {
		this.jwtTokenProvider = jwtTokenProvider;
		this.sysceDetailsService = sysceDetailsService;
	}

	@Override
	public void configure(HttpSecurity builder) throws Exception {
		JwtTokenFilter tokenFilter = new JwtTokenFilter(jwtTokenProvider,sysceDetailsService);
		builder.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
	}

}
