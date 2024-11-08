package com.zettamine.rest.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.zettamine.rest.Entity.User;
import com.zettamine.rest.repository.UserRepository;


@Component
public class UserInfoDetailesService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByEmail(username);
		System.out.println(user.get().getRoles());
		return user.map(o->new UserInfoDetails(o))
				   .orElseThrow(()->new UsernameNotFoundException(username));
		
		
		
	}

}
