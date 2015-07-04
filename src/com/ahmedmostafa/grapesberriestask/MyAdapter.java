package com.ahmedmostafa.grapesberriestask;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
/*
 * connect RecyclerView with products ArrayList
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

	ArrayList<Product> products;
	Context context;

	String url, description;
	double price;

	// constructor takes context and ArrayList
	public MyAdapter(Context context, ArrayList<Product> products) {
		this.products = products;
		this.context = context;
	}

	/*
	 * ViewHolder class extends RecyclerView.Adapter and implements
	 * OnClickListener interface.
	 * Used to hold views on the custom view shown on RecyclerView 
	 */
	public class ViewHolder extends RecyclerView.ViewHolder implements
			OnClickListener {

		public ImageView imgViewProduct;
		public TextView tvPrice, tvDescription;

		// Provide a reference to the views for each data item
		// Complex data items may need more than one view per item, and
		// you provide access to all the views for a data item in a view holder
		public ViewHolder(View view) {
			super(view);
			imgViewProduct = (ImageView) view
					.findViewById(R.id.img_view_product);
			tvPrice = (TextView) view.findViewById(R.id.tv_price_value);
			tvDescription = (TextView) view
					.findViewById(R.id.tv_description_value);
			view.setOnClickListener(this);

		}
		
		/*
		 * to show another activity with details of the clicked view
		 */
		@Override
		public void onClick(View v) {

			String url = products.get(getAdapterPosition()).getImage().getUrl();
			String description = products.get(getAdapterPosition())
					.getProductDescription();

			Intent intent = new Intent(context, ProductItemDetails.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.putExtra("url", url);
			intent.putExtra("description", description);
			context.startActivity(intent);

		}

	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

		View view;

		view = LayoutInflater.from(parent.getContext()).inflate(
				R.layout.single_product, parent, false);

		ViewHolder vh = new ViewHolder(view);

		return vh;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {

		url = products.get(position).getImage().getUrl();
		price = products.get(position).getPrice();
		description = products.get(position).getProductDescription();

		Picasso.with(context).load(url).into(holder.imgViewProduct);
		holder.tvPrice.setText(price + " $");
		holder.tvDescription.setText(description);

	}

	@Override
	public int getItemCount() {

		return products.size();
	}

}
