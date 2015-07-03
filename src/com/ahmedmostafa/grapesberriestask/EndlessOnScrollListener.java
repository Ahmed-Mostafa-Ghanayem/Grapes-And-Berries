package com.ahmedmostafa.grapesberriestask;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.StaggeredGridLayoutManager;

/*
 * Abstract class extends OnScrollListener of RecyclerView. It checks the
 * scroll state and position of the RecyclerView elements. it provides
 * abstract method onLoadMore to override when a new object is created.
 * onLoadMore method implementation specifies the action to be done when the
 * scroll state is close to the end position.
 */

public abstract class EndlessOnScrollListener extends OnScrollListener {

	public static String TAG = EndlessOnScrollListener.class.getSimpleName();

	// int variables to calculate the position of last item
	private int previousTotal = 0;

	private int visibleThreshold = 2;

	private int visibleItemCount, totalItemCount;

	private int firstVisibleItem[];

	private boolean loading = true;

	private StaggeredGridLayoutManager sglm;

	public EndlessOnScrollListener(StaggeredGridLayoutManager sglm) {
		this.sglm = sglm;
	}

	public EndlessOnScrollListener() {

	}

	@Override
	public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
		super.onScrolled(recyclerView, dx, dy);

		// first solution (simple)

		// if (!recyclerView.canScrollVertically(1)) {
		// onScrolledToEnd();
		// }
		// end of first solution

		// second solution
		visibleItemCount = recyclerView.getChildCount();
		totalItemCount = sglm.getItemCount();
		firstVisibleItem = sglm.findFirstVisibleItemPositions(null);

		if (loading) {
			if (totalItemCount > previousTotal) {
				loading = false;
				previousTotal = totalItemCount;
			}
		}
		if (!loading
				&& (totalItemCount - visibleItemCount) <= (firstVisibleItem[0] + visibleThreshold)) {

			onLoadMore();

			loading = true;
		}
		// End second solution
	}

	// used by first solution
	// public abstract void onScrolledToEnd();

	// used by second solution
	public abstract void onLoadMore();

}
