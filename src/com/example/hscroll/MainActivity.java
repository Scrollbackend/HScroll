package com.example.hscroll;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
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
import android.widget.Scroller;

public class MainActivity extends Activity {
	// Set up singleton mode to allow HSV use method in this activity
	private static MainActivity INSTANCE = null;

	private static final String TAG = "hscroll";
	private MyAdapter myAdapter;
	private ArrayList<Integer> scrollXList;
	private ArrayList<Integer> BoundaryList;
	private ListView listView;
	private int mFirstVisiblePosition;
	private int mLastVisiblePosition;
	// Remember to use Integer instead of int to avoid Arrays.asList.contains
	// return wrong result.
	private Integer[] mVisiblePosition;
	// it works up to 10 ms (tested on this particular device), but considering slower device (?need confirmation),
	// and UX that inserting a small interval between user and UI action makes
	// it looks smooth (!but here the interval is not constant).
	private int mDelay = 30;

	ViewHolder touchedViewHolder;

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

	// public method enable other to call, singleton mode
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
			Log.i(TAG, "MSetS" + " P " + position + " X " + scrollX);

		}
	}

	public void setBoundaryX(int position, int lowerBoundary) {

		BoundaryList.set(position + 1, lowerBoundary);
		// Log.i(TAG, "setBoundaryList"
		// + Arrays.asList(BoundaryList).toString());

	}

	class ViewHolder {
		public com.example.hscroll.HSV hsv;
		public LinearLayout hll;
//		public Button btn1;
//		public Button btn2;
//		public Button btn3;
//		public Button btn4;
//		public Button btn5;
//		public Button btn6;
	}

	public class MyAdapter extends BaseAdapter {
		private View[] itemViews = new View[100];
		private int itemViewsLength = itemViews.length;
		private View[] innerItemViews = new View[6];

		public int getItemViewsLength() {
			return itemViewsLength;
		}

		public MyAdapter() {
			iniScrollXList();
			iniBoundaryList();
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
			Log.i(TAG, "MgetScrollX" + Arrays.asList(scrollXList).toString());
			return scrollX;
		}

		// TODO refine the boundaries List DONE, though this is not needed
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
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			final ViewHolder viewHolder;
			final Button btn3View;
			if (null == convertView) {
				viewHolder = new ViewHolder();
				convertView = getLayoutInflater().inflate(R.layout.hsview,
						parent, false);

				viewHolder.hsv = (com.example.hscroll.HSV) convertView
						.findViewById(R.id.hsview);
//				viewHolder.btn1 = (Button) convertView
//						.findViewById(R.id.button_m1);
//				viewHolder.btn2 = (Button) convertView
//						.findViewById(R.id.button_1);
//				viewHolder.btn3 = (Button) convertView
//						.findViewById(R.id.button_3);
//				viewHolder.btn4 = (Button) convertView
//						.findViewById(R.id.button_2);
//				viewHolder.btn5 = (Button) convertView
//						.findViewById(R.id.button_p1);
//				viewHolder.btn6 = (Button) convertView
//						.findViewById(R.id.button_p2);
				viewHolder.hll = (LinearLayout) convertView
						.findViewById(R.id.hlinear);
				// this tag the convertView created here for reusing.
				convertView.setTag(viewHolder);

				// myAdapter.notifyDataSetChanged();
				// int[] location1 = new int[2];
				// viewHolder.btn1.getLocationOnScreen(location1);
				// setBoundaryX(0, location1[0]);
				// viewHolder.btn2.getLocationOnScreen(location1);
				// setBoundaryX(1, location1[0]);
				// viewHolder.btn3.getLocationOnScreen(location1);
				// setBoundaryX(3, location1[0]);
				// viewHolder.btn4.getLocationOnScreen(location1);
				// setBoundaryX(2, location1[0]);
				// viewHolder.btn5.getLocationOnScreen(location1);
				// setBoundaryX(4, location1[0]);
				// viewHolder.btn6.getLocationOnScreen(location1);
				// setBoundaryX(5, location1[0]);
				// Log.i(TAG, "BoundaryList"
				// + Arrays.asList(BoundaryList).toString());
				
				for (int i=0; i<innerItemViews.length;i++){
					Button button = new Button(getApplicationContext());
					button.setText("This is a Button "+i);
				viewHolder.hll.addView(button);
				}
				for (int i=0; i<innerItemViews.length;i++){
					Button button = new Button(getApplicationContext());
					button.setText("This is a Button "+i);
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
				// myAdapter.notifyDataSetChanged();
				// int[] location1 = new int[2];
				// viewHolder.btn1.getLocationOnScreen(location1);
				// setBoundaryX(0, location1[0]);
				// viewHolder.btn2.getLocationOnScreen(location1);
				// setBoundaryX(1, location1[0]);
				// viewHolder.btn3.getLocationOnScreen(location1);
				// setBoundaryX(3, location1[0]);
				// viewHolder.btn4.getLocationOnScreen(location1);
				// setBoundaryX(2, location1[0]);
				// viewHolder.btn5.getLocationOnScreen(location1);
				// setBoundaryX(4, location1[0]);
				// viewHolder.btn6.getLocationOnScreen(location1);
				// setBoundaryX(5, location1[0]);
				// Log.i(TAG, "BoundaryList"
				// + Arrays.asList(BoundaryList).toString());
				// int[] location2 = new int[2];
				// viewHolder.hll.getChildAt(1).getLocationOnScreen(location2);
				// Log.i(TAG, "childcount" + viewHolder.hll.getChildCount()
				// + "child 1 " + location2[0]);
			}
			// When the view is recycled, the state of the view will be
			// preserved, as the view is reused, the previous state will be
			// restored.
			// Log.i(TAG, "Array" + Arrays.asList(scrollXList).toString());
			convertView.scrollTo(getScrollX(position), 0);
			// Log.i(TAG, "getViewscrollTo" + getScrollX(position) + " P" +
			// position);

			// set OnTouchListener to obtain the scrollX value
			convertView.setOnTouchListener(new View.OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
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
					
					
//					TODO comment the following if to simplify debug while trying dynamic adding
//					if (event.getAction() == MotionEvent.ACTION_UP) {
//						new getFinalXTask().execute(position);
//						Log.i(TAG,
//								"onTouchViewHolder"
//										+ touchedViewHolder.hsv
//												.getTag()
//												.toString()
//												.replace(
//														"com.example.hscroll.MainActivity$ViewHolder",
//														""));
//					}
					
//					switch (event.getAction()) {
//					case MotionEvent.ACTION_MOVE:
//						
//						break;
//
////					case MotionEvent.ACTION_UP:
////						new getFinalXTask().execute(position);
////						break;
//					}
					
					
					
					touchedViewHolder.hsv.onTouchEvent(event);

					// the getLocationOnScreen can provide absolute top-left
					// corner coordinated
					// int[] location = new int[2];
					// touchedViewHolder.btn3.getLocationOnScreen(location);
					// Log.i(TAG, "left" + location[0]);

					// Log.i(TAG,
					// "MSetP"+position+" getFirstVisiblePosition"+listView.getFirstVisiblePosition());

					// Log.i(TAG,
					// "btn1ScrollX"+touchedViewHolder.btn1.getScrollX());

					// TODO won't work fine if this in in onTouch
					// int[] location1 = new int[2];
					// touchedViewHolder.btn1.getLocationOnScreen(location1);
					// setBoundaryX(0, location1[0]);
					// touchedViewHolder.btn2.getLocationOnScreen(location1);
					// setBoundaryX(1, location1[0]);
					// touchedViewHolder.btn3.getLocationOnScreen(location1);
					// setBoundaryX(3, location1[0]);
					// touchedViewHolder.btn4.getLocationOnScreen(location1);
					// setBoundaryX(2, location1[0]);
					// touchedViewHolder.btn5.getLocationOnScreen(location1);
					// setBoundaryX(4, location1[0]);
					// touchedViewHolder.btn6.getLocationOnScreen(location1);
					// setBoundaryX(5, location1[0]);
					// Log.i(TAG, "BoundaryList"
					// + Arrays.asList(BoundaryList).toString());

					return true;
				}
			});

			// Debug
//			String viewID = viewHolder.hsv.getTag().toString()
//					.replace("com.example.hscroll.MainActivity$ViewHolder", "");
//			viewHolder.btn3.setText("R" + position + " V" + viewID);
			// Log.i(TAG, " position " + position + " viewID " + viewID
			// + " ScrollX " + viewHolder.hsv.getScrollX() + "+"
			// + convertView.getScrollX());
			// Log.i(TAG, "convertViewID" + viewID);
			// Debug end

			return convertView;
		}

	}

	class getFinalXTask extends AsyncTask<Integer, Void, Integer> {
		Integer oldX;
		Integer newX;
		int testCount = 0;
		Boolean isScrollStop = false;
		private MainActivity mainActivity = MainActivity.getInstance();

		@Override
		protected Integer doInBackground(Integer... position) {
			// TODO oldx and newx got the same always, that cause this method
			// only run once
			// Can I use l and oldl directly? No, the oldl never = l;
			oldX = myAdapter.getScrollX(position[0]);
			// Log.i(TAG,
			// "Async Position"+Arrays.asList(position).toString()+"P"+position[0]+" oldX "+myAdapter.getScrollX(position[0]));
			Log.i(TAG, "oldX " + oldX);
			do {
				sleep();
				newX = myAdapter.getScrollX(position[0]);
				Log.i(TAG, "newX " + newX);
				// Log.i(TAG,
				// "!oldX.equals(newX) "+!oldX.equals(newX)+" isScrollStop "+isScrollStop+" newX "+newX);
				if (!oldX.equals(newX)) {
					oldX = newX;
					Log.i(TAG, "set old=new");
				} else {
					isScrollStop = true;
				}
				Log.i(TAG, "isScrollStop " + isScrollStop + " Pos "
						+ position[0] + " NoldX " + oldX + " newX " + newX);
				Log.i(TAG, "count" + testCount);
				testCount++;
			} while (!isScrollStop);
			newX = mainActivity.myAdapter.getBoundaryX(position[0]);
			mainActivity.touchedViewHolder.hsv.smoothScrollTo(newX, 0);
			mainActivity.setScrollX(position[0], newX);
			return newX;
		}

		// @Override
		// protected void onPostExecute(Integer result) {
		// // TODO Auto-generated method stub
		// super.onPostExecute(result);
		// Log.i(TAG, "PostNewX "+newX+" P "+position);
		// // mainActivity.setScrollX(position, newX);
		// Log.i(TAG,
		// "HSVScrollXList"+Arrays.asList(scrollXList).toString()+"\nHSVScrollXList"+Arrays.asList(BoundaryList).toString());
		// mainActivity.touchedViewHolder.hsv.post(new Runnable() {
		//
		// @Override
		// public void run() {
		// // TODO Auto-generated method stub
		// mainActivity.touchedViewHolder.hsv.smoothScrollTo(newX, 0);
		// }
		// });
		// Log.i(TAG,
		// "AsyncViewHolder"+mainActivity.touchedViewHolder.hsv.getTag().toString().replace("com.example.hscroll.MainActivity$ViewHolder",
		// ""));
		// }

		private void sleep() {
			try {
				Thread.sleep(mDelay);
				Log.i(TAG, "sleepppppppppppping");
			} catch (InterruptedException e) {
				// TODO: handle exception
				Log.e(TAG, e.toString());
			}
		}
	}
}

// There will be as many object as the system needs when convertView is created
// the first time, the position need to be pass here then feed back to take
// advantage of multiple HSV object, therefore when scroll multiple rows the
// scrollX will store correctly.
// It is been created many instances while convertView == null
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

	// this will not run
	public HSV(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	// this will run
	public HSV(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void scrollTo(int x, int y) {
		// TODO Auto-generated method stub
		super.scrollTo(x, y);
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
		Log.i(TAG, "l:" + l + " ol:" + oldl + " P:" + position);

		// getLocationOnScreen and getScrollX both provide accurate result. But
		// the boundaries need calculate as the scroll happening since the views
		// could be shifted a bit for each row if the child width is not the
		// same.

		// int[] location1 = new int[2];
		// int[] location2 = new int[2];
		// int[] locationset = new int[2];
		// locationset[1] = 0;
		// mainActivity.touchedViewHolder.btn2.getLocationOnScreen(location2);
		// Log.i(TAG, "HSVleft2 " + location2[0]);
		// if (location2[0] <= 0
		// && -location2[0] <= mainActivity.touchedViewHolder.btn2
		// .getWidth())
		// locationset[1] = location2[0];
		// mainActivity.touchedViewHolder.btn1.getLocationOnScreen(location1);
		// locationset[0] = location1[0];
		// Log.i(TAG, "HSVleft1 " + location1[0]);

		// View v = mainActivity.touchedViewHolder.hsv.getChildAt(0);
		// Log.i(TAG, "childCount"+);
		// TODO
		// for (int i = 0; i < 5; i++) {
		// int[] boundaries = new int[2];
		// boundaries = mainActivity.getBoundaryX(i);
		// // if (l >= boundaries[0] && l < boundaries[1])
		// // Log.i(TAG,
		// // "Display Lower Boundary"
		// // + mainActivity.getBoundaryX(i)[0]);
		// // }
		// // if (0 >= boundaries[0] && 0 < boundaries[1])
		// // TODO check how to get accurate boundaries
		// // always substrate the btn1's left+1, then the boundary will be
		// // valid
		// // put the initial, set, get boundary methods in this class
		// // Log.i(TAG,
		// // "Low"+boundaries[0]+"High"+boundaries[1]+"Display Lower Boundary"
		// // + (mainActivity.getBoundaryX(i)[0]-locationset[0]));
		// }
		int childCount = mainActivity.touchedViewHolder.hll.getChildCount();
		// Log.i(TAG, "childCount"+childCount);
		// childCount -1 or will exceed, remember the last digit of i never
		// reach in for loop.
		for (int i = 0; i < childCount - 1; i++) {
//			TODO separate the getLocationOnScreen method, return a int
			int[] locationLow = new int[2];
			int[] locationUpper = new int[2];
			int[] locationRef = new int[2];
			mainActivity.touchedViewHolder.hll.getChildAt(i)
					.getLocationOnScreen(locationLow);
			mainActivity.touchedViewHolder.hll.getChildAt(i + 1)
					.getLocationOnScreen(locationUpper);
			mainActivity.touchedViewHolder.hll.getChildAt(0)
					.getLocationOnScreen(locationRef);
			int lowerBoundary = locationLow[0];
			// Log.i(TAG, "Lower" + lowerBoundary);
			// upper has -1 pixel off accurate
			int upperBoundary = locationUpper[0];
			// Log.i(TAG, "Upper" + upperBoundary);
			int refBoundary = locationRef[0];
			// Log.i(TAG, "Boundary Ref" + refBoundary);
			// Log.i(TAG, "Lower-Ref"+(lowerBoundary-refBoundary));
			int lowerWidth=mainActivity.touchedViewHolder.hll.getChildAt(i).getWidth();
			int upperWidth=mainActivity.touchedViewHolder.hll.getChildAt(i+1).getWidth();
			//			Log.i(TAG, "i "+i);
			
			int[] lastChildLoc=new int[2];
			mainActivity.touchedViewHolder.hll.getChildAt(childCount-1).getLocationOnScreen(lastChildLoc);
//			int[] midChildLoc = new int[2];
//			mainActivity.touchedViewHolder.hll.getChildAt((childCount/2)-1).getLocationOnScreen(midChildLoc);
//			int midChildWidth= mainActivity.touchedViewHolder.hll.getChildAt((childCount/2)-1).getWidth();
//			int theline = lastChildLoc[0]-mainActivity.touchedViewHolder.hsv.getWidth();
//			if (l>=theline)mainActivity.touchedViewHolder.hll.scrollBy(-(midChildLoc[0]+midChildWidth), 0);
//			Log.i(TAG, "childCount"+mainActivity.touchedViewHolder.hll.getChildCount());
			int lastChildWidth= mainActivity.touchedViewHolder.hll.getChildAt((childCount)-1).getWidth();
			int HSVWidth = mainActivity.touchedViewHolder.hll.getWidth();
			Log.i(TAG, "l "+l+" ?iseuqalto"+(lastChildWidth+lastChildLoc[0]-HSVWidth));
//			if(l>=1000)mainActivity.touchedViewHolder.hll.scrollBy(-500, 0);
			
			
//			if (0 >= lowerBoundary && 0 < upperBoundary)
			if (0>=lowerBoundary&&0<(lowerBoundary+(lowerWidth/2)))
			{
				// Log.i(TAG, "P"+position+" Lower Boundary " + (lowerBoundary -
				// refBoundary)
				// + " Upper Boundary " + (upperBoundary - refBoundary));
				mainActivity
						.setBoundaryX(position, lowerBoundary - refBoundary);

				// mainActivity.touchedViewHolder.hll.scrollTo(lowerBoundary-refBoundary,
				// t);
				break;
			}
			if(0>=(lowerBoundary+(lowerWidth/2))&&0<(upperBoundary+(lowerWidth/2))) 
			{
				mainActivity
				.setBoundaryX(position, upperBoundary - refBoundary);
				break;
			}
			
		}
		// Log.i(TAG, mainActivity.)

	}

	// class ViewHolder {
	// public Button btn1;
	// public Button btn2;
	// public Button btn3;
	// public Button btn4;
	// public Button btn5;
	// public Button btn6;
	// }
}