package com.sys.botica.crce.pe.sys_botica.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "adm_unit",schema = "crce")
public class Unit {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "initials")
	private String initials;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "state")
	private String state;
}
