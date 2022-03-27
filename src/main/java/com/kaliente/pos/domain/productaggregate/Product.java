package com.kaliente.pos.domain.productaggregate;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.kaliente.pos.domain.seedwork.AggregateRoot;
import com.kaliente.pos.domain.seedwork.BaseEntity;

@Entity(name="products")
@Table
public class Product extends BaseEntity implements AggregateRoot {
	
	@Column(unique=true)
	private String title;
	@Column
	private String description;
	@Column
	private double price;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="catalogue_id", nullable = true)
	private ProductCatalogue catalogue;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public ProductCatalogue getCatalogue() {
		return catalogue;
	}

	public void setCatalogue(ProductCatalogue catalogue) {
		this.catalogue = catalogue;
	}

	
}