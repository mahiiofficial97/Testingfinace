package com.aspiremanagement.modeladmin;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import com.aspiremanagement.filtermodeladmin.Brands;

@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String productName="";
	@Column(name = "subcategory_id")
	private Long subCategoryId;
	@CreatedDate
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date updatedDate = new Date();
	private String updatedName="";
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "subcategory_id", nullable = false, insertable = false, updatable = false)
	private SubCategory subCategory;
	
	@OneToMany(targetEntity = Items.class, mappedBy="product", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	List<Items> items;
	
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Long getSubCategoryId() {
		return subCategoryId;
	}
	public void setSubCategoryId(Long subCategoryId) {
		this.subCategoryId = subCategoryId;
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
	@Override
	public String toString() {
		return "Product [id=" + id + ", productName=" + productName + ", subCategoryId=" + subCategoryId
				+ ", updatedDate=" + updatedDate + ", updatedName=" + updatedName + "]";
	}
	public Product() {
		super();
	}
	public Product(Long id, String productName, Long subCategoryId, Date updatedDate, String updatedName) {
		super();
		this.id = id;
		this.productName = productName;
		this.subCategoryId = subCategoryId;
		this.updatedDate = updatedDate;
		this.updatedName = updatedName;
	}
	
	
	
}
