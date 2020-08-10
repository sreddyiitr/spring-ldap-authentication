package com.spring.security.ldap.integration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableWebSecurity
public class LdapSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	private final Log logger = LogFactory.getLog(getClass());
	/**
	 * Authentication
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		
//		System.out.println(passwordEncoder.encode("benspassword"));
//		logger.info("encoded password: " + passwordEncoder.encode("benspassword"));
//		
//		logger.info("encoded password matches?: "+passwordEncoder.matches("benspassword", "$2a$10$C4RmaY2aRdOZwt6XxMvgTuKK1gwFeZPnWTSyVg.e0qYFgwUxaWOsO"));
//		logger.info("encoded password2 matches?: "+passwordEncoder.matches("benspassword", "$2a$10$LQlLfZXPPMAcdOaRXnhi0.hLrBI7Zbi2EQB5bBjHCBrTtxs65Csuy"));
//		logger.info("encoded spring password matches?: "+passwordEncoder.matches("benspassword", "$2a$10$c6bSeWPhg06xB1lvmaWNNe4NROmZiSpYhlocU/98HNr2MhIOiSt36"));
		
		
		authenticationManagerBuilder.ldapAuthentication()
		.userDnPatterns("uid={0},ou=people")
		.groupSearchBase("ou=groups")
		.contextSource()
		.url("ldap://localhost:8389/dc=springframework,dc=org")
		.and()
		.passwordCompare()
		.passwordEncoder(new BCryptPasswordEncoder())
		.passwordAttribute("userPassword");
	}

	/**
	 * Authorization
	 */
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeRequests().anyRequest().fullyAuthenticated().and().formLogin();
	}

}
