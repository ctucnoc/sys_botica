package com.sys.botica.crce.pe.sys_botica.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import com.sys.botica.crce.pe.sys_botica.constant.SysBoticaConstant;
import com.sys.botica.crce.pe.sys_botica.security.JwtAuthenticationEntryPoint;
import com.sys.botica.crce.pe.sys_botica.security.JwtProvider;
import com.sys.botica.crce.pe.sys_botica.security.JwtTokenFilterConfigurer;
import com.sys.botica.crce.pe.sys_botica.security.SysBoticaAccessDeniedHandler;
import com.sys.botica.crce.pe.sys_botica.security.SysBoticaAuthenticationEntryPoint;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SysceSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	UserDetailsService sysceDetailsService;

    @Autowired
    JwtProvider jwtTokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors().and().csrf().disable();
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        httpSecurity.authorizeRequests()
        		.antMatchers(HttpMethod.GET, SysBoticaConstant.RESOURCE_USER_SUBSIDIARY_PERMIT_ALL).permitAll()
                .antMatchers(HttpMethod.POST,SysBoticaConstant.RESOURCE_SIP_PERMIT_ALL).permitAll()
                .antMatchers("/swagger-ui.html", "/swagger-resources/**", "/webjars/**","/v2/api-docs").permitAll()
                .anyRequest().authenticated();
        httpSecurity.exceptionHandling().accessDeniedHandler(accessDeniedHandler()).authenticationEntryPoint(authenticationEntryPoint());

        httpSecurity.apply(new JwtTokenFilterConfigurer(jwtTokenProvider,sysceDetailsService));

    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return new SysBoticaAuthenticationEntryPoint();
    }

    @Bean
    AccessDeniedHandler accessDeniedHandler(){
        return new SysBoticaAccessDeniedHandler();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(sysceDetailsService).passwordEncoder(passwordEncoder());
    }
}
