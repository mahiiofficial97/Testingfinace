package com.aspiremanagement.filtermodeladmin;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import com.aspiremanagement.modeladmin.Product;

@Entity
public class Brands {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String brandName = "";

	private Long categoryId;
	@CreatedDate
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date updatedDate = new Date();
	private String updatedName = "";

	public Brands() {
		super();
	}

	@Override
	public String toString() {
		return "Brands [id=" + id + ", brandName=" + brandName + ", categoryId=" + categoryId + ", updatedDate="
				+ updatedDate + ", updatedName=" + updatedName + "]";
	}

	public Brands(Long id, String brandName, Long categoryId, Date updatedDate, String updatedName) {
		super();
		this.id = id;
		this.brandName = brandName;
		this.categoryId = categoryId;
		this.updatedDate = updatedDate;
		this.updatedName = updatedName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedName() {
		return updatedName;
	}

	public void setUpdatedName(String updatedName) {
		this.updatedName = updatedName;
	}

}
