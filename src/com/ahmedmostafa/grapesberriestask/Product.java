package com.ahmedmostafa.grapesberriestask;

public class Product {
	
	String productDescription;
	int id = 1;
	double price = 0;
	Image image;
	
	public Product(int id,String productDescription, Image image, double price) {
		this.id = id;
		this.productDescription = productDescription;
		this.image = image;
		this.price = price;
	}

	public double getPrice() {
		return price;
	}

	public int getId() {
		return id;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public Image getImage() {
		return image;
	}
	
	
}
