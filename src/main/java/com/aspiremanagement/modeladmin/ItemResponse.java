package com.aspiremanagement.modeladmin;

import java.util.List;

public class ItemResponse {
	
	private Long id;
	private String name="";
	private String shortDescription="";
	
	private String productName="";
	private String brandName="";
	private String colourName="";
	private Long quantity;
	private List<String> itemImages;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getColourName() {
		return colourName;
	}
	public void setColourName(String colourName) {
		this.colourName = colourName;
	}
	
	public ItemResponse() {
		super();
	}
	
	
	public ItemResponse(Long id, String name, String shortDescription, String productName, String brandName,
			String colourName, Long quantity, List<String> itemImages) {
		super();
		this.id = id;
		this.name = name;
		this.shortDescription = shortDescription;
		this.productName = productName;
		this.brandName = brandName;
		this.colourName = colourName;
		this.quantity = quantity;
		this.itemImages = itemImages;
	}
	@Override
	public String toString() {
		return "ItemResponse [id=" + id + ", name=" + name + ", shortDescription=" + shortDescription + ", productName="
				+ productName + ", brandName=" + brandName + ", colourName=" + colourName + ", quantity=" + quantity
				+ ", itemImages=" + itemImages + "]";
	}
	public Long getQuantity() {
		return quantity;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	public List<String> getItemImages() {
		return itemImages;
	}
	public void setItemImages(List<String> itemImages) {
		this.itemImages = itemImages;
	}
	
	
	

}
