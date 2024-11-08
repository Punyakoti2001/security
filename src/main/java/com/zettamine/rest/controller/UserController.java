package com.zettamine.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zettamine.rest.dto.User;
import com.zettamine.rest.dto.UserDto;
import com.zettamine.rest.security.JwtService;
import com.zettamine.rest.service.UserServices;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@RestController
@NoArgsConstructor
@AllArgsConstructor
@RequestMapping("user")
public class UserController 
{
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserServices userServices;
	
	@Autowired
	private UserDetailsService detailesService;
	
	@PostMapping("/newUser")
	public ResponseEntity<?> saveNewUser(@RequestBody UserDto userDto)
	{
		userServices.saveUser(userDto);
		return ResponseEntity.status(HttpStatus.OK).body("Saved");
	}
	
	@PostMapping("/auth")
	public String authenticateAndGetToken(@RequestBody User user)
	{
		 Authentication authenticate = authenticationManager.authenticate(
			                            new UsernamePasswordAuthenticationToken(
											user.getUserName(), user.getPassword()));
		
		
		UserDetails userDetails = detailesService.loadUserByUsername(user.getUserName());
		
		System.err.println(userDetails.getAuthorities());
		 if(authenticate.isAuthenticated())
			 return jwtService.getToken(userDetails); 
		 return "wrong userName and Password";
	}

}
