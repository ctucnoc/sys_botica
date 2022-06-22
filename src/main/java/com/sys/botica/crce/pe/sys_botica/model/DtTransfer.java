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
import lombok.ToString;

@Data
@ToString
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = SysBoticaConstant.TB_SYS_BOTICA_DT_TRANSFER, schema = SysBoticaConstant.SCHEMA_SYS_BOTICA_CRCE)
public class DtTransfer {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "amount")
	private Integer amount;
		
	@Column(name = "state")
	private String state;
	
	@Column(name = "usercreated")
	private String usercreated;
	
	@Column(name = "useralter")
	private String useralter;
	
	@Column(name = "datecreated")
	private Date datecreated;
	
	@Column(name = "datealter")
	private Date datealter;
	
	@Column(name = "saleprice")
	private Float saleprice;
	
	@ManyToOne
	@JoinColumn(name = "iddtentryproduct")
	private DtEntryProduct dtEntryProduct;
	
	@ManyToOne
	@JoinColumn(name = "idtransfer")
	private Transfer transfer;
}
