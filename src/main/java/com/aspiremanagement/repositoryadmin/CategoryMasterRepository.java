package com.aspiremanagement.repositoryadmin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aspiremanagement.modeladmin.CategoryMaster;

@Repository
public interface CategoryMasterRepository extends JpaRepository<CategoryMaster, Long>{

	@Query(value = "Select c from CategoryMaster c where c.categoryName=:categoryName")
	CategoryMaster findByCategoryNAme(String categoryName);

}
