package com.example.demo.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("bhaskar").password("{noop}12345").authorities("EMPLOYEE");
		auth.inMemoryAuthentication().withUser("admin").password("{noop}12345").authorities("ADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/home").permitAll()
		.antMatchers("/welcome").authenticated()
		.antMatchers("/emp").hasAuthority("EMPLOYEE")
		.antMatchers("/admin").hasAuthority("ADMIN")
		.and()
		.formLogin()
		.defaultSuccessUrl("/welcome",true)
		.and()
		.logout()
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.and()
		.exceptionHandling()
		.accessDeniedPage("/denied")
		;
	}

}
