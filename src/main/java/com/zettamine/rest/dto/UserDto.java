package com.zettamine.rest.dto;

import com.zettamine.rest.Entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class UserDto 
{
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String roles;

}