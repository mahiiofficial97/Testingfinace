package com.aspiremanagement.repositoryadmin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aspiremanagement.modeladmin.SubCategory;
import com.aspiremanagement.modeladmin.SubCategoryResponse;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long>{

	@Query(value = "Select s from SubCategory s where s.subCatogotyName=:subCatogotyName")
	SubCategory getCategory(String subCatogotyName);

	@Query(value = "Select s from SubCategory s where s.categoryId=:categoryId")
	List<SubCategory> fetchCategoryWiseSubCategory(Long categoryId);

	

}
