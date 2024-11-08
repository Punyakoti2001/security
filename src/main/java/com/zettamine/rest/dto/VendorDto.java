package com.zettamine.rest.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class VendorDto 
{

	private Integer vendorId;
	
	
	@NotBlank
	@NotNull
	private String  vendorName;
	
	
	@NotBlank
	@NotNull
	@Pattern(regexp = "[6-9]{1}[0-9]{9}")
	private String contactNo;
	
	@NotBlank
	@NotNull
	@Pattern(regexp = "[a-zA-Z ]+")
	private String city;
	
	@NotBlank
	@NotNull
	@Pattern(regexp = "[a-zA-Z ]+")
	private String state;
	

	@Email
	private String email;
	
	@NotBlank
	@NotNull
	private String status ="active";

}
