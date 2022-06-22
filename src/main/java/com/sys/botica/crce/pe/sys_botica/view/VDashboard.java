package com.sys.botica.crce.pe.sys_botica.view;

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
@Table(name = SysBoticaConstant.VT_SYS_BOTICA_DASHBOARD, schema = SysBoticaConstant.SCHEMA_SYS_BOTICA_CRCE)
public class VDashboard {
	
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "quantity_product")
	private Integer quantityProduct;
	
	@Column(name = "quantity_provider")
	private Integer quantityProvider;
	
	@Column(name = "quantity_mark")
	private Integer quantityMark;
	
	@Column(name = "quantity_category")
	private Integer quantityCategory;
	
	@Column(name = "quantity_unit")
	private Integer quantityUnit;
	
	@Column(name = "quantity_customer")
	private Integer quantityCustomer;
}
