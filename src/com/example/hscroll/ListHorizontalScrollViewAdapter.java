package com.example.hscroll;

import java.util.ArrayList;
import java.util.Arrays;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

public class ListHorizontalScrollViewAdapter extends BaseAdapter {
//	TODO obtain the items number from main input
	private View[] itemViews = new View[100];
	private int itemViewsLength = itemViews.length;
	private View[] innerItemViews = new View[30];
	private static final String TAG = "hscroll MyAdapter";

	private ArrayList<Integer> scrollXList;
	private ArrayList<Integer> BoundaryList;
	private int mFirstVisiblePosition;
	private int mLastVisiblePosition;
	// Remember to use Integer instead of int to avoid Arrays.asList.contains
	// return wrong result.
	private Integer[] mVisiblePosition;
	ViewHolder touchedViewHolder;
	private Context mContext;
	private ListView mListView;
	private static ListHorizontalScrollViewAdapter INSTANCE = null;

	public ListHorizontalScrollViewAdapter(Context context, ListView listView) {
		INSTANCE = this;
		mContext = context;
		mListView = listView;
		iniScrollXList();
		iniBoundaryList();
	}
	
	public static ListHorizontalScrollViewAdapter getInstance() {
		 return INSTANCE;
		 }

	public int getItemViewsLength() {
		return itemViewsLength;
	}

	@Override
	public int getCount() {
		return itemViews.length;
	}

	@Override
	public Object getItem(int position) {
		return itemViews[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public ArrayList<Integer> iniScrollXList() {
		scrollXList = new ArrayList<Integer>();
		for (int i = 0; i < itemViews.length + 1; i++) {

			scrollXList.add(0);
		}
		return scrollXList;
	}

	public int getScrollX(int position) {
		int scrollX = scrollXList.get(position + 1);
		// Log.i(TAG, "MgetScrollX" +
		// Arrays.asList(scrollXList).toString());
		return scrollX;
	}

	public void setScrollX(int position, int scrollX) {
		// this create a list of current visible view's position
		mFirstVisiblePosition = mListView.getFirstVisiblePosition();
		mLastVisiblePosition = mListView.getLastVisiblePosition();
		calVisiblePositions(mFirstVisiblePosition, mLastVisiblePosition);

		// *****Debug*****
		// Log.i(TAG,
		// "getFirst"+listView.getFirstVisiblePosition()+"getLast"+listView.getLastVisiblePosition());
		// Log.i(TAG, "MsetScrollX");
		// Log.i(TAG,
		// "P"+position+" contain"+Arrays.toString(mVisiblePosition));

		// ensure the scrollX value of the position is not invisible
		if (Arrays.asList(mVisiblePosition).contains(position)) {
			scrollXList.set(position + 1, scrollX);
			// Log.i(TAG, "MSetS" + " P " + position + " X " + scrollX);

		}
	}

	public ArrayList<Integer> iniBoundaryList() {
		BoundaryList = new ArrayList<Integer>();
		for (int i = 0; i < itemViews.length + 1; i++) {

			BoundaryList.add(0);
		}
		// Log.i(TAG, "iniBoundaryList"
		// + Arrays.asList(BoundaryList).toString());
		return BoundaryList;
	}

	public int getBoundaryX(int position) {

		int lowerBoundary = BoundaryList.get(position + 1);
		// Log.i(TAG, "getBoundaryX"
		// + Arrays.asList(BoundaryList).toString());
		return lowerBoundary;

	}

	public void setBoundaryX(int position, int lowerBoundary) {

		BoundaryList.set(position + 1, lowerBoundary);
		// Log.i(TAG, "setBoundaryList"
		// + Arrays.asList(BoundaryList).toString());

	}

	public void calVisiblePositions(int firstP, int lastP) {
		int count = lastP - firstP + 1;
		// Log.i(TAG, "cal.count"+count);
		Integer[] positions = new Integer[count];
		// a list from firstVisiblePosition to the lastVisiblePosition
		for (int i = 0; i < count; i++) {
			positions[i] = firstP;
			firstP++;
		}
		mVisiblePosition = positions;
		// Log.i(TAG, "Contain"+Arrays.asList(positions).contains(firstP));
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		if (null == convertView) {
			viewHolder = new ViewHolder();
			LayoutInflater layoutInflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = layoutInflater
					.inflate(R.layout.hsview, parent, false);

			viewHolder.hsv = (com.example.hscroll.HSV) convertView
					.findViewById(R.id.hsview);
			viewHolder.hll = (LinearLayout) convertView
					.findViewById(R.id.hlinear);
			// this tag the convertView created here for reusing.
			convertView.setTag(viewHolder);

			// for (int i = 0; i < innerItemViews.length; i++) {
			// Button button = new Button(getApplicationContext());
			// button.setText("This is a Button " + (position + 1) + " "
			// + (i + 1));
			// viewHolder.hll.addView(button);
			// }
			// for (int i = 0; i < innerItemViews.length; i++) {
			// Button button = new Button(getApplicationContext());
			// button.setText("This is a Button " + (position + 1) + " "
			// + (i + 1));
			// viewHolder.hll.addView(button);
			// }
			for (int i = 0; i < innerItemViews.length; i++) {
				Button button = new Button(mContext);
				button.setText("This is a Button " + (i + 1));
				viewHolder.hll.addView(button);
			}
			for (int i = 0; i < innerItemViews.length; i++) {
				Button button = new Button(mContext);
				button.setText("This is a Button " + (i + 1));
				viewHolder.hll.addView(button);
			}

		} else {
			// reuse created view
			viewHolder = (ViewHolder) convertView.getTag();
			// after recycle a view, reset the position value in HSV.
			viewHolder.hsv.setPosition(-1);

			// This can get initial btns' X, but need to redraw views to get
			// valid data, and each row are slightly different, need a
			// arrays. OR when onTouch, find the current row's boundaries at
			// then.
			// **********the boundaries = btn's current X + l.
		}
		// When the view is recycled, the state of the view will be
		// preserved, as the view is reused, the previous state will be
		// restored.
		// Log.i(TAG, "Array" + Arrays.asList(scrollXList).toString());
		convertView.scrollTo(getScrollX(position), 0);
		// Log.i(TAG, "getViewscrollTo" + getScrollX(position) + " P" +
		// position);

		// TODO the following can get each midloc after scrolling down, can
		// scroll to bottom then back to top at start?
		// int[] midChildLoc = new int[2];
		// viewHolder.hll.getChildAt((viewHolder.hll.getChildCount()/2)-1).getLocationOnScreen(midChildLoc);
		// Log.i(TAG, "midLoc"+midChildLoc[0]+" p "+position);
		// setScrollX(position, midChildLoc[0]);

		// set OnTouchListener to obtain the scrollX value
		convertView.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// Log.i(TAG, "onTouch");

				// get viewHolder
				touchedViewHolder = (ViewHolder) v.getTag();

				// **********TC2***************
				// Pass the position which user is interact with, in order
				// to store to correct position in ArrayList
				touchedViewHolder.hsv.setPosition(position);
				// Log.i(TAG, "TouchArray"
				// + Arrays.asList(scrollXList).toString());

				// Reassign touch event to HorizontalScrollView's
				// onTouchEvent, in order to scroll, if the onTouch set to
				// true ???try pass to different container(not working)

				if (event.getAction() == MotionEvent.ACTION_UP) {
					// TODO
					 new GetFinalXTask().execute(position, v, this);
					Log.i(TAG,
							"onTouchViewHolder"
									+ touchedViewHolder.hsv
											.getTag()
											.toString()
											.replace(
													"com.example.hscroll.MainActivity$ViewHolder",
													""));
				}

				touchedViewHolder.hsv.onTouchEvent(event);

				// the getLocationOnScreen can provide absolute top-left
				// corner coordinated

				// Log.i(TAG,
				// "MSetP"+position+" getFirstVisiblePosition"+listView.getFirstVisiblePosition());

				return true;
			}
		});

		// Debug
		// String viewID = viewHolder.hsv.getTag().toString()
		// .replace("com.example.hscroll.MainActivity$ViewHolder", "");
		// viewHolder.btn3.setText("R" + position + " V" + viewID);
		// Log.i(TAG, " position " + position + " viewID " + viewID
		// + " ScrollX " + viewHolder.hsv.getScrollX() + "+"
		// + convertView.getScrollX());
		// Log.i(TAG, "convertViewID" + viewID);
		// Debug end

		return convertView;
	}

}