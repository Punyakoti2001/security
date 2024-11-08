package com.zettamine.rest.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zettamine.rest.Entity.Vendor;
import com.zettamine.rest.dto.VendorDto;
import com.zettamine.rest.service.VendorService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/vendors")
public class VendorController 
{
	private Logger LOGGER = LoggerFactory.getLogger(VendorController.class);
	
	
	private VendorService vendorSer;
	
	
	
	public VendorService getVendorSer() {
		return vendorSer;
//		return 
	}

	@Autowired
	public void setVendorSer(VendorService vendorSer) {
		this.vendorSer = vendorSer;
		LOGGER.info("inside SetterInjection");
	}
	
	
	@PostMapping("/vendor")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> saveVendor(@RequestBody @Valid  VendorDto vendorDto)
	{
	

		LOGGER.info("inside saveVendor()");
		
		Vendor vendor = new Vendor();
		
		vendor.setVendorName(vendorDto.getVendorName());
		vendor.setContactNo(vendorDto.getContactNo());
		vendor.setCity(vendorDto.getCity());
		vendor.setState(vendorDto.getState());
		vendor.setEmail(vendorDto.getEmail());
	
		vendorSer.saveVendor(vendor);
		return new ResponseEntity<String>("sucessfully saved",HttpStatus.OK);
		
	}
	
	
	@GetMapping("/vendor")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<?> viewVendors()
	{
		LOGGER.info("inside Vendor ViewVendors()");
		List<Vendor> records = vendorSer.getAllVendor("active");
		
		if(records.size() <=0)
			return new ResponseEntity<>("No Records in the Vendor",HttpStatus.BAD_REQUEST);
		
		List<VendorDto> vendorDtoRecords = new ArrayList<>();
		for (Vendor vendor : records) 
		{
			 VendorDto ven = new VendorDto();
			 
			 ven.setVendorId(vendor.getVendorId());
			 ven.setVendorName(vendor.getVendorName());
			 ven.setContactNo(vendor.getContactNo());
			 ven.setCity(vendor.getCity());
			 ven.setState(vendor.getState());
			 ven.setEmail(vendor.getEmail());
			 ven.setStatus(vendor.getStatus());
			 
			 vendorDtoRecords.add(ven);
		}
		
		return new ResponseEntity<>(vendorDtoRecords,HttpStatus.ACCEPTED);
		
	}
	
	@DeleteMapping("/vendor")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<?> delete(@RequestParam Integer vendorId)
	{
		LOGGER.info("inside Vendor Delete()");
		
		boolean  report = vendorSer.deleteById(vendorId);
		if(report)
		{
			return new ResponseEntity<String>("Deleted sucessfully",HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<>("Record not found with Id: "+vendorId,HttpStatus.BAD_REQUEST);
		}
			
			
	}
	
	
	@PutMapping("/vendor")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<?> edit(@RequestBody @Valid VendorDto vendorDto)
	{
		LOGGER.info("inside Vendor edit()");
		
		Vendor findById = vendorSer.fingById(vendorDto.getVendorId());
		if(findById == null)
		{
			return new ResponseEntity<>("Vendor with Id: "+ vendorDto.getVendorId() +" is Not Found ",HttpStatus.BAD_REQUEST);
		}
		
		
        Vendor vendor = new Vendor();
		
        vendor.setVendorId(vendorDto.getVendorId());
		vendor.setVendorName(vendorDto.getVendorName());
		vendor.setContactNo(vendorDto.getContactNo());
		vendor.setCity(vendorDto.getCity());
		vendor.setState(vendorDto.getState());
		vendor.setEmail(vendorDto.getEmail());
		
		
		vendorSer.saveVendor(vendor);
		return new ResponseEntity<String>("Updated succesfully",HttpStatus.OK);	
		
		
			
	}
	
	@GetMapping("/vendor/{vendorId}")
	@PreAuthorize(value = "hasAuthority('ROLE_USER','ROLE_ADMIN')")
	public ResponseEntity<?> viewVendor(@PathVariable Integer vendorId)
	{
		LOGGER.info("Inside viewPlant()");
		
		 Vendor record = vendorSer.fingById(vendorId);
		 if(record == null)
			{
			 return new ResponseEntity<>("No Such Record with Id: "+vendorId,HttpStatus.BAD_REQUEST);
				
			}
		 VendorDto vendor = new VendorDto();
		 
		 vendor.setVendorId(record.getVendorId());
		 vendor.setVendorName(record.getVendorName());
		 vendor.setContactNo(record.getContactNo());
		 vendor.setCity(record.getCity());
		 vendor.setState(record.getState());
		 vendor.setEmail(record.getEmail());
		 vendor.setStatus(record.getStatus());
		 
		 return new ResponseEntity<>(vendor,HttpStatus.ACCEPTED);
		
		
		
	}

	
}
