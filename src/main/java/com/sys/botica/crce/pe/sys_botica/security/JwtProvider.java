package com.sys.botica.crce.pe.sys_botica.security;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import com.sys.botica.crce.pe.sys_botica.config.PropertiesConfig;
import com.sys.botica.crce.pe.sys_botica.dto.UserDetailDTO;
import com.sys.botica.crce.pe.sys_botica.errorhandler.SysBoticaGenericClientException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtProvider  {

	private PropertiesConfig propertiesConfig;

	public JwtProvider(PropertiesConfig propertiesConfig) {
		this.propertiesConfig = propertiesConfig;
	}

	public String createToken(String keyWord, UserDetailDTO principalDTO) {
		log.info("createToken -> {} " + principalDTO);
		Claims claims = Jwts.claims().setSubject(keyWord);
		claims.put("auth", principalDTO.getAuthorities());
		claims.put("fullname", principalDTO.getFullname());
		Date date = new Date();
		Date validity = new Date(date.getTime() + propertiesConfig.getValidityMilliseconds()*100);
		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(date)
				.setExpiration(validity)
				.signWith(SignatureAlgorithm.HS256, propertiesConfig.getSecretKey())
				.compact();
	}
	
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(propertiesConfig.getSecretKey()).parseClaimsJws(token).getBody();
	}
	
	public String refreshToken(String token) {
		final Date createdDate = new Date();
		final Date expirationDate = calculeExpirationRefreshDate(createdDate);
		final Claims claims = getAllClaimsFromToken(token);
		claims.setIssuedAt(createdDate);
		claims.setExpiration(expirationDate);
		return Jwts.builder()
				.setClaims(claims)
				.signWith(SignatureAlgorithm.HS512, propertiesConfig.getSecretKey())
				.compact();
	}

	public String getUsername(String token) {
		return Jwts.parser().setSigningKey(propertiesConfig.getSecretKey()).parseClaimsJws(token).getBody().getSubject();
	}

	public String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(propertiesConfig.getSecretKey()).parseClaimsJws(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			throw new SysBoticaGenericClientException("Expired or invalid JWT token", HttpStatus.BAD_REQUEST);
		}
	}
	
	
	private Date calculeExpirationRefreshDate(Date createdDate) {
		return new Date(createdDate.getTime() + propertiesConfig.getValidityMilliseconds() * 20000);
	}

}
