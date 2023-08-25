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

import com.aspiremanagement.modeladmin.CategoryMaster;

@Entity
public class Colour {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String colourName = "";

	@CreatedDate
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date updatedDate = new Date();
	private String updatedName = "";
	private Long categoryId;

	@Override
	public String toString() {
		return "Colour [id=" + id + ", colourName=" + colourName + ", updatedDate=" + updatedDate + ", updatedName="
				+ updatedName + ", categoryId=" + categoryId + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getColourName() {
		return colourName;
	}

	public void setColourName(String colourName) {
		this.colourName = colourName;
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

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Colour(Long id, String colourName, Date updatedDate, String updatedName, Long categoryId) {
		super();
		this.id = id;
		this.colourName = colourName;
		this.updatedDate = updatedDate;
		this.updatedName = updatedName;
		this.categoryId = categoryId;
	}

	public Colour() {
		super();
	}

}
