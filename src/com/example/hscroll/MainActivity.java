package com.example.hscroll;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;

public class MainActivity extends Activity {
	// Set up singleton mode to allow HSV use method in this activity
	private static MainActivity INSTANCE = null;

	private static final String TAG = "hscroll";
	private MyAdapter myAdapter;
	private ArrayList<Integer> scrollXList;
	private ListView listView;
	private int mFirstVisiblePosition;
	private int mLastVisiblePosition;
	// Remember to use Integer instead of int to avoid Arrays.asList.contains
	// return wrong result.
	private Integer[] mVisiblePosition;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView) findViewById(R.id.listview_main);
		// set Singleton return instance
		INSTANCE = this;
		myAdapter = new MyAdapter();
		listView.setAdapter(myAdapter);

	}
//public method enable other to call, singleton mode
	public static MainActivity getInstance() {
		return INSTANCE;
	}

	// **********TC1***************
	public void setScrollX(int position, int scrollX) {
		// this create a list of current visible view's position
		mFirstVisiblePosition = listView.getFirstVisiblePosition();
		mLastVisiblePosition = listView.getLastVisiblePosition();
		myAdapter.calVisiblePositions(mFirstVisiblePosition,
				mLastVisiblePosition);
		// Log.i(TAG,
		// "getFirst"+listView.getFirstVisiblePosition()+"getLast"+listView.getLastVisiblePosition());
		// Log.i(TAG, "MsetScrollX");
		// Log.i(TAG,
		// "P"+position+" contain"+Arrays.toString(mVisiblePosition));

		// ensure the scrollX value of the position is not invisible
		if (Arrays.asList(mVisiblePosition).contains(position)) {
			scrollXList.set(position + 1, scrollX);
			// Log.i(TAG, "MSetS");

		}
	}

	class ViewHolder {
		public com.example.hscroll.HSV hsv;
		public LinearLayout hll;
		public Button btn1;
		public Button btn2;
		public Button btn3;
		public Button btn4;
		public Button btn5;
		public Button btn6;
	}

	public class MyAdapter extends BaseAdapter {
		private View[] itemViews = new View[100];
		private int itemViewsLength = itemViews.length;

		public int getItemViewsLength() {
			return itemViewsLength;
		}

		public MyAdapter() {
			iniScrollXList();
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return itemViews.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return itemViews[position];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
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
			return scrollX;
		}

		public void calVisiblePositions(int firstP, int lastP) {
			int count = lastP - firstP + 1;
			// Log.i(TAG, "cal.count"+count);
			Integer[] positions = new Integer[count];
//			a list from firstVisiblePosition to the lastVisiblePosition
			for (int i = 0; i < count; i++) {
				positions[i] = firstP;
				firstP++;
			}
			mVisiblePosition = positions;
			// Log.i(TAG, "Contain"+Arrays.asList(positions).contains(firstP));
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			final ViewHolder viewHolder;
			if (null == convertView) {
				viewHolder = new ViewHolder();
				convertView = getLayoutInflater().inflate(R.layout.hsview,
						parent, false);

				viewHolder.hsv = (com.example.hscroll.HSV) convertView
						.findViewById(R.id.hsview);
				viewHolder.btn1 = (Button) convertView
						.findViewById(R.id.button_m1);
				viewHolder.btn2 = (Button) convertView
						.findViewById(R.id.button_1);
				viewHolder.btn3 = (Button) convertView
						.findViewById(R.id.button_3);
				viewHolder.btn4 = (Button) convertView
						.findViewById(R.id.button_2);
				viewHolder.btn5 = (Button) convertView
						.findViewById(R.id.button_p1);
				viewHolder.btn6 = (Button) convertView
						.findViewById(R.id.button_p2);
				viewHolder.hll = (LinearLayout) convertView
						.findViewById(R.id.hlinear);

				// this tag the convertView created here for reusing.
				convertView.setTag(viewHolder);
			} else {
				// reuse created view
				viewHolder = (ViewHolder) convertView.getTag();
				// after recycle a view, reset the position value in HSV.
				viewHolder.hsv.setPosition(-1);
			}

			// When the view is recycled, the state of the view will be
			// preserved, as the view is reused, the previous state will be
			// restored.
			Log.i(TAG, "Array" + Arrays.asList(scrollXList).toString());
			convertView.scrollTo(getScrollX(position), 0);
			// Log.i(TAG, "getViewscrollTo" + getScrollX(position) + " P" +
			// position);

			// set OnTouchListener to obtain the scrollX value
			convertView.setOnTouchListener(new View.OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					Log.i(TAG, "onTouch");

					// get viewHolder
					ViewHolder touchedViewHolder = (ViewHolder) v.getTag();

					// **********TC2***************
					// Pass the position which user is interact with, in order
					// to store to correct position in ArrayList
					touchedViewHolder.hsv.setPosition(position);
					Log.i(TAG, "TouchArray"
							+ Arrays.asList(scrollXList).toString());
					// Reassign touch event to HorizontalScrollView's
					// onTouchEvent, in order to scroll, if the onTouch set to
					// true ???try pass to different container(not working)

					touchedViewHolder.hsv.onTouchEvent(event);

					// Log.i(TAG,
					// "MSetP"+position+" getFirstVisiblePosition"+listView.getFirstVisiblePosition());

					// Log.i(TAG,
					// "btn1ScrollX"+touchedViewHolder.btn1.getScrollX());

					return true;
				}
			});

			// Debug
			String viewID = viewHolder.hsv.getTag().toString()
					.replace("com.example.hscroll.MainActivity$ViewHolder", "");
			viewHolder.btn3.setText("R" + position + " V" + viewID);
			// Log.i(TAG, " position " + position + " viewID " + viewID
			// + " ScrollX " + viewHolder.hsv.getScrollX() + "+"
			// + convertView.getScrollX());
			// Log.i(TAG, "convertViewID" + viewID);
			// Debug end

			return convertView;
		}

	}

}

// There will be as many object as the system needs when convertView is created
// the first time, the position need to be pass here then feed back to take
// advantage of multiple HSV object, therefore when scroll multiple rows the
// scrollX will store correctly.
class HSV extends HorizontalScrollView {
	private static final String TAG = "hscroll";
	private MainActivity mainActivity = MainActivity.getInstance();

	private int position;

	// **********TC2***************
	public void setPosition(int position) {
		this.position = position;
		// Log.i(TAG, "HSVSetP" + position);
	}

	public int getPosition() {
		return position;
	}

	public HSV(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public HSV(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		// The position is passed to here when the onTouch is triggered,
		// however,
		// when the new HSV is inflated (recycled) the onScrollChanged method is
		// called.
		// The "position" value is not changed, but the new view's left is zero.
		// So the new view's left value and the "old position" accidently set to
		// the arraylist.

		// **********TC1***************
		mainActivity.setScrollX(position, l);
		// Log.i(TAG, "l:" + l +" ol"+oldl+" P:" + position);
	}

}