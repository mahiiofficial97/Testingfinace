package com.aspiremanagement.repositoryadmin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aspiremanagement.modeladmin.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

	@Query(value = "Select p from Product p where p.productName=:productName")
	Product getByProductName(String productName);

	@Query(value = "Select p from Product p where p.subCategoryId=:subCategoryId")
	List<Product> fetchSubCategoryWiseProducts(Long subCategoryId);

}
