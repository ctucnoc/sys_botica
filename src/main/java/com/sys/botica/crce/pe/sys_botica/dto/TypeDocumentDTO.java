package com.sys.botica.crce.pe.sys_botica.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TypeDocumentDTO {
	private Long id;
	private String description;
}
