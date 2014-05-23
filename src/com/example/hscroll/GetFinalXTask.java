package com.example.hscroll;

import java.util.Arrays;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.HorizontalScrollView;

public class GetFinalXTask extends AsyncTask<Object, Void, Integer> {

	// it works up to 10 ms (tested on this particular device), but considering
	// slower device (?need confirmation),
	// and UX that inserting a small interval between user and UI action makes
	// it looks smooth (!but here the interval is not constant since the
	// AsyncTask runs different times, this could be beneficial as the more
	// determined scroll (i.e sweep to final location with finger) will settle
	// faster and less determined scroll (i.e fling) will settle slower).
	private int mDelay = 30;

	private static final String TAG = "hscroll GetFinalXTask";
	Integer oldX;
	Integer newX;
	int testCount = 0;
	Boolean isScrollStop = false;

	 private ListHorizontalScrollViewAdapter LHSVAdapter = ListHorizontalScrollViewAdapter.getInstance();
	// private ViewHolder viewTag;

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		// viewTag=(ViewHolder) mainActivity.touchedViewHolder.hsv.getTag();
		Log.i(TAG, "onPreExecute");
	}

	@Override
	protected Integer doInBackground(Object... params) {
		Integer position = (Integer) params[0];
		HorizontalScrollView touchedView = (HorizontalScrollView) params[1];
//		ListHorizontalScrollViewAdapter myAdapter = (ListHorizontalScrollViewAdapter) params[2];
		Log.i(TAG,
				"touchedView "
						+ touchedView.toString().replace(
								"com.example.hscroll.MainActivity$ViewHolder",
								""));
		oldX = LHSVAdapter.getScrollX(position);
		Log.i(TAG, "Async Position" + Arrays.asList(position).toString() + "P"
				+ position + " oldX " + LHSVAdapter.getScrollX(position));
		// Log.i(TAG, "oldX " + oldX);
		do {
			sleep();
			newX = LHSVAdapter.getScrollX(position);
			// Log.i(TAG, "newX " + newX);
			// Log.i(TAG,
			// "!oldX.equals(newX) "+!oldX.equals(newX)+" isScrollStop "+isScrollStop+" newX "+newX);
			if (!oldX.equals(newX)) {
				oldX = newX;
				// Log.i(TAG, "set old=new");
			} else {
				isScrollStop = true;
			}
			// Log.i(TAG, "isScrollStop " + isScrollStop + " Pos "
			// + position[0] + " NoldX " + oldX + " newX " + newX);
			Log.i(TAG,
					"*************count "
							+ testCount
							+ " P "
							+ position
							+ " Tag "
							+ LHSVAdapter.touchedViewHolder.hsv
									.getTag()
									.toString()
									.replace(
											"com.example.hscroll.MainActivity$ViewHolder",
											""));
			testCount++;
		} while (!isScrollStop);
		newX = LHSVAdapter.getBoundaryX(position);
		// if (mainActivity.touchedViewHolder.hsv.getTag()==viewTag)
		// mainActivity.touchedViewHolder.hsv.smoothScrollTo(newX, 0);
		// viewTag.hsv.smoothScrollTo(newX, 0);
		touchedView.smoothScrollTo(newX, 0);
		Log.i(TAG,
				"touchedView.scroll "
						+ touchedView.toString().replace(
								"com.example.hscroll.MainActivity$ViewHolder",
								""));
		Log.i(TAG,
				"smoothScrollTo"
						+ newX
						+ " P "
						+ position
						+ " Tag "
						+ LHSVAdapter.touchedViewHolder.hsv
								.getTag()
								.toString()
								.replace(
										"com.example.hscroll.MainActivity$ViewHolder",
										""));
		LHSVAdapter.setScrollX(position, newX);
		return newX;
	}

	private void sleep() {
		try {
			Thread.sleep(mDelay);
			// Log.i(TAG, "sleepppppppppppping");
		} catch (InterruptedException e) {
			// TODO: handle exception
			Log.e(TAG, e.toString());
		}
	}
}
