package com.aspiremanagement.modeladmin;

public class JsonResponse {

	private String message;
	private String statusCode;
	private String result;

	public JsonResponse() {

	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public JsonResponse(String message, String statusCode) {
		super();
		this.message = message;
		this.statusCode = statusCode;
	}

	public JsonResponse(String message, String statusCode, String result) {
		super();
		this.message = message;
		this.statusCode = statusCode;
		this.result = result;
	}

	@Override
	public String toString() {
		return "JsonResponse [message=" + message + ", statusCode=" + statusCode + ", result=" + result + "]";
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
