package com.bank.enumm;

public enum Constants {
	
	ADMIN("Admin"),
	ACTIVE("Active"),
	INACTIVE("Inactive"),
	FIRST_NAME("first_name"),
	SESSION_EXPIRED_MESSAGE("Your session has expired. Please login to pick up where you left off."),
	INVALID_TOKEN_MESSAGE("Invalid Access Token !!!"),
	
	 TRUE("1"),
	 FALSE("0"),
	 INVALID_CREADINTIALS("Invalid Credintials"), 
	 AUTHORIZATION("adminJwtToken"),
	 COMPANY_NAME("jspm school of group"),
	 CUSTOMER_SERVICE_MOB("+91 8600006122"),
	 CUSTOMER_SERVICE_EMAIL("mahiiofficial97@gmail.com"),
	//Status codes...
	 CONTINUE("100"),
	 SWITCHING_PROTOCOLS("101"),
	 SUCCESS("200"),
	 INVALID_TOKEN("401"),
	 SESSION_EXPIRED("440"),
	 CREATED("201"),
	 ALREADY_EXIST("409"),
	 BAD_REQUEST("400"),
	 NOT_FOUND("404"),
	 UNAUTHORIZED("401"),
	 UPLOADCATEGORYIMAGEDIRECTORY(System.getProperty("user.dir") + "/uploads/admin/images/categoryImages/"),
	 UPLOADITEMSIMAGEDIRECTORY(System.getProperty("user.dir") + "/uploads/admin/images/ItemsImages/"),
	 UPLOADPROFILEIMAGEDIRECTORY(System.getProperty("user.dir") + "/uploads/admin/adminProfileimage/");
	
	
	
	
	
	 final public String Constant;
	 
	 
	 
	 
	 private Constants(String Constant)
	 {
		this.Constant=Constant;
	 }
	

}
