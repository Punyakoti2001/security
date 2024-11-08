package com.zettamine.rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.zettamine.rest.Entity.Vendor;

public interface VendorRepository extends JpaRepository<Vendor, Integer> 
{

	@Query("select vendorId from Vendor")
	List<Integer> findAllIds();

	@Query("from Vendor where status=:stus")
	List<Vendor> findAllByStatus(String stus);
	

}
