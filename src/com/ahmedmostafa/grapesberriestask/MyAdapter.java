package com.ahmedmostafa.grapesberriestask;

import java.util.ArrayList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

public class MyAdapter extends RecyclerView.Adapter<ViewHolder> {

	ArrayList<Product> products;
	Context context;

	// constructor
	public MyAdapter(Context context, ArrayList<Product> products) {
		this.products = products;
		this.context = context;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

		View view;

		view = LayoutInflater.from(parent.getContext()).inflate(
				R.layout.single_product, parent, false);

		ViewHolder vh = new ViewHolder(view);
		Log.v("First", "done");
		return vh;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		Picasso.with(context).load(products.get(position).getImage().getUrl())
				.into(holder.imgViewProduct);
		holder.tvPrice.setText(products.get(position).getPrice() + " $");
		holder.tvDescription.setText(products.get(position)
				.getProductDescription());
		Log.v("Second", "done");

	}

	@Override
	public int getItemCount() {
		
		return products.size();
	}

}
