package com.aspiremanagement.springjwtAdminResponse;

import java.util.List;
import java.util.Optional;

import com.aspiremanagement.modeladmin.Product;
import com.aspiremanagement.modeladmin.ProductResponse;

public class Manage_Products_Json {

	 private String message;
	    private String statusCode;
	    private List<ProductResponse> fetchAllProducts;
	    private Optional<Product> getProduct;
	    private List<ProductResponse> fetchSubCategoryWiseProducts;
	    
		public Manage_Products_Json() {
			super();
		}

		public Manage_Products_Json(String message, String statusCode, List<ProductResponse> fetchAllProducts,
				Optional<Product> getProduct, List<ProductResponse> fetchSubCategoryWiseProducts) {
			super();
			this.message = message;
			this.statusCode = statusCode;
			this.fetchAllProducts = fetchAllProducts;
			this.getProduct = getProduct;
			this.fetchSubCategoryWiseProducts = fetchSubCategoryWiseProducts;
		}

		@Override
		public String toString() {
			return "Manage_Products_Json [message=" + message + ", statusCode=" + statusCode + ", fetchAllProducts="
					+ fetchAllProducts + ", getProduct=" + getProduct + ", fetchSubCategoryWiseProducts="
					+ fetchSubCategoryWiseProducts + "]";
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

		public List<ProductResponse> getFetchAllProducts() {
			return fetchAllProducts;
		}

		public void setFetchAllProducts(List<ProductResponse> fetchAllProducts) {
			this.fetchAllProducts = fetchAllProducts;
		}

		public Optional<Product> getGetProduct() {
			return getProduct;
		}

		public void setGetProduct(Optional<Product> getProduct) {
			this.getProduct = getProduct;
		}

		public List<ProductResponse> getFetchSubCategoryWiseProducts() {
			return fetchSubCategoryWiseProducts;
		}

		public void setFetchSubCategoryWiseProducts(List<ProductResponse> fetchSubCategoryWiseProducts) {
			this.fetchSubCategoryWiseProducts = fetchSubCategoryWiseProducts;
		}
		
		

	    
	    
}
