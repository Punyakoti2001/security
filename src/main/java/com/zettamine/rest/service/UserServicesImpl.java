package com.zettamine.rest.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.zettamine.rest.Entity.User;
import com.zettamine.rest.dto.UserDto;
import com.zettamine.rest.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@AllArgsConstructor
public class UserServicesImpl implements UserServices {

	private UserRepository userRepository;

	private PasswordEncoder encoder;
	
	@Override
	public void saveUser(UserDto userDto) 
	{
		
		User user = new User();
		user.setFirstName(userDto.getFirstName().toLowerCase());
		user.setLastName(userDto.getLastName().toLowerCase());
		user.setEmail(userDto.getEmail().toLowerCase());
		user.setPassword(encoder.encode(userDto.getPassword().toLowerCase()));
		user.setRoles(userDto.getRoles().toUpperCase());
		userRepository.save(user);
		
	}

}
