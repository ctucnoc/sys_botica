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
@Table(name = SysBoticaConstant.TB_SYS_BOTICA_ENTRY_PRODUCT, schema = SysBoticaConstant.SCHEMA_SYS_BOTICA_CRCE)
public class EntryProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "state")
	private String state;

	@Column(name = "useralter")
	private String userAlter;

	@Column(name = "usercreated")
	private String userCreated;

	@Column(name = "datealter")
	@Temporal(TemporalType.DATE)
	private Date dateAlter;

	@Column(name = "datecreated")
	@Temporal(TemporalType.DATE)
	private Date dateCreated;

	@ManyToOne
	@JoinColumn(name = "idprovider")
	private Provider provider;

	@ManyToOne
	@JoinColumn(name = "idwharehouse")
	private Wharehouse wharehouse;
	
	@ManyToOne
	@JoinColumn(name = "identrydocument")
	private EntryDocument entryDocument;
}
