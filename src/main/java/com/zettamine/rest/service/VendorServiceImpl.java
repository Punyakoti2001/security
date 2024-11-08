package com.zettamine.rest.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zettamine.rest.Entity.Vendor;
import com.zettamine.rest.repository.VendorRepository;

@Service
public class VendorServiceImpl implements VendorService 
{

	private VendorRepository vendorRepo;
	
	
	public VendorRepository getVendorRepo() {
		return vendorRepo;
	}


	@Autowired
	public void setVendorRepo(VendorRepository vendorRepo) {
		this.vendorRepo = vendorRepo;
	}


	public void saveVendor(Vendor vendor)
	{
		Vendor ven = new Vendor(vendor.getVendorId(),
								vendor.getVendorName().toLowerCase(),
								vendor.getContactNo().toLowerCase(),
								vendor.getCity().toLowerCase(),
								vendor.getState().toLowerCase(),
								vendor.getEmail().toLowerCase(),
								vendor.getStatus().toLowerCase());
		
		vendorRepo.save(ven);
	}


	@Override
	public List<Vendor> getAllVendor(String string) {
		List<Vendor> report = vendorRepo.findAllByStatus("active");
		return report;
	}


	@Override
	public boolean deleteById(Integer vendorId){
		
		Optional<Vendor> byId = vendorRepo.findById(vendorId);	
		if(byId.isPresent())
		{
			System.err.println("+".repeat(10));
			Vendor vendor = byId.get();
			vendor.setStatus("inActive");
			vendorRepo.save(vendor);
			return true;
		}
		else
			return false;
		
	}
	@Override
	public Vendor fingById(Integer id)
	{
		Optional<Vendor> byId = vendorRepo.findById(id);
		if(byId.isPresent())
		{
			Vendor vendor = byId.get();
			return vendor;
		}
		else
			return null;
	}


	@Override
	public List<Integer> findAllIds() 
	{
		 List<Integer> report = vendorRepo.findAllIds();
		 
		return report;
	}


	@Override
	public List<Vendor> getAllVendor() {
		
		List<Vendor> report = vendorRepo.findAll();
		
		return report;
	}





	
	
}
