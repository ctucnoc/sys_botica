package com.sys.botica.crce.pe.sys_botica.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Data;

@Data
@MappedSuperclass
public class GenericModel {
	@Column(name = "usercreated")
	private String usercreated;
	
	@Column(name = "useralter")
	private String useralter;
	
	@Column(name = "datecreated")
	private Date datecreated;
	
	@Column(name = "datealter")
	private Date datealter;

}
