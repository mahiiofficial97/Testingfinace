package com.aspiremanagement.repositoryadmin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aspiremanagement.modeladmin.Admin;

@Repository
public interface AdminApplicationRepository extends JpaRepository<Admin, Long>{

	@Query("SELECT a from Admin a where a.email=:email")
	Admin fetchByEmailId(String email);

	
	Admin findUserById(long parseLong);

}
