package com.sys.botica.crce.pe.sys_botica.dto;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDTO {
    private String accsesToken;
    private String user;
    private String name;
    private Long idSubsidiary;
    private Collection<? extends GrantedAuthority> authorities;
}
