package com.aspiremanagement.modeladmin;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Admin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String firstName = "";
	private String lastName = "";
	private String email = "";
	private String phone = "";
	private String password = "";
	private String buisness = "";
	private String city = "";
	private String role = Constants.ADMIN.constant;
	private String userStatus = Constants.ACTIVE.constant;
	@Column(name = "reset_password_token")
	private String resetPasswordToken;

	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createdOn = new Date();

	private String profileImage = "";

	public Admin() {
		super();
	}

	@Override
	public String toString() {
		return "Admin [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", phone=" + phone + ", password=" + password + ", buisness=" + buisness + ", city=" + city
				+ ", role=" + role + ", userStatus=" + userStatus + ", resetPasswordToken=" + resetPasswordToken
				+ ", createdOn=" + createdOn + ", profileImage=" + profileImage + "]";
	}

	public Admin(Long id, String firstName, String lastName, String email, String phone, String password,
			String buisness, String city, String role, String userStatus, String resetPasswordToken, Date createdOn,
			String profileImage) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.password = password;
		this.buisness = buisness;
		this.city = city;
		this.role = role;
		this.userStatus = userStatus;
		this.resetPasswordToken = resetPasswordToken;
		this.createdOn = createdOn;
		this.profileImage = profileImage;
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

	public String getResetPasswordToken() {
		return resetPasswordToken;
	}

	public void setResetPasswordToken(String resetPasswordToken) {
		this.resetPasswordToken = resetPasswordToken;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

}
