package com.ahmedmostafa.grapesberriestask;
import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;

public class AppAnimation {

	public static Animation animFadein, animblink, animbounce,
			animmove;

	public static void initialAnimation(Context context) {

		// load the animation
		animFadein = AnimationUtils.loadAnimation(
				context.getApplicationContext(), R.anim.fade_in);

		animblink = AnimationUtils.loadAnimation(
				context.getApplicationContext(), R.anim.blink);
		animbounce = AnimationUtils.loadAnimation(
				context.getApplicationContext(), R.anim.bounce);
		animmove = AnimationUtils.loadAnimation(
				context.getApplicationContext(), R.anim.move);
		// set animation listener
		animFadein.setAnimationListener((AnimationListener) context);
		animblink.setAnimationListener((AnimationListener) context);
		animbounce.setAnimationListener((AnimationListener) context);
		animmove.setAnimationListener((AnimationListener) context);

	}

}
