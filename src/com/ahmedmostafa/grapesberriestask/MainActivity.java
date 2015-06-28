package com.ahmedmostafa.grapesberriestask;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

public class MainActivity extends Activity {

	RecyclerView recyclerView;
	ProgressBar progressBar;

	MyAdapter myAdapter;
	StaggeredGridLayoutManager sglm;
	ArrayList<Product> products;

	JSONHandler handler;

	boolean loading = false;
	int count = 20, from = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		handler = new JSONHandler();

		recyclerView = (RecyclerView) findViewById(R.id.recyclerGridView);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		sglm = new StaggeredGridLayoutManager(2,
				StaggeredGridLayoutManager.VERTICAL);

		products = new ArrayList<Product>();

		myAdapter = new MyAdapter(getApplicationContext(), products);

		fillProducts();

		recyclerView.setLayoutManager(sglm);
		recyclerView.setAdapter(myAdapter);
		// recyclerView.addOnScrollListener(new EndlessOnScrollListener(sglm) {
		//
		// @Override
		// public void onLoadMore() {
		//
		// fillProducts();
		// }
		// });

		recyclerView.addOnScrollListener(new EndlessOnScrollListener() {

			@Override
			public void onScrolledToEnd() {
				if (!loading) {
					loading = true;
					fillProducts();
				}
				loading = false;
			}
		});
	}

	private void fillProducts() {
		progressBar.setVisibility(View.VISIBLE);
		handler.fetchJSON(count, from);

		while (!handler.parsingComplete)
			;

		for (Product p : handler.productsArrayList) {
			Log.v("URL", p.getImage().getUrl());
			products.add(p);
		}

		from += 20;
		count += 20;
		myAdapter.notifyDataSetChanged();
		progressBar.setVisibility(View.GONE);
	}

}
