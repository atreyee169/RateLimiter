package com.blueoptima.ratelimiter.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.blueoptima.ratelimiter.auth.UserAuthService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	UserAuthService authSvc;
	
	@Bean
	public DaoAuthenticationProvider getAuthProvider()
	{
		DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(authSvc);
		daoAuthenticationProvider.setPasswordEncoder(getPasswordEncoder());
		daoAuthenticationProvider.setAuthoritiesMapper(authoritiesMapper());
		return daoAuthenticationProvider;
	}
	
	public GrantedAuthoritiesMapper authoritiesMapper()
	{
		SimpleAuthorityMapper authorityMapper=new SimpleAuthorityMapper();
		authorityMapper.setConvertToUpperCase(true);
		authorityMapper.setDefaultAuthority("USER");
		return authorityMapper;
	}
	//for authentication
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.authenticationProvider(getAuthProvider());
	}
	
	//for authorization
	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		http.authorizeRequests().antMatchers("/api/**").hasAnyRole("USER","ADMIN")
		.antMatchers("/").permitAll()
		.antMatchers("/h2-console/**").permitAll();
		http.csrf().disable();
		http.headers().frameOptions().disable();
		http.formLogin();
		
		//.and().formLogin();
	}
	
	@Bean
	public PasswordEncoder getPasswordEncoder()
	{
		return new BCryptPasswordEncoder(11);
	}
}
