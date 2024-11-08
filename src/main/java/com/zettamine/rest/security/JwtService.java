package com.zettamine.rest.security;

import java.security.Key;
import java.security.PublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.zettamine.rest.dto.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


@Component
public class JwtService {
	
	private final String SECRET = "mqKcywWjlQRXQI5d9R1ddHP2hZCX81BERR10w/T1aZk=";
	
	
	
	public String extractUsername(String token) {
		System.err.println("Inside extractUsername() ");
		
		return extractClaims(token,Claims::getSubject);
	}


	private <T> T extractClaims(String token, Function<Claims , T> claimsResolver) {
		System.err.println("inside extractClaim()");
		Claims claims = extractAllClaims(token);
		
		return claimsResolver.apply(claims);
	}


	private Claims extractAllClaims(String token) {
		System.err.println("inside claimsAll()");
		
		return Jwts
				.parser()
				.verifyWith(getSignKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();			
				
	}
	
	
	public boolean isValid(String token, UserDetails details) {
		String userName = extractUsername(token);
		
		if(details.getUsername().equals(userName) && !isTokenExpired(token))
			return true;
		return false;
	}
	
	

	private boolean isTokenExpired(String token) {
		Date claims = extractClaims(token, Claims::getExpiration);
		
		return claims.before(new Date());
	}


	public String getToken(UserDetails user)
	{
		
		return createToken(user);
		
	}

//	@Autowired
//	private UserDetails user;
	
	private String createToken( UserDetails user) {
	
//		@Sunil Sir Code
		// return Jwts.builder()
		// 		.setHeaderParam("typ","JWT")
		// 		.setIssuer("Zettamine")        					
       	// 	.setSubject(userName)        					
       	// 	.setIssuedAt(new Date(System.currentTimeMillis()))
       	// 	.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) //30 mins
       	// 	.addClaims(claims)        					
       	// 	.signWith(SignatureAlgorithm.HS256, getSignKey())
       	// 	.compact(); 

		
//		return Jwts.builder()
//				.setHeaderParam("typ","JWT")
//				.setClaims(claims)
//				.setSubject(userName)
//				.setIssuedAt(new Date(System.currentTimeMillis()))
//				.setExpiration(new Date(System.currentTimeMillis()+1000*60*30))
//				.signWith(getSignKey(),SignatureAlgorithm.HS256).compact();
//				.signWith(SignatureAlgorithm.HS256, SECRET).compact();

            return Jwts.builder()
				.subject(user.getUsername())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis()))
				.signWith(getSignKey()).claim("roles", user.getAuthorities())
				.compact();
				

}

	private SecretKey getSignKey() {
		byte[] decode = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(decode);
		
	}


	

	
}	