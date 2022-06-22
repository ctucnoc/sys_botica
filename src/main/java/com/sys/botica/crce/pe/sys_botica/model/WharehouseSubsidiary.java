package com.sys.botica.crce.pe.sys_botica.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = SysBoticaConstant.TB_SYS_BOTICA_WHAREHOUSE_SUBSIDIARY, schema = SysBoticaConstant.SCHEMA_SYS_BOTICA_CRCEGU)
public class WharehouseSubsidiary {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "state")
	private String state;
	
	@ManyToOne
	@JoinColumn(name = "idwharehouse")
	private Wharehouse wharehouse;
	
	@ManyToOne
	@JoinColumn(name = "idsubsidiary")
	private Subsidiary subsidiary;

}
