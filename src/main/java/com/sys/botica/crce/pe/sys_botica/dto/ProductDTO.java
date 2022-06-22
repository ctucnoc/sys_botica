package com.sys.botica.crce.pe.sys_botica.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDTO {
	private Long id;
	private String name;
	private String summaryname;
	private String isexpiratedate;
	private String isrefrigeration;
	private String isbatch;
	private String isgeneric;
	private String iskit;
	private String ismedicine;
	private String iscontrolled;
	private String barcode;
	private GenericDTO category;
	private GenericDTO mark;
	private GenericDTO unit;
}
