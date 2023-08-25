package com.aspiremanagement.filtermodeladmin;


public class BrandResponse {

	private Long id;
	private String brandName="";
	
	private String categoryName="";

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public BrandResponse(Long id, String brandName, String categoryName) {
		super();
		this.id = id;
		this.brandName = brandName;
		this.categoryName = categoryName;
	}

	public BrandResponse() {
		super();
	}

	@Override
	public String toString() {
		return "BrandResponse [id=" + id + ", brandName=" + brandName + ", categoryName=" + categoryName + "]";
	}
	
	
	
}
