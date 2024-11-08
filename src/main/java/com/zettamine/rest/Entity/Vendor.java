package com.zettamine.rest.Entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Vendor extends BaseEntity 
{
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "vendorid")
	@SequenceGenerator(name = "vendorid",initialValue = 5000,allocationSize = 1)
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
