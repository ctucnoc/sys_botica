package com.sys.botica.crce.pe.sys_botica.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = SysBoticaConstant.TB_SYS_BOTICA_USER, schema = SysBoticaConstant.SCHEMA_SYS_BOTICA_CRCEGU)
public class User {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "fullname")
	private String fullName;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "state")
	private String state;
	
	@Column(name = "numberattempts")
	private int numberattempts;
	
	@Column(name = "passwordchangefirstlogin")
	private String passwordchangefirstlogin;
}
