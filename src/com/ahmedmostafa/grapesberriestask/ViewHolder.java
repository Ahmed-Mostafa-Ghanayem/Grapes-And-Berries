package com.ahmedmostafa.grapesberriestask;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewHolder extends RecyclerView.ViewHolder {

	public ImageView imgViewProduct;
	public TextView tvPrice, tvDescription;

	// Provide a reference to the views for each data item
	// Complex data items may need more than one view per item, and
	// you provide access to all the views for a data item in a view holder
	public ViewHolder(View view) {
		super(view);
		imgViewProduct = (ImageView) view.findViewById(R.id.img_view_product);
		tvPrice = (TextView) view.findViewById(R.id.tv_price_value);
		tvDescription = (TextView) view.findViewById(R.id.tv_description_value);
	}

}
