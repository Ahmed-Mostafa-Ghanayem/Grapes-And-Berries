package com.ahmedmostafa.grapesberriestask;

/*
 * Class to save data of image objects parsed from json file 
 */

public class Image {

	String url;
	double width = 0, height = 0;
	public Image(double width, double height, String url) {
		this.width = width;
		this.height = height;
		this.url = url;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	public String getUrl() {
		return url;
	}
	
}
