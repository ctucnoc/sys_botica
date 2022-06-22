package com.sys.botica.crce.pe.sys_botica.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthorityDTO {
	private Long id;
	private GenericDTO user;
	private GenericDTO authority;
}
