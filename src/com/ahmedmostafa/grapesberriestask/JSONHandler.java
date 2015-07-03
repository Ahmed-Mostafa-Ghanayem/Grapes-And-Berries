package com.ahmedmostafa.grapesberriestask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*
 * Class to handle json file downloaded from the web service.
 * It is an option.
 * I actually used MainActivity.JSONParser class which extends AsyncTask to do this Job.
 */

public class JSONHandler {
	
	// to check if parsing is done
	public volatile boolean parsingComplete = false;
	
	// to save data from json
	public ArrayList<Product> productsArrayList;
	
	// save key names used in json
	final String ID = "id";
	final String PRODUCT_DESCRIPTION = "productDescription";
	final String IMAGE = "image";
	final String WIDTH = "width";
	final String HEIGHT = "height";
	final String URL = "url";
	final String PRICE = "price";
	
	/*
	 * to download json
	 */
	public void fetchJSON(int count, int from) {

		parsingComplete = false;

		final String urlString = "http://grapesnberries.getsandbox.com/products?count="
				+ count + "&from=" + from;
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					URL url = new URL(urlString);
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.setReadTimeout(10000);
					conn.setConnectTimeout(15000);
					conn.setRequestMethod("GET");
					conn.setDoInput(true);
					// Starts the query
					conn.connect();
					InputStream stream = conn.getInputStream();
					// convert json to String
					String data = convertStreamToString(stream);
					
					// read String json and save it in ArrayList
					readAndParseJSON(data);
					stream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});

		thread.start();
	}
	/*
	 * read json String and save it's content in ArrayList
	 */
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
	
	/*
	 * Convert json to String
	 */
	private static String convertStreamToString(InputStream stream) {

		java.util.Scanner s = new java.util.Scanner(stream).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}

}
