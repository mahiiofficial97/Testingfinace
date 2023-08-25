package com.aspiremanagement.springjwtAdminResponse;

import java.util.List;
import java.util.Optional;

import com.aspiremanagement.modeladmin.SubCategory;
import com.aspiremanagement.modeladmin.SubCategoryResponse;

public class Manage_SubCategory_Json {

	 	private String message;
	    private String statusCode;
	    private List<SubCategoryResponse> fectchAllSubCategories;
	    private List<SubCategoryResponse> fetchCategoryWiseSubCategory;
	    private Optional<SubCategory> getSubCategory;
	   
		public Manage_SubCategory_Json() {
			super();
		}
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
		public List<SubCategoryResponse> getFectchAllSubCategories() {
			return fectchAllSubCategories;
		}
		public void setFectchAllSubCategories(List<SubCategoryResponse> fectchAllSubCategories) {
			this.fectchAllSubCategories = fectchAllSubCategories;
		}
		public List<SubCategoryResponse> getFetchCategoryWiseSubCategory() {
			return fetchCategoryWiseSubCategory;
		}
		public void setFetchCategoryWiseSubCategory(List<SubCategoryResponse> fetchCategoryWiseSubCategory) {
			this.fetchCategoryWiseSubCategory = fetchCategoryWiseSubCategory;
		}
		@Override
		public String toString() {
			return "Manage_SubCategory_Json [message=" + message + ", statusCode=" + statusCode
					+ ", fectchAllSubCategories=" + fectchAllSubCategories + ", fetchCategoryWiseSubCategory="
					+ fetchCategoryWiseSubCategory + ", getSubCategory=" + getSubCategory + "]";
		}
		public Manage_SubCategory_Json(String message, String statusCode,
				List<SubCategoryResponse> fectchAllSubCategories,
				List<SubCategoryResponse> fetchCategoryWiseSubCategory, Optional<SubCategory> getSubCategory) {
			super();
			this.message = message;
			this.statusCode = statusCode;
			this.fectchAllSubCategories = fectchAllSubCategories;
			this.fetchCategoryWiseSubCategory = fetchCategoryWiseSubCategory;
			this.getSubCategory = getSubCategory;
		}
		public Optional<SubCategory> getGetSubCategory() {
			return getSubCategory;
		}
		public void setGetSubCategory(Optional<SubCategory> getSubCategory) {
			this.getSubCategory = getSubCategory;
		}
		
		
		

		
		
	    
	    
}
