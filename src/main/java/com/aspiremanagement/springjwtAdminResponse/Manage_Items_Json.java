package com.aspiremanagement.springjwtAdminResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.aspiremanagement.modeladmin.ItemResponse;
import com.aspiremanagement.modeladmin.Items;

public class Manage_Items_Json {
	private String message;
	private String statusCode;
	private String result;
	private List<ItemResponse> fetchAllItems;
	private List<ItemResponse> getProductWiseItems;
	private Optional<Items> getItem;
	
	HashMap<String, HashMap<String, Long>> getStock;

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

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public List<ItemResponse> getFetchAllItems() {
		return fetchAllItems;
	}

	public void setFetchAllItems(List<ItemResponse> fetchAllItems) {
		this.fetchAllItems = fetchAllItems;
	}

	public Optional<Items> getGetItem() {
		return getItem;
	}

	public void setGetItem(Optional<Items> getItem) {
		this.getItem = getItem;
	}

	
	

	public HashMap<String, HashMap<String, Long>> getGetStock() {
		return getStock;
	}

	public void setGetStock(HashMap<String, HashMap<String, Long>> getStock) {
		this.getStock = getStock;
	}

	public Manage_Items_Json(String message, String statusCode, String result, List<ItemResponse> fetchAllItems,
			List<ItemResponse> getProductWiseItems, Optional<Items> getItem,
			HashMap<String, HashMap<String, Long>> getStock) {
		super();
		this.message = message;
		this.statusCode = statusCode;
		this.result = result;
		this.fetchAllItems = fetchAllItems;
		this.getProductWiseItems = getProductWiseItems;
		this.getItem = getItem;
		this.getStock = getStock;
	}

	@Override
	public String toString() {
		return "Manage_Items_Json [message=" + message + ", statusCode=" + statusCode + ", result=" + result
				+ ", fetchAllItems=" + fetchAllItems + ", getProductWiseItems=" + getProductWiseItems + ", getItem="
				+ getItem + ", getStock=" + getStock + "]";
	}

	public List<ItemResponse> getGetProductWiseItems() {
		return getProductWiseItems;
	}

	public void setGetProductWiseItems(List<ItemResponse> getProductWiseItems) {
		this.getProductWiseItems = getProductWiseItems;
	}

	public Manage_Items_Json() {
		super();
	}

}
