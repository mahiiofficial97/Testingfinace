package com.aspiremanagement.modeladmin;

public class SubCategoryResponse {

	private Long id;
	private String subCatogotyName;
	private String categoryName;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSubCatogotyName() {
		return subCatogotyName;
	}
	public void setSubCatogotyName(String subCatogotyName) {
		this.subCatogotyName = subCatogotyName;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public SubCategoryResponse(Long id, String subCatogotyName, String categoryName) {
		super();
		this.id = id;
		this.subCatogotyName = subCatogotyName;
		this.categoryName = categoryName;
	}
	@Override
	public String toString() {
		return "SubCategoryResponse [id=" + id + ", subCatogotyName=" + subCatogotyName + ", categoryName="
				+ categoryName + "]";
	}
	public SubCategoryResponse() {
		super();
	}
	
	
}
