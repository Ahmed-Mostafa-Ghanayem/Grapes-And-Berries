package com.ahmedmostafa.grapesberriestask;

import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductItemDetails extends Activity {
	
	TextView tvDescription;
	ImageView imgViewProduct;
	
	String url, description;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_item_details);
		
		tvDescription = (TextView) findViewById(R.id.tv_item_description);
		imgViewProduct = (ImageView) findViewById(R.id.img_view_product);
		
		Bundle bundle = getIntent().getExtras();
		url = bundle.getString("url");
		description =bundle.getString("description");
		
		Picasso.with(getApplicationContext()).load(url).into(imgViewProduct);
		tvDescription.setText(description);
	}
}
