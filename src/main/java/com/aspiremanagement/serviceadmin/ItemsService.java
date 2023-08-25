package com.aspiremanagement.serviceadmin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aspiremanagement.filtermodeladmin.Brands;
import com.aspiremanagement.filtermodeladmin.Colour;
import com.aspiremanagement.filterrepositoryadmin.BrandsRepository;
import com.aspiremanagement.filterrepositoryadmin.ColourRepository;
import com.aspiremanagement.modeladmin.ItemResponse;
import com.aspiremanagement.modeladmin.Items;
import com.aspiremanagement.modeladmin.Product;
import com.aspiremanagement.repositoryadmin.ItemsRepository;
import com.aspiremanagement.repositoryadmin.ProductRepository;

@Service
public class ItemsService {

	@Autowired
	 ItemsRepository itemsRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	ColourRepository colourRepository;
	
	@Autowired
	BrandsRepository brandsRepository;
	
	public void save(Items items) {
		itemsRepository.save(items);
		
	}
	public Optional<Items> getItem(Long itemId) {
		
		return itemsRepository.findById(itemId);
	}
	public void deleteItem(Long itemId) {
		itemsRepository.deleteById(itemId);
		
	}
	public List<ItemResponse> fetchAllItems() {
		List<Items> items=itemsRepository.findAll();
		List<ItemResponse> list=new ArrayList<>();
		
		for(int i=0;i<items.size();i++)
		{
			ItemResponse itemResponse=new ItemResponse();
			itemResponse.setId(items.get(i).getId());
			itemResponse.setName(items.get(i).getName());
			itemResponse.setShortDescription(items.get(i).getShortDescription());
			itemResponse.setItemImages(items.get(i).getItemImages());
			
			Long productId=items.get(i).getProductId();
			Optional<Product> product=productRepository.findById(productId);
			if(product.isPresent())
			{
				itemResponse.setProductName(product.get().getProductName());
			}else {
				itemResponse.setProductName("");
			}
			
			Long colourId=items.get(i).getColourId();
			Optional<Colour> colour=colourRepository.findById(colourId);
			if(colour.isPresent())
			{
				itemResponse.setColourName(colour.get().getColourName());
			}else {
				itemResponse.setColourName("");
			}
			
			Long brandId=items.get(i).getBrandId();
			Optional<Brands> brand=brandsRepository.findById(brandId);
			if(brand.isPresent())
			{
				itemResponse.setBrandName(brand.get().getBrandName());
			}else {
				itemResponse.setBrandName("");
			}
			
			
			list.add(itemResponse);
			
		}
		return list;
	}
	public Items getItemByName(String name, String shortDescription) {
		
		return itemsRepository.getItemByName(name,shortDescription);
	}
	public List<ItemResponse> fetchProductWiseAllItems(Long productId) {
		
		List<Items> items=itemsRepository.findAll().stream().filter(i->i.getProductId()== productId).collect(Collectors.toList());
		List<ItemResponse> list=new ArrayList<>();
		
		for(int i=0;i<items.size();i++)
		{
			ItemResponse itemResponse=new ItemResponse();
			itemResponse.setId(items.get(i).getId());
			itemResponse.setName(items.get(i).getName());
			itemResponse.setShortDescription(items.get(i).getShortDescription());
			itemResponse.setItemImages(items.get(i).getItemImages());
			itemResponse.setQuantity(items.get(i).getQuantity());
			
			Long proId=items.get(i).getProductId();
			Optional<Product> product=productRepository.findById(proId);
			if(product.isPresent())
			{
				itemResponse.setProductName(product.get().getProductName());
			}else {
				itemResponse.setProductName("");
			}
			
			Long colourId=items.get(i).getColourId();
			if(colourId !=null)
			{
			Optional<Colour> colour=colourRepository.findById(colourId);
			if(colour.isPresent())
			{
				itemResponse.setColourName(colour.get().getColourName());
			}else {
				itemResponse.setColourName("");
			}
			}else {
				itemResponse.setColourName("");
			}
			
			Long brandId=items.get(i).getBrandId();
			if(brandId !=null)
			{
			Optional<Brands> brand=brandsRepository.findById(brandId);
			if(brand.isPresent())
			{
				itemResponse.setBrandName(brand.get().getBrandName());
			}else {
				itemResponse.setBrandName("");
			}
			}else {
				itemResponse.setBrandName("");
			}
			
			
			list.add(itemResponse);
			
		}
		return list;
	}
	
}
