package com.aspiremanagement.filterserviceadmin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aspiremanagement.filtermodeladmin.BrandResponse;
import com.aspiremanagement.filtermodeladmin.Brands;
import com.aspiremanagement.filterrepositoryadmin.BrandsRepository;
import com.aspiremanagement.modeladmin.CategoryMaster;
import com.aspiremanagement.repositoryadmin.CategoryMasterRepository;

@Service
public class BrandsService {

	@Autowired
	BrandsRepository brandsRepository;
	
	@Autowired 
	CategoryMasterRepository categoryMasterRepository;

	public List<BrandResponse> fetchAllBrands() {
		
		List<Brands> brands=brandsRepository.findAll();
		List<BrandResponse> list=new ArrayList<>();
		
		for(int i=0;i<brands.size();i++)
		{
			BrandResponse brandResponse=new BrandResponse();
			brandResponse.setId(brands.get(i).getId());
			brandResponse.setBrandName(brands.get(i).getBrandName());
			
			Long id=brands.get(i).getCategoryId();
			Optional<CategoryMaster> category=categoryMasterRepository.findById(id);
			if(category.isPresent())
			{
				brandResponse.setCategoryName(category.get().getCategoryName());
			}
			else {
				brandResponse.setCategoryName("");
			}
			list.add(brandResponse);
		}
		
		return list;
	}

	public Brands getBrandsByName(String brandName, Long categoryId) {
		
		return brandsRepository.getBrandByName(brandName,categoryId);
	}

	public void saveBrand(Brands brands) {
		brandsRepository.save(brands);
		
	}

	public Optional<Brands> getBrand(Long brandId) {
		return brandsRepository.findById(brandId);
	}

	public void deleteBrand(Long brandId) {
		brandsRepository.deleteById(brandId);
		
	}

	public List<BrandResponse> getAllCategoryWiseBrands(Long categoryId) {
		
		
		List<Brands> brands=brandsRepository.getAllCategoryWiseBrands(categoryId);
List<BrandResponse> list=new ArrayList<>();
		
		for(int i=0;i<brands.size();i++)
		{
			BrandResponse brandResponse=new BrandResponse();
			brandResponse.setId(brands.get(i).getId());
			brandResponse.setBrandName(brands.get(i).getBrandName());
			
			Long id=brands.get(i).getCategoryId();
			Optional<CategoryMaster> category=categoryMasterRepository.findById(id);
			if(category.isPresent())
			{
				brandResponse.setCategoryName(category.get().getCategoryName());
			}
			else {
				brandResponse.setCategoryName("");
			}
			list.add(brandResponse);
		}
		
		return list;
	}
}
