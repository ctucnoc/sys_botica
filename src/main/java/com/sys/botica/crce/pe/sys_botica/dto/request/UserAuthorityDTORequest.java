package com.sys.botica.crce.pe.sys_botica.dto.request;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthorityDTORequest {
	@NotNull
	private Long idUser;
	
	@NotNull
	private Long idAuthority;
}
