package com.aspiremanagement.modeladmin;

import org.springframework.web.multipart.MultipartFile;

public class AdminResponse {

	private Long id;
	private String firstName = "";
	private String lastName = "";
	private String email = "";
	private String phone = "";
	
	private String buisness = "";
	private String city = "";
	private String role = Constants.ADMIN.constant;
	private String userStatus = Constants.ACTIVE.constant;
	private String profileImage ;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getBuisness() {
		return buisness;
	}
	public void setBuisness(String buisness) {
		this.buisness = buisness;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}
	
	public AdminResponse() {
		super();
	}
	@Override
	public String toString() {
		return "AdminResponse [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", phone=" + phone + ", buisness=" + buisness + ", city=" + city + ", role=" + role + ", userStatus="
				+ userStatus + ", profileImage=" + profileImage + "]";
	}
	public AdminResponse(Long id, String firstName, String lastName, String email, String phone, String buisness,
			String city, String role, String userStatus, String profileImage) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.buisness = buisness;
		this.city = city;
		this.role = role;
		this.userStatus = userStatus;
		this.profileImage = profileImage;
	}
	public String getProfileImage() {
		return profileImage;
	}
	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}
	
	
	
}
