package com.aspiremanagement.modeladmin;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class CategoryMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String categoryName = "";
	
	@CreatedDate
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date updatedDate = new Date();
	private String updatedName = "";

	@OneToMany(targetEntity = SubCategory.class, mappedBy = "categoryMaster", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	List<SubCategory> subCategoryList;
	private String categoryImage;
	public CategoryMaster() {
		super();
	}

	public String getCategoryImage() {
		return categoryImage;
	}

	public void setCategoryImage(String categoryImage) {
		this.categoryImage = categoryImage;
	}

	public CategoryMaster(Long id, String categoryName, String categoryImage, Date updatedDate, String updatedName) {
		super();
		this.id = id;
		this.categoryName = categoryName;
		this.categoryImage = categoryImage;
		this.updatedDate = updatedDate;
		this.updatedName = updatedName;
	}

	@Override
	public String toString() {
		return "CategoryMaster [id=" + id + ", categoryName=" + categoryName + ", categoryImage=" + categoryImage
				+ ", updatedDate=" + updatedDate + ", updatedName=" + updatedName + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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
