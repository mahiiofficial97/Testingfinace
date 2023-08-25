package com.aspiremanagement.springjwtAdminResponse;

import java.util.List;
import java.util.Optional;

import com.aspiremanagement.filtermodeladmin.BrandResponse;
import com.aspiremanagement.filtermodeladmin.Brands;

public class Manage_Brands_Json {

	 private String message;
	    private String statusCode;
	    private List<BrandResponse> fetchAllBrands;
	    private Optional<Brands> getBrand;
	    private List<BrandResponse> getAllCategoryWiseBrands;
		
	    
		
		public Manage_Brands_Json() {
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



		


		public Optional<Brands> getGetBrand() {
			return getBrand;
		}



		public void setGetBrand(Optional<Brands> getBrand) {
			this.getBrand = getBrand;
		}



		public List<BrandResponse> getGetAllCategoryWiseBrands() {
			return getAllCategoryWiseBrands;
		}



		public void setGetAllCategoryWiseBrands(List<BrandResponse> getAllCategoryWiseBrands) {
			this.getAllCategoryWiseBrands = getAllCategoryWiseBrands;
		}



		public List<BrandResponse> getFetchAllBrands() {
			return fetchAllBrands;
		}



		public void setFetchAllBrands(List<BrandResponse> fetchAllBrands) {
			this.fetchAllBrands = fetchAllBrands;
		}



		public Manage_Brands_Json(String message, String statusCode, List<BrandResponse> fetchAllBrands,
				Optional<Brands> getBrand, List<BrandResponse> getAllCategoryWiseBrands) {
			super();
			this.message = message;
			this.statusCode = statusCode;
			this.fetchAllBrands = fetchAllBrands;
			this.getBrand = getBrand;
			this.getAllCategoryWiseBrands = getAllCategoryWiseBrands;
		}



		@Override
		public String toString() {
			return "Manage_Brands_Json [message=" + message + ", statusCode=" + statusCode + ", fetchAllBrands="
					+ fetchAllBrands + ", getBrand=" + getBrand + ", getAllCategoryWiseBrands="
					+ getAllCategoryWiseBrands + "]";
		}



		



		


		
		
	    
}
