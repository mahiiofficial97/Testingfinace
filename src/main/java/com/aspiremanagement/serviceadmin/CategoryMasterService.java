package com.aspiremanagement.serviceadmin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aspiremanagement.modeladmin.CategoryMaster;
import com.aspiremanagement.repositoryadmin.CategoryMasterRepository;

@Service
public class CategoryMasterService {

	@Autowired
	CategoryMasterRepository categoryMasterRepository;

	public List<CategoryMaster> fetchAllCategories() {
		
		return categoryMasterRepository.findAll();
	}

	public void savecategoryMaster(CategoryMaster categoryMaster) {
		categoryMasterRepository.save(categoryMaster);
		
	}

	public CategoryMaster findByCategoryName(String categoryName) {
		
		return categoryMasterRepository.findByCategoryNAme(categoryName);
	}

	public Optional<CategoryMaster> findById(Long categoryId) {
		
		return categoryMasterRepository.findById(categoryId);
	}

	public void deleteUser(Long categoryId) {
		categoryMasterRepository.deleteById(categoryId);
		
	}
}
