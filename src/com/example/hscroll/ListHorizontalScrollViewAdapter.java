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

/**
 * Require following input parameters in this order
 * 
 * @param <View>
 *            View[],How many lines of item are there in the ListView;
 * @param <View>
 *            View[],How many Child per item;
 * @param <Context>
 *            The UI thread context;
 * @param <ListView>
 *            The ListView instance;
 * @param <String>
 *            Choose which mode of the alignment, options are:
 *            <p>
 *            NO_ALIGN (No alignment);
 *            </p>
 *            <p>
 *            ALIGN_TO_RIGHT (Always align to the next child to the right);
 *            </p>
 *            <p>
 *            ALIGN_TO_BOTH (Align to itself if the edge is on the left half of
 *            the child, otherwise to the next child to the right);
 *            </p>
 * @param <String>
 *            Choose weather enable or disable the continues scrolling, options
 *            are:
 *            <p>
 *            Enable (default enable);
 *            </p>
 *            <p>
 *            Disable (No continues scroll);
 *            </p>
 */

public class ListHorizontalScrollViewAdapter extends BaseAdapter {
	private View[] itemViews;
	private View[] innerItemViews;
	private static final String TAG = "hscroll MyAdapter";

	private ArrayList<Integer> scrollXList;
	private ArrayList<Integer> BoundaryList;
	private int mFirstVisiblePosition;
	private int mLastVisiblePosition;
	// Remember to use Integer instead of int to avoid Arrays.asList.contains
	// return wrong result.
	private Integer[] mVisiblePosition;
	ViewHolder touchedViewHolder;
	protected Context mContext;
	private ListView mListView;
	private String mAlignMode;

	private enum ContinuesScrollSetting {
		Enable, Disable;
		public static ContinuesScrollSetting getCSrollSetting(String CScrollMode) {
			// Log.i(TAG, "valueOf" + valueOf(CScrollMode));
			return valueOf(CScrollMode);

		}
	};

	private String cSrollMode;
	// Set up singleton mode to allow HSV use method in this activity
	private static ListHorizontalScrollViewAdapter INSTANCE = null;

	public ListHorizontalScrollViewAdapter(View[] itemViews,
			View[] innerItemViews, Context context, ListView listView,
			String alignMode, String cSrollMode) {
		// set Singleton return instance
		INSTANCE = this;
		this.itemViews = itemViews;
		this.innerItemViews = innerItemViews;
		mContext = context;
		mListView = listView;
		mAlignMode = alignMode;
		this.cSrollMode = cSrollMode;
		iniScrollXList();
		iniBoundaryList();
	}

	// public method enable other to call, singleton mode
	public static ListHorizontalScrollViewAdapter getInstance() {
		return INSTANCE;
	}

	public String getmAlignMode() {
		return mAlignMode;
	}

	public String getCSrollMode() {
		return cSrollMode;
	}

	public int getItemViewsLength() {
		return itemViews.length;
	}

	// TODO if the continues scroll to left is needed at start, in theory the
	// midChild left can be calculated and set to the ArrayList beforehand,
	// Through this require more work especially when the child number in each
	// item is different, as that child width and number need input. Unless
	// there is a way that get midChild left onPreDraw.
	public ArrayList<Integer> iniScrollXList() {
		scrollXList = new ArrayList<Integer>();
		for (int i = 0; i < itemViews.length + 1; i++) {

			scrollXList.add(0);
		}
		return scrollXList;
	}

	public int getScrollX(int position) {
		int scrollX = scrollXList.get(position + 1);
		Log.i(TAG, "MgetScrollX" + Arrays.asList(scrollXList).toString());
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
			Log.i(TAG, "MsetScrollX" + Arrays.asList(scrollXList).toString());
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
		Log.i(TAG, "setBoundaryList" + Arrays.asList(BoundaryList).toString());

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
			switch (ContinuesScrollSetting.getCSrollSetting(cSrollMode)) {
			case Disable:
				for (int i = 0; i < innerItemViews.length; i++) {
					Button button = new Button(mContext);
					button.setText("This is a Button " + (position + 1) + " "
							+ (i + 1));
					viewHolder.hll.addView(button);
				}
				break;

			default:
				for (int j= 0;j<1;j++){
				for (int i = 0; i < innerItemViews.length; i++) {
					Button button = new Button(mContext);
					button.setText("This is a Button " + (position + 1) + " "
							+ (i + 1));
					viewHolder.hll.addView(button);
				}
//				for (int i = 0; i < innerItemViews.length; i++) {
//					Button button = new Button(mContext);
//					button.setText("This is a Button " + (position + 1) + " "
//							+ (i + 1));
//					viewHolder.hll.addView(button);
//				}
				}
				break;
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
		// [?]Why this scrollTo not working, it did be called at start.
		// FIXME Sometimes the scrollTO will not reach the defined location
		// after intense scroll up%down.
		// Log.i(TAG, "Array" + Arrays.asList(scrollXList).toString());
		convertView.scrollTo(getScrollX(position), 0);
		Log.i(TAG, "getViewscrollTo" + getScrollX(position) + " P" + position);

		// TODO This can work, through it seems render slower, could be better
		// if the
		// view is pre-instanced rather than render every-time, try image view
		// next.
		// switch (ContinuesScrollSetting.getCSrollSetting(cSrollMode)) {
		// case Disable:
		// for (int i = 0; i < innerItemViews.length; i++) {
		// Button button = new Button(mContext);
		// button.setText("This is a Button " + (position + 1) + " "
		// + (i + 1));
		// viewHolder.hll.addView(button);
		// }
		// break;
		//
		// default:
		// for (int i = 0; i < innerItemViews.length; i++) {
		// Button button = new Button(mContext);
		// button.setText("This is a Button " + (position + 1) + " "
		// + (i + 1));
		// viewHolder.hll.addView(button);
		// }
		// for (int i = 0; i < innerItemViews.length; i++) {
		// Button button = new Button(mContext);
		// button.setText("This is a Button " + (position + 1) + " "
		// + (i + 1));
		// viewHolder.hll.addView(button);
		// }
		// break;
		// }

		// TODO because the view reuse will mess around the order, so the
		// content of each item children need to set here.This work for the
		// starting too. Remember the view will carry the state around, the
		// setting need to be reinforced for every item on each vertical scroll.
		// [!] However, this will seriously slow down the render to a point like
		// no view reuse if the number of child in item is large.
		// [?] If not using listview, a linearview BUT that would not be
		// dynamic.
		int childCount = viewHolder.hll.getChildCount();
		for (int i = 0; i < (childCount / 2) - 1; i++) {
			Button button = (Button) viewHolder.hll.getChildAt(i);
			button.setText("This is a Button " + (position + 1) + " " + (i + 1));
			// The blank effect when hold down on a button is Clickable, and
			// triggered the default click_down_effect, [?]its white since the
			// button is dark.
			button.setClickable(false);
		}
		for (int i = (childCount / 2); i < childCount - 1; i++) {
			Button button = (Button) viewHolder.hll.getChildAt(i);
			button.setText("This is a Button " + (position + 1) + " "
					+ (i - ((childCount) / 2) + 1));
			button.setClickable(false);
		}

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
					new GetFinalXTask().execute(position, v);
					// Log.i(TAG,
					// "onTouchViewHolder"
					// + touchedViewHolder.hsv
					// .getTag()
					// .toString()
					// .replace(
					// "com.example.hscroll.MainActivity$ViewHolder",
					// ""));
				}
				// This enable the continues scroll to left at beginning
				switch (ContinuesScrollSetting.getCSrollSetting(cSrollMode)) {
				case Disable:

					break;

				default:
					if (event.getAction() == MotionEvent.ACTION_MOVE) {
						// Because the HSV will never at 0 after any scroll
						if (getScrollX(position) == 0) {
							touchedViewHolder.hsv.scrollBy(1, 0);
						}
						Log.i(TAG,
								"*********************************\nMoved 1 pixel");
					}
					break;
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