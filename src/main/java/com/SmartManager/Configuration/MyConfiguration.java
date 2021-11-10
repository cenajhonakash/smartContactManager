package com.SmartManager.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class MyConfiguration extends WebSecurityConfigurerAdapter {

	//use to bind all user data at runtime in CustomUserDetailServImpl
	@Bean
	public UserDetailsService getUserDetailServ()
	{
		//System.out.println("1");
		return new CustomUserDetailServImpl();
	}
	@Bean
	public BCryptPasswordEncoder passEncode()
	{
		//System.out.println("2");
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authProvider()
	{
	//	System.out.println("3");
		DaoAuthenticationProvider prov = new DaoAuthenticationProvider();
		prov.setUserDetailsService(this.getUserDetailServ());
		prov.setPasswordEncoder(passEncode());
		
		System.out.println(prov.toString());
		return prov;
	}
	
	//method configuration
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	//	System.out.println("4");
		auth.authenticationProvider(authProvider());
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//System.out.println("5");
		http.authorizeRequests().antMatchers("/user/**").hasRole("USER")
		.antMatchers("/admin/**").hasRole("ADMIN").
		antMatchers("/**").permitAll().and().formLogin().loginPage("/signin").
		loginProcessingUrl("/doLogin").
		defaultSuccessUrl("/user/index").
		and().csrf().disable();
	}
	
	
}
