package com.sys.botica.crce.pe.sys_botica.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "adm_product",schema = "crce")
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "summaryname")
	private String summaryname;
	
	@Column(name = "isexpiratedate")
	private String isexpiratedate;
	
	@Column(name = "isrefrigeration")
	private String isrefrigeration;
	
	@Column(name = "isbatch")
	private String isbatch;
	
	@Column(name = "isgeneric")
	private String isgeneric;
	
	@Column(name = "iskit")
	private String iskit;
	
	@Column(name = "ismedicine")
	private String ismedicine;
	
	@Column(name = "iscontrolled")
	private String iscontrolled;
	
	@Column(name = "barcode")
	private String barcode;
	
	@Column(name = "state")
	private String state;
	
	@ManyToOne
	@JoinColumn(name = "idcategory")
	private Category category;
	
	@ManyToOne
	@JoinColumn(name = "idmark")
	private Mark mark;
	
	@ManyToOne
	@JoinColumn(name = "idunit")
	private Unit unit;
}
