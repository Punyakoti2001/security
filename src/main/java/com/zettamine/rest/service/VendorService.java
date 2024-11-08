package com.zettamine.rest.service;

import java.util.List;

import com.zettamine.rest.Entity.Vendor;

public interface VendorService
{

	void saveVendor(Vendor vendor);
	
	List<Vendor> getAllVendor(String string);
	
	boolean deleteById(Integer vendorId);
	
	Vendor fingById(Integer id);

	List<Integer> findAllIds();

	List<Vendor> getAllVendor();

}
