package com.aspiremanagement.serviceadmin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aspiremanagement.modeladmin.Product;
import com.aspiremanagement.modeladmin.ProductResponse;
import com.aspiremanagement.modeladmin.SubCategory;
import com.aspiremanagement.repositoryadmin.ProductRepository;
import com.aspiremanagement.repositoryadmin.SubCategoryRepository;

@Service
public class ProductService {

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	SubCategoryRepository subCategoryRepository;

	public List<ProductResponse> fetchAllProducts() {
		List<Product>productList=productRepository.findAll();
		List<ProductResponse>list =new ArrayList<>();
		
		for(int i=0;i<productList.size();i++)
		{
			ProductResponse product=new ProductResponse();
			product.setId(productList.get(i).getId());
			product.setProductName(productList.get(i).getProductName());
			
			Long id=productList.get(i).getSubCategoryId();
			Optional<SubCategory>subCategory=subCategoryRepository.findById(id);
			if(subCategory.isPresent())
			{
				product.setSubCategoryName(subCategory.get().getSubCatogotyName());
			
			}else {
				product.setSubCategoryName("");
			}
			list.add(product);
		}
		
		return list;
	}

	public Optional<Product> getProduct(Long productId) {
		
		return productRepository.findById(productId);
	}

	public Product getProductByName(String productName) {
		
		return productRepository.getByProductName(productName);
	}

	public void saveProduct(Product product) {
		productRepository.save(product);
		
	}

	public void deleteProduct(Long productId) {
		productRepository.deleteById(productId);
		
	}

	public List<ProductResponse> fetchSubCategoryWiseProducts(Long subCategoryId) {
		List<Product>productList=productRepository.fetchSubCategoryWiseProducts(subCategoryId);
		List<ProductResponse>list =new ArrayList<>();
		
		for(int i=0;i<productList.size();i++)
		{
			ProductResponse product=new ProductResponse();
			product.setId(productList.get(i).getId());
			product.setProductName(productList.get(i).getProductName());
			
			Long id=productList.get(i).getSubCategoryId();
			Optional<SubCategory>subCategory=subCategoryRepository.findById(id);
			if(subCategory.isPresent())
			{
				product.setSubCategoryName(subCategory.get().getSubCatogotyName());
			
			}else {
				product.setSubCategoryName("");
			}
			list.add(product);
		}
		
		return list;
		
	}
}
