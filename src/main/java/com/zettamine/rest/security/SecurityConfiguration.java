package com.zettamine.rest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration 
{
	@Autowired
	private JwtFilter jwtFilter;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	//authorization
//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
//	{
//		System.err.println("inside the security Chain");
//		http.authorizeHttpRequests(request->request
//				.requestMatchers("/user/**","/v2/api-docs",
//			            "/swagger-resources",
//			            "/swagger-resources/**",
//			            "/configuration/ui",
//			            "/configuration/security",
//			            "/swagger-ui.html",
//			            "/webjars/**",
//			            "/v3/api-docs/**",
//			            "/swagger-ui/**"
//						         )
//				.permitAll()
//				.requestMatchers("/vendors/**").authenticated()).formLogin(Customizer.withDefaults());
//		
//		http.csrf(csrf->csrf.disable()).httpBasic(Customizer.withDefaults());
//		return http.build();
//		
//	}
	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
	{
		System.err.println("inside the security Chain");
		http.authorizeHttpRequests(request->request
				.requestMatchers("/user/**","/v2/api-docs",
			            "/swagger-resources",
			            "/swagger-resources/**",
			            "/configuration/ui",
			            "/configuration/security",
			            "/swagger-ui.html",
			            "/webjars/**",
			            "/v3/api-docs/**",
			            "/swagger-ui/**")
				.permitAll()
				.requestMatchers("/vendors/**").authenticated())
		.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.authenticationProvider(authenticationProvider())
		.addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class);
		
		
		http.csrf(csrf->csrf.disable());
		return http.build();
		
	}
	
	
	              //authentication
//	@Bean
//	public UserDetailsService userDetailsService()
//	{
//		System.err.println("inside the userDetailsService()");
//		
//		return new UserInfoDetailesService();
//		
//	}
//	
	@Bean
	public AuthenticationProvider authenticationProvider()
	{
		System.err.println("inside the authenticationProvider()");
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		
		return authenticationProvider;
	}
	
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
	{
		try {
			return config.getAuthenticationManager();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return null;
	}
	

}
