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


@Entity
public class SubCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String subCatogotyName="";
	@Column(name = "category_id")
	private Long categoryId;
	@CreatedDate
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date updatedDate = new Date();
	private String updatedName="";

	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "category_id", nullable = false, insertable = false, updatable = false)
	private CategoryMaster categoryMaster;

	
	@OneToMany(targetEntity = Product.class, mappedBy = "subCategory", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	List<Product> products;
	
	public SubCategory() {
		super();
	}


	public SubCategory(Long id, String subCatogotyName, Long categoryId, Date updatedDate, String updatedName) {
		super();
		this.id = id;
		this.subCatogotyName = subCatogotyName;
		this.categoryId = categoryId;
		this.updatedDate = updatedDate;
		this.updatedName = updatedName;
	}


	@Override
	public String toString() {
		return "SubCategory [id=" + id + ", subCatogotyName=" + subCatogotyName + ", categoryId=" + categoryId
				+ ", updatedDate=" + updatedDate + ", updatedName=" + updatedName + "]";
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getSubCatogotyName() {
		return subCatogotyName;
	}


	public void setSubCatogotyName(String subCatogotyName) {
		this.subCatogotyName = subCatogotyName;
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
