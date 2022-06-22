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
@ToString
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = SysBoticaConstant.TB_SYS_BOTICA_CUSTOMER, schema = SysBoticaConstant.SCHEMA_SYS_BOTICA_CRCE)
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "numberdocument")
	private String numberdocument;
	
	@Column(name = "firstname")
	private String firstname;
	
	@Column(name = "lastname")
	private String lastname;
	
	@Column(name = "bussinesname")
	private String bussinesname;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "direction")
	private String direction;
	
	@Column(name = "state")
	private String state;
	
	@ManyToOne
	@JoinColumn(name = "idtypedocument")
	private TypeDocument typeDocument;
}
