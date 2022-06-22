package com.sys.botica.crce.pe.sys_botica.view;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Immutable;
import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Immutable
@Table(name = SysBoticaConstant.VT_SYS_BOTICA_SALE_DATE, schema = SysBoticaConstant.SCHEMA_SYS_BOTICA_CRCE)
public class VSaleDate {

	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "date")
	private Date date;
	
	@Column(name = "total_price")
	private Float totalPrice;
}
