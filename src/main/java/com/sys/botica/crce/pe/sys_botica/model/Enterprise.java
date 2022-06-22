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
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "adm_enterprise", schema = "crce")
public class Enterprise {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "bussinesname")
	private String bussinesname;
	
	@Column(name = "tradename")
	private String tradename;
	
	@Column(name = "ruc")
	private String ruc;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "cellphone")
	private String cellphone;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "webpage")
	private String webpage;
	
	@Column(name = "state")
	private String state;
}
