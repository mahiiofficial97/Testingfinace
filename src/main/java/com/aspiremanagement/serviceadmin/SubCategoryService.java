package com.aspiremanagement.serviceadmin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aspiremanagement.modeladmin.CategoryMaster;
import com.aspiremanagement.modeladmin.SubCategory;
import com.aspiremanagement.modeladmin.SubCategoryResponse;
import com.aspiremanagement.repositoryadmin.CategoryMasterRepository;
import com.aspiremanagement.repositoryadmin.SubCategoryRepository;

@Service
public class SubCategoryService {

	@Autowired
	SubCategoryRepository subCategoryRepository;
	
	@Autowired 
	CategoryMasterRepository categoryMasterRepository;

	public SubCategory getCategory(String subCatogotyName) {
		
		return subCategoryRepository.getCategory(subCatogotyName);
	}

	public void saveSubCategory(SubCategory subCategory) {
		subCategoryRepository.save(subCategory);
		
	}

	public List<SubCategoryResponse> fetchAllSubCategories() {
		List<SubCategory> subCategoryList=subCategoryRepository.findAll();
		List<SubCategoryResponse> list= new ArrayList<>();
		
		for(int i=0;i<subCategoryList.size();i++)
		{
			SubCategoryResponse subCategory=new SubCategoryResponse();
			subCategory.setId(subCategoryList.get(i).getId());
			subCategory.setSubCatogotyName(subCategoryList.get(i).getSubCatogotyName());
			Long id=subCategoryList.get(i).getCategoryId();
			Optional<CategoryMaster> categoryMaster=categoryMasterRepository.findById(id);
			if(categoryMaster.isPresent())
			{
				subCategory.setCategoryName(categoryMaster.get().getCategoryName());
				
			}
			else {
				subCategory.setCategoryName("");
			}
			list.add(subCategory);
		}
		
		System.out.println("All List is sssss"+list);
		return list;
	}

public List<SubCategoryResponse> fetchCategoryWiseSubCategory(Long categoryId) {
		
	//List<SubCategory> subCategoryList=subCategoryRepository.fetchCategoryWiseSubCategory(categoryId);
	List<SubCategory> subCategoryList=subCategoryRepository.findAll().stream().filter(a->a.getCategoryId()==categoryId).collect(Collectors.toList());
	List<SubCategoryResponse> list= new ArrayList<>();
	for(int i=0;i<subCategoryList.size();i++)
	{
		SubCategoryResponse subCategory=new SubCategoryResponse();
		subCategory.setId(subCategoryList.get(i).getId());
		subCategory.setSubCatogotyName(subCategoryList.get(i).getSubCatogotyName());
		Long id=subCategoryList.get(i).getCategoryId();
		Optional<CategoryMaster> categoryMaster=categoryMasterRepository.findById(id);
		if(categoryMaster.isPresent())
		{
			subCategory.setCategoryName(categoryMaster.get().getCategoryName());
			
		}
		else {
			subCategory.setCategoryName("");
		}
		list.add(subCategory);
	}
	
	
	return list;
		
	}
	
	
	public Optional<SubCategory> fetchSubCategory(Long subCategoryId) {
		
	
		return subCategoryRepository.findById(subCategoryId);
	}

	public void deteteSubCategory(Long subCategoryId) {
		subCategoryRepository.deleteById(subCategoryId);
		
	}

	

	public Optional<SubCategory> fetchSubCategoryById(Long categoryId) {
		
		return subCategoryRepository.findById(categoryId);
	}

	
}
