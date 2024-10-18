package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import com.model.BankEntity;


@Repository
public interface BankEntityRepo extends JpaRepository<BankEntity, Long>{

	
	@Query("SELECT a FROM BankEntity a WHERE a.email=:email")
	BankEntity findbyemail(String email);
	
	
	@Query("SELECT a from BankEntity a where a.email=:email")
	BankEntity fetchByEmailId(String email);

}
