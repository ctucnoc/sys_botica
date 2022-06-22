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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = SysBoticaConstant.TB_SYS_BOTICA_USER_AUTHORITY, schema = SysBoticaConstant.SCHEMA_SYS_BOTICA_CRCEGU)
public class UserAuthority {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "state")
	private String state;
	
	@ManyToOne
	@JoinColumn(name = "idauthority")
	private Authority authority;
	
	@ManyToOne
	@JoinColumn(name = "iduser")
	private User user;
}
