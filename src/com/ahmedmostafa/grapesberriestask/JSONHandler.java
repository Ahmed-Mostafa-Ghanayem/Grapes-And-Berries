package com.ahmedmostafa.grapesberriestask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONHandler {

	public volatile boolean parsingComplete = false;

	public ArrayList<Product> productsArrayList;

	final String ID = "id";
	final String PRODUCT_DESCRIPTION = "productDescription";
	final String IMAGE = "image";
	final String WIDTH = "width";
	final String HEIGHT = "height";
	final String URL = "url";
	final String PRICE = "price";

	public void fetchJSON(int count, int from) {

		parsingComplete = false;

		final String urlString = "http://grapesnberries.getsandbox.com/products?count="
				+ count + "&from=" + from;
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					// Log.w("FIRST", "a");
					URL url = new URL(urlString);
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					// Log.w("SECOND", "b");
					conn.setReadTimeout(10000);
					conn.setConnectTimeout(15000);
					// Log.w("THIRD", "c");
					conn.setRequestMethod("GET");
					conn.setDoInput(true);
					// Starts the query
					conn.connect();
					InputStream stream = conn.getInputStream();
					// Log.w("FOURTH", "d");
					String data = convertStreamToString(stream);
					// Log.w("DATA", data);
					readAndParseJSON(data);
					stream.close();
					// Log.w("FIFTH", "e");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});

		thread.start();
	}

	private void readAndParseJSON(String data) {

		try {
			JSONArray result = new JSONArray(data);
			productsArrayList = new ArrayList<Product>();
			int id ;
			String productDescription, url;
			double width, height, price;
			for (int i = 0; i < result.length(); i++) {
				JSONObject product = result.getJSONObject(i);
				id = product.getInt(ID);
				productDescription = product
						.getString(PRODUCT_DESCRIPTION);
				JSONObject image = product.getJSONObject(IMAGE);
				url = image.getString(URL);
				width = image.getDouble(WIDTH);
				height = image.getDouble(HEIGHT);
				price = product.getDouble(PRICE);

				productsArrayList.add(new Product(id, productDescription,
						new Image(width, height, url), price));

			}
			parsingComplete = true;

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	private static String convertStreamToString(InputStream stream) {

		java.util.Scanner s = new java.util.Scanner(stream).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}

}
