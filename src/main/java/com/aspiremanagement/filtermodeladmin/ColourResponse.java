package com.aspiremanagement.filtermodeladmin;

public class ColourResponse {
	
	private Long id;
	private String colourName="";
	private String categoryName="";
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getColourName() {
		return colourName;
	}
	public void setColourName(String colourName) {
		this.colourName = colourName;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public ColourResponse(Long id, String colourName, String categoryName) {
		super();
		this.id = id;
		this.colourName = colourName;
		this.categoryName = categoryName;
	}
	public ColourResponse() {
		super();
	}
	@Override
	public String toString() {
		return "ColourResponse [id=" + id + ", colourName=" + colourName + ", categoryName=" + categoryName + "]";
	}
	
	

}
