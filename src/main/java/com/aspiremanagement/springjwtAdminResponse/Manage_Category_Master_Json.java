package com.aspiremanagement.springjwtAdminResponse;

import java.util.List;
import java.util.Optional;

import com.aspiremanagement.modeladmin.CategoryMaster;

public class Manage_Category_Master_Json {

	 private String message;
	    private String statusCode;
	    private Optional<CategoryMaster>getCategory;
	    private List<CategoryMaster> getAllCategory;
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		
		public Optional<CategoryMaster> getGetCategory() {
			return getCategory;
		}
		public void setGetCategory(Optional<CategoryMaster> getCategory) {
			this.getCategory = getCategory;
		}
		public List<CategoryMaster> getGetAllCategory() {
			return getAllCategory;
		}
		public void setGetAllCategory(List<CategoryMaster> getAllCategory) {
			this.getAllCategory = getAllCategory;
		}
		public Manage_Category_Master_Json(String message, String statusCode, Optional<CategoryMaster> getCategory,
				List<CategoryMaster> getAllCategory) {
			super();
			this.message = message;
			this.statusCode = statusCode;
			this.getCategory = getCategory;
			this.getAllCategory = getAllCategory;
		}
		public String getStatusCode() {
			return statusCode;
		}
		
		public Manage_Category_Master_Json() {
			super();
		}
		public void setStatusCode(String statusCode) {
			this.statusCode = statusCode;
		}
		@Override
		public String toString() {
			return "Manage_Category_Master_Json [message=" + message + ", statusCode=" + statusCode + ", getCategory="
					+ getCategory + ", getAllCategory=" + getAllCategory + "]";
		}
		
	    
	    
	    
}
