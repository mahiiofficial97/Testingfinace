package com.aspiremanagement.springjwtAdminResponse;

import java.util.List;
import java.util.Optional;

import com.aspiremanagement.filtermodeladmin.Colour;
import com.aspiremanagement.filtermodeladmin.ColourResponse;

public class ManageColourJson {

	private String message;
	private String statusCode;
	private List<ColourResponse> fetchAllColours;
	private List<ColourResponse> fetchCategoryWiseAllColours;
	private Optional<Colour> getColour;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public List<ColourResponse> getFetchAllColours() {
		return fetchAllColours;
	}
	public void setFetchAllColours(List<ColourResponse> fetchAllColours) {
		this.fetchAllColours = fetchAllColours;
	}
	
	
	public Optional<Colour> getGetColour() {
		return getColour;
	}
	public void setGetColour(Optional<Colour> getColour) {
		this.getColour = getColour;
	}
	public ManageColourJson() { 
		super();
	}
	public ManageColourJson(String message, String statusCode, List<ColourResponse> fetchAllColours,
			List<ColourResponse> fetchCategoryWiseAllColours, Optional<Colour> getColour) {
		super();
		this.message = message;
		this.statusCode = statusCode;
		this.fetchAllColours = fetchAllColours;
		this.fetchCategoryWiseAllColours = fetchCategoryWiseAllColours;
		this.getColour = getColour;
	}
	public List<ColourResponse> getFetchCategoryWiseAllColours() {
		return fetchCategoryWiseAllColours;
	}
	public void setFetchCategoryWiseAllColours(List<ColourResponse> fetchCategoryWiseAllColours) {
		this.fetchCategoryWiseAllColours = fetchCategoryWiseAllColours;
	}
	@Override
	public String toString() {
		return "ManageColourJson [message=" + message + ", statusCode=" + statusCode + ", fetchAllColours="
				+ fetchAllColours + ", fetchCategoryWiseAllColours=" + fetchCategoryWiseAllColours + ", getColour="
				+ getColour + "]";
	}
	
	
	
	
}
