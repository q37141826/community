package com.qixiu.intelligentcommunity.widget;

import android.widget.AbsListView;

import com.bumptech.glide.RequestManager;

public class PauseOnScrollListener implements AbsListView.OnScrollListener {

	private final boolean pauseOnScroll;
    private final boolean pauseOnFling;
	private final AbsListView.OnScrollListener externalListener;
	private RequestManager requestManager;

	/**
	 * Constructor
	 *
	 * @param
	 * @param pauseOnScroll during touch scrolling
	 * @param pauseOnFling  during fling
	 */
	public PauseOnScrollListener(RequestManager requestManager, boolean pauseOnScroll, boolean pauseOnFling) {
		this(requestManager, pauseOnScroll, pauseOnFling, null);
	}


	public PauseOnScrollListener(RequestManager requestManager, boolean pauseOnScroll, boolean pauseOnFling, AbsListView.OnScrollListener customListener) {
		this.requestManager = requestManager;
		this.pauseOnScroll = pauseOnScroll;
		this.pauseOnFling = pauseOnFling;
		externalListener = customListener;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		switch (scrollState) {
			case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
				requestManager.resumeRequests();
				break;
			case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
				if (pauseOnScroll) {
					requestManager.pauseRequests();
				}
				break;
			case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
				if (pauseOnFling) {
					requestManager.pauseRequests();
				}
				break;
		}
		if (externalListener != null) {
			externalListener.onScrollStateChanged(view, scrollState);
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		if (externalListener != null) {
			externalListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
		}
	}
}