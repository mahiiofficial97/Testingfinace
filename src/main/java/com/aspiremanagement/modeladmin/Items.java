package com.aspiremanagement.modeladmin;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Items {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name="";
	private String shortDescription="";
	@Column(name = "productId")
		private Long productId;
	private Long brandId;
	private Long colourId;
	private Long quantity;
	@ElementCollection
	private List<String> itemImages;
	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "productId", nullable = false, insertable = false, updatable = false)
	private Product product;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	
	public Items() {
		super();
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getBrandId() {
		return brandId;
	}
	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}
	public Long getColourId() {
		return colourId;
	}
	public void setColourId(Long colourId) {
		this.colourId = colourId;
	}
	public List<String> getItemImages() {
		return itemImages;
	}
	public void setItemImages(List<String> itemImages) {
		this.itemImages = itemImages;
	}
	public Long getQuantity() {
		return quantity;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	@Override
	public String toString() {
		return "Items [id=" + id + ", name=" + name + ", shortDescription=" + shortDescription + ", productId="
				+ productId + ", brandId=" + brandId + ", colourId=" + colourId + ", quantity=" + quantity
				+ ", itemImages=" + itemImages + "]";
	}
	public Items(Long id, String name, String shortDescription, Long productId, Long brandId, Long colourId,
			Long quantity, List<String> itemImages) {
		super();
		this.id = id;
		this.name = name;
		this.shortDescription = shortDescription;
		this.productId = productId;
		this.brandId = brandId;
		this.colourId = colourId;
		this.quantity = quantity;
		this.itemImages = itemImages;
	}
	
	
	
	
	
	
	
	
	
}
