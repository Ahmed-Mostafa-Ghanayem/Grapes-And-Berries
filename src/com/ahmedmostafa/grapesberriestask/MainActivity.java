package com.ahmedmostafa.grapesberriestask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

/*
 * An Application that download json from web service.
 * Then use JSONParser class that extends AsyncTask to parse it.
 * Then show it's contents on a RecyclerView using MyAdapter
 * that extends RecyclerView.Adapter and StaggeredGridLayoutManager.
 * I load 20 results at a time, when scroll is before end by 5
 * the application will load another 20 results automatically.
 * If there is no network connection a footer bar will show up.
 * If you click this bar, the application will try to reload again.
 * 
 */
public class MainActivity extends Activity {

	// views used to show parsed data
	RecyclerView recyclerView;
	ProgressBar progressBar;
	LinearLayout footerLinearLayout, noConnectionLinearLayout;

	// fill RecyclerView
	MyAdapter myAdapter;
	StaggeredGridLayoutManager sglm;
	ArrayList<Product> products;

	// JSONHandler handler;
	JSONParser parser;

	// to check network and internet connection.
	ConnectivityManager cm;
	NetworkInfo ni;

	boolean loading = false;
	int count = 20, from = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// handler = new JSONHandler();
		
		// progressBar to show json download progress
		progressBar = (ProgressBar) findViewById(R.id.progress_bar);
		
		// footerLinearLayout holds the progressBar and noConnectionLayout
		footerLinearLayout = (LinearLayout) findViewById(R.id.footer_linear_layout);
		
		// noConnectionLinearLayout shows up when there is a problem in the connection
		noConnectionLinearLayout = (LinearLayout) findViewById(R.id.no_connection_linear_layout);
		
		// The action when noConnectionLinearLayout pressed
		noConnectionLinearLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				fillProducts();

			}
		});
		
		//recyclerView used to show data in downloaded json
		recyclerView = (RecyclerView) findViewById(R.id.recycler_grid_view);
		
		// StaggeredLayoutManager to manage showing items in recyclerView
		sglm = new StaggeredGridLayoutManager(2,
				StaggeredGridLayoutManager.VERTICAL);
		
		// ArrayList to hold data in downloaded json
		products = new ArrayList<Product>();
		
		
		myAdapter = new MyAdapter(getApplicationContext(), products);

		fillProducts();

		recyclerView.setLayoutManager(sglm);
		recyclerView.setAdapter(myAdapter);

		// used for second solution
		recyclerView.addOnScrollListener(new EndlessOnScrollListener(sglm) {

			@Override
			public void onLoadMore() {

				fillProducts();
			}
		});

		// used for first solution
		// recyclerView.addOnScrollListener(new EndlessOnScrollListener() {
		//
		// @Override
		// public void onScrolledToEnd() {
		// if (!loading) {
		// loading = true;
		// fillProducts();
		// }
		// loading = false;
		// }
		// });

	}

	private void fillProducts() {

		if (isNetworkConnected() && isInternetAvailable()) {
			// handler.fetchJSON(count, from);

			// while (!handler.parsingComplete);

			// for (Product p : handler.productsArrayList) {
			// Log.v("URL", p.getImage().getUrl());
			// products.add(p);
			// }

			parser = new JSONParser();
			parser.execute(count, from);

			while (!parser.parsingComplete)
				;

			for (Product p : parser.productsArrayList) {
				Log.v("URL", p.getImage().getUrl());
				products.add(p);
			}

			from += 20;

			myAdapter.notifyDataSetChanged();
		}

	}

	private boolean isNetworkConnected() {
		cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		ni = cm.getActiveNetworkInfo();

		if (ni != null && ni.isConnectedOrConnecting()) {
			return true;
		}

		showNoConnection();
		return false;
	}

	private boolean isInternetAvailable() {

		try {
			Process p1 = Runtime.getRuntime().exec("ping -c 1 www.google.com");
			int returnVal = p1.waitFor();
			boolean reachable = (returnVal == 0);
			return reachable;
		} catch (Exception e) {
			e.printStackTrace();
		}

		showNoConnection();
		return false;

	}

	private void showNoConnection() {
		if (footerLinearLayout.getVisibility() == View.GONE) {
			footerLinearLayout.setVisibility(View.VISIBLE);
			progressBar.setVisibility(View.GONE);
			noConnectionLinearLayout.setVisibility(View.VISIBLE);
		} else {
			noConnectionLinearLayout.setVisibility(View.VISIBLE);
			progressBar.setVisibility(View.GONE);
		}
	}

	private void showProgress() {

		if (footerLinearLayout.getVisibility() == View.GONE) {
			footerLinearLayout.setVisibility(View.VISIBLE);
			progressBar.setVisibility(View.VISIBLE);
			noConnectionLinearLayout.setVisibility(View.GONE);
		} else {
			progressBar.setVisibility(View.VISIBLE);
			noConnectionLinearLayout.setVisibility(View.GONE);
		}

	}

	private void hideFooter() {
		footerLinearLayout.setVisibility(View.GONE);

	}

	private class JSONParser extends AsyncTask<Integer, Void, Void> {

		public volatile boolean parsingComplete = false;

		public ArrayList<Product> productsArrayList;

		final String ID = "id";
		final String PRODUCT_DESCRIPTION = "productDescription";
		final String IMAGE = "image";
		final String WIDTH = "width";
		final String HEIGHT = "height";
		final String URL = "url";
		final String PRICE = "price";

		int count, from;

		@Override
		protected void onPreExecute() {

			showProgress();
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Integer... integers) {

			count = integers[0];
			from = integers[1];

			parsingComplete = false;

			final String urlString = "http://grapesnberries.getsandbox.com/products?count="
					+ count + "&from=" + from;

			try {
				URL url = new URL(urlString);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setReadTimeout(10000);
				conn.setConnectTimeout(10000);
				conn.setRequestMethod("GET");
				conn.setDoInput(true);
				// Starts the query
				conn.connect();
				InputStream stream = conn.getInputStream();
				String data = convertStreamToString(stream);
				readAndParseJSON(data);
				stream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

			hideFooter();
			super.onPostExecute(result);
		}

		private String convertStreamToString(InputStream stream) {

			java.util.Scanner s = new java.util.Scanner(stream)
					.useDelimiter("\\A");
			return s.hasNext() ? s.next() : "";
		}

		private void readAndParseJSON(String data) {

			try {
				JSONArray result = new JSONArray(data);
				productsArrayList = new ArrayList<Product>();
				int id;
				String productDescription, url;
				double width, height, price;
				for (int i = 0; i < result.length(); i++) {
					JSONObject product = result.getJSONObject(i);
					id = product.getInt(ID);
					productDescription = product.getString(PRODUCT_DESCRIPTION);
					price = product.getDouble(PRICE);
					JSONObject image = product.getJSONObject(IMAGE);
					url = image.getString(URL);
					width = image.getDouble(WIDTH);
					height = image.getDouble(HEIGHT);

					productsArrayList.add(new Product(id, productDescription,
							new Image(width, height, url), price));

				}
				parsingComplete = true;

			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
	}
}
