package com.sys.botica.crce.pe.sys_botica.dto;

import java.util.Collection;
import java.util.Date;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.Data;

@Data
public class UserDetailDTO implements UserDetails{
	private static final long serialVersionUID = 1L;
	
	private String username;
	private String fullname;
	private String password;
	private Boolean state;
	private Collection<? extends GrantedAuthority> authorities;
	private Date lastPasswordResetDate;
	
	public UserDetailDTO(String username, String password, String fullname, boolean state,
			Collection<? extends GrantedAuthority> authorities, Date lastPasswordResetDate) {
		this.username = username;
		this.password = password;
		this.fullname = fullname;
		this.state = state;
		this.authorities = authorities;
		this.lastPasswordResetDate = lastPasswordResetDate;
	}
    
    public String getFullname(){
        return fullname;
    }

    public Date getLastPasswordResetDate(){
        return lastPasswordResetDate;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
