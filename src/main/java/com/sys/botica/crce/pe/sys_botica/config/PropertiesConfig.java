package com.sys.botica.crce.pe.sys_botica.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
public class PropertiesConfig {
	@Value("${jwt.secret}")
	private String secretKey;

	@Value("${jwt.expiration}")
	private Long validityMilliseconds;
}
