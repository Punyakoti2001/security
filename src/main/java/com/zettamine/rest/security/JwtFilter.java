package com.zettamine.rest.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserInfoDetailesService detailesService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		System.err.println("inside filter");
		String header = request.getHeader("Authorization");
		String token = null;
		String username = null;
		
		if(header != null && header.startsWith("Bearer "))
		{
			token = header.substring(7);
			username = jwtService.extractUsername(token);
			System.out.println();
			System.err.println(username);
		}
		
		if(username != null && SecurityContextHolder.getContext().getAuthentication()==null)
		{
			System.err.println("inside second If conditional statement");
			
			UserDetails details = detailesService.loadUserByUsername(username);
			
			if(jwtService.isValid(token,details))
			{
				System.err.println("inside third If conditional statement");
			//in constructors UserDetails,credentials,authorities
			UsernamePasswordAuthenticationToken authenticationToken =
					new UsernamePasswordAuthenticationToken(details,null,details.getAuthorities());
			
			authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		}
		System.err.println(" outside If conditional statement");
		filterChain.doFilter(request, response);
		
	}

}
