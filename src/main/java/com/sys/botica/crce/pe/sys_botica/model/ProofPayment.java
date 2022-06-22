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
import lombok.ToString;

@Data
@Builder
@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = SysBoticaConstant.TB_SYS_BOTICA_PROOF_PAYMENT, schema = SysBoticaConstant.SCHEMA_SYS_BOTICA_CRCEUTIL)
public class ProofPayment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "cod")
	private String cod;
	
	@Column(name = "state")
	private String state;
}
