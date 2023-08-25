package com.aspiremanagement.modeladmin;

public class ProductResponse {

	Long id;
	String productName;
	String subCategoryName;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getSubCategoryName() {
		return subCategoryName;
	}
	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}
	@Override
	public String toString() {
		return "ProductResponse [id=" + id + ", productName=" + productName + ", subCategoryName=" + subCategoryName
				+ "]";
	}
	public ProductResponse(Long id, String productName, String subCategoryName) {
		super();
		this.id = id;
		this.productName = productName;
		this.subCategoryName = subCategoryName;
	}
	public ProductResponse() {
		super();
	}
	
	
}
