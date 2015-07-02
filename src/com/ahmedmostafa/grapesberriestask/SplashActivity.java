package com.ahmedmostafa.grapesberriestask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

public class SplashActivity extends Activity implements AnimationListener {

	Animation animFadein, animblink, animbounce, animmove;
	ImageView imgViewBrand, imgViewProducts;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_splash);

		initViews();
		initObjects();

		imgViewProducts.setVisibility(View.INVISIBLE);
		imgViewBrand.startAnimation(animbounce);
	}

	private void initObjects() {
		AppAnimation.initialAnimation(SplashActivity.this);
		animblink = AppAnimation.animblink;
		animFadein = AppAnimation.animFadein;
		animbounce = AppAnimation.animbounce;
		animmove = AppAnimation.animmove;
	}

	private void initViews() {
		imgViewBrand = (ImageView) findViewById(R.id.img_view_brand);
		imgViewProducts = (ImageView) findViewById(R.id.img_view_products);

	}

	@Override
	public void onAnimationEnd(Animation animation) {
		// check for fade in animation
		if (animation == animbounce) {

			imgViewBrand.startAnimation(animmove);

		}

		if (animation == animmove) {

			imgViewProducts.startAnimation(animFadein);
			imgViewProducts.setVisibility(View.VISIBLE);

		}
		if (animation == animFadein) {

			imgViewProducts.startAnimation(animblink);

		}
		if (animation == animblink) {
			try {

				Thread.sleep(1000);
				Intent intent = new Intent(SplashActivity.this,
						MainActivity.class);

				startActivity(intent);

			} catch (Exception e) {
			}

		}

	}

	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
}
