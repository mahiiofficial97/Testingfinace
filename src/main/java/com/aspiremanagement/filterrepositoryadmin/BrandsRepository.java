package com.aspiremanagement.filterrepositoryadmin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aspiremanagement.filtermodeladmin.Brands;

@Repository
public interface BrandsRepository extends JpaRepository<Brands, Long> {

	@Query(value = "Select b from Brands b where b.brandName=:brandName AND b.categoryId=:categoryId")
	Brands getBrandByName(String brandName, Long categoryId);

	@Query(value = "Select b from Brands b where b.categoryId=:categoryId")
	List<Brands> getAllCategoryWiseBrands(Long categoryId);

}
