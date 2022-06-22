package com.sys.botica.crce.pe.sys_botica.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "adm_security_policy",schema = "crcegu")
public class SegurityPolicy {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "")
	private Long id;
	
	@Column(name = "minpasswordlength")
	private Integer minpasswordlength;
	
	@Column(name = "maxpasswordlength")
	private Integer maxpasswordlength;
	
	@Column(name = "maxnumberattempts")
	private Integer maxnumberattempts;
	
	@Column(name = "maxidletime")
	private Integer maxidletime;
	
	@Column(name = "passwordchangefirstlogin")
	private String passwordchangefirstlogin;
	
	@Column(name = "codesecuritypolicy")
	private String codesecuritypolicy;
}
