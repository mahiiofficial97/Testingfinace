package com.aspiremanagement.filterserviceadmin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aspiremanagement.filtermodeladmin.Colour;
import com.aspiremanagement.filtermodeladmin.ColourResponse;
import com.aspiremanagement.filterrepositoryadmin.ColourRepository;
import com.aspiremanagement.modeladmin.CategoryMaster;
import com.aspiremanagement.repositoryadmin.CategoryMasterRepository;

@Service
public class ColourService {

	@Autowired
	ColourRepository colourRepository;
	
	@Autowired 
	CategoryMasterRepository categoryMasterRepository;

	public List<ColourResponse> fetchAllColours() {
		
		List<Colour> colour= colourRepository.findAll();
		List<ColourResponse> list=new ArrayList<>();
		
		for (int i = 0; i < colour.size(); i++) {
			ColourResponse colourResponse=new ColourResponse();
			colourResponse.setColourName(colour.get(i).getColourName());
			Long id=colour.get(i).getId();
			Optional<CategoryMaster> category=categoryMasterRepository.findById(id);
			if(category.isPresent())
			{
				colourResponse.setCategoryName(category.get().getCategoryName());
			}
			else {
				colourResponse.setCategoryName("");
			}
			list.add(colourResponse);
		}
		return list;
	}

	public List<ColourResponse> fetchAllCategoryWiseColours(String categoryId) {
		List<Colour> colour= colourRepository.findAllColoursBycategoryId(categoryId);
		List<ColourResponse> list=new ArrayList<>();
		
		for (int i = 0; i < colour.size(); i++) { 
			ColourResponse colourResponse=new ColourResponse();
			colourResponse.setColourName(colour.get(i).getColourName());
			Long id=colour.get(i).getId();
			Optional<CategoryMaster> category=categoryMasterRepository.findById(id);
			if(category.isPresent())
			{
				colourResponse.setCategoryName(category.get().getCategoryName());
			}
			else {
				colourResponse.setCategoryName("");
			}
			list.add(colourResponse);
		}
		return list;
	}

	public Colour getColourByNameAndCategoryId(String colourName, Long categoryId) {
		
		return colourRepository.fetchColour(colourName,categoryId);
	}

	public void saveColour(Colour colour) {
		
		colourRepository.save(colour);
	}

	public Optional<Colour> getColour(Long colourId) {
		
		return colourRepository.findById(colourId);
	}

	public void deleteColour(Long colourId) {
		colourRepository.deleteById(colourId);
		
	}

	
	
}
