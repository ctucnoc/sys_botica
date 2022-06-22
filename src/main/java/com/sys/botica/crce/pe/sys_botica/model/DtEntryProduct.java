package com.sys.botica.crce.pe.sys_botica.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = SysBoticaConstant.TB_SYS_BOTICA_DT_ENTRY_PRODUCT, schema = SysBoticaConstant.SCHEMA_SYS_BOTICA_CRCE)
public class DtEntryProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "purchaseprecio")
	private Float purchaseprecio;
	
	@Column(name = "amount")
	private Integer amount;
	
	@Column(name = "expiratedate")
	@Temporal(TemporalType.DATE)
	private Date expiratedate;
	
	@Column(name = "lotnumber")
	private String lotnumber;
	
	@Column(name = "state")
	private String state;
	
	@Column(name = "usercreated")
	private String usercreated;
	
	@Column(name = "datecreated")
	@Temporal(TemporalType.DATE)
	private Date datecreated;
	
	@Column(name = "datealter")
	@Temporal(TemporalType.DATE)
	private Date datealter;
	
	@ManyToOne
	@JoinColumn(name = "identryproduct")
	private EntryProduct entryProduct;
	
	@ManyToOne
	@JoinColumn(name = "idproduct")
	private Product product;
}
