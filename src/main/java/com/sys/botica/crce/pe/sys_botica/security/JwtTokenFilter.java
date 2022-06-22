package com.sys.botica.crce.pe.sys_botica.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sys.botica.crce.pe.sys_botica.dto.UserDetailDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {
	

	private JwtProvider jwtTokenProvider;
    private UserDetailsService sysceDetailsService; 

	public JwtTokenFilter(JwtProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}
	
	public JwtTokenFilter(JwtProvider jwtTokenProvider,UserDetailsService sysceDetailsService) {
		this.jwtTokenProvider = jwtTokenProvider;
		this.sysceDetailsService = sysceDetailsService;
	}	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		log.info("doFilterInternal -> {} " + request);
		String token = this.jwtTokenProvider.resolveToken(request);
		try {
			if (token != null && this.jwtTokenProvider.validateToken(token)) {
				Authentication auth = this.getAuthentication(token);
				log.info("auth -> {} " + auth);
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		} catch (RuntimeException e) {
			SecurityContextHolder.clearContext();
			response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
			return;
		}
		filterChain.doFilter(request, response);
	}
	
	public Authentication getAuthentication(String token) {
		String userName = this.jwtTokenProvider.getUsername(token);
		log.info("username -> {} " + userName);
		UserDetailDTO userDetails = (UserDetailDTO) this.sysceDetailsService
				.loadUserByUsername(userName);
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

}
