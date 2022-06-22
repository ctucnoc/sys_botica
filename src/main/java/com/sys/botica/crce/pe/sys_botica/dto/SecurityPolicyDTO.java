package com.sys.botica.crce.pe.sys_botica.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SecurityPolicyDTO {
	private Long id;
	private Integer minpasswordlength;
	private Integer maxpasswordlength;
	private Integer maxnumberattempts;
	private Integer maxidletime;
	private String passwordchangefirstlogin;
}
