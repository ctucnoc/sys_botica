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
@Table(name = SysBoticaConstant.TB_SYS_BOTICA_SALE, schema = SysBoticaConstant.SCHEMA_SYS_BOTICA_CRCE)
public class Sale {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "usercreated")
	private String usercreated;

	@Column(name = "useralter")
	private String useralter;

	@Column(name = "datecreated")
	private Date datecreated;

	@Column(name = "datealter")
	private Date datealter;

	@Column(name = "state")
	private String state;

	@ManyToOne
	@JoinColumn(name = "idcustomer")
	private Customer customer;

	@ManyToOne
	@JoinColumn(name = "idproofpayment")
	private ProofPayment proofPayment;
}
