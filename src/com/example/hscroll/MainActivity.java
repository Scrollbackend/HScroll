package com.example.hscroll;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.Scroller;

public class MainActivity extends Activity {

	private static final String TAG = "hscroll";
	private int viewPosition;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ListView listView = (ListView) findViewById(R.id.listview_main);
		listView.setAdapter(new mAdapter());

	}

	class ViewHolder {
		public HorizontalScrollView hsv;
		public Button btn1;
		public Button btn2;
		public Button btn3;
		public Button btn4;
		public Button btn5;
		public Button btn6;
	}

	public class mAdapter extends BaseAdapter {
		// View[] itemViews;
		private View[] itemViews = new View[30];

		// ArrayList<HashMap<String, integer>> scrollXList;
		ArrayList<Integer> scrollXList;

		public mAdapter() {
			// itemViews = new View[30];
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

		/*
		 * public ArrayList<HashMap<String, integer>> iniScrollXList() {
		 * scrollXList = new ArrayList<HashMap<String,integer>>(); for (int i=0;
		 * i<itemViews.length;i++){ HashMap<String, integer> map = new
		 * HashMap<String, integer>(); map.put("Row"+i, null);
		 * scrollXList.add(map); } return scrollXList; }
		 */

		public ArrayList<Integer> iniScrollXList() {
			scrollXList = new ArrayList<Integer>();
			for (int i = 0; i < itemViews.length; i++) {

				scrollXList.add(0);
			}
			return scrollXList;
		}

		public void setScrollX(int position, int scrollX) {

			scrollXList.set(position, scrollX);
		}

		public int getScrollX(int position) {
			int scrollX = scrollXList.get(position);

			return scrollX;
		}

		//get the getLeft value that relative to the root view
		//http://stackoverflow.com/questions/3619693/getting-views-coordinates-relative-to-the-root-layout
/*		private int getLeftRoot (View v){
			int vl;
			if (v.getParent() == v.getRootView()){
				vl=v.getLeft();
				Log.i(TAG, "getLeftRootif"+vl);
				return v.getLeft();
			
			}
			else{ 
				vl=v.getLeft()+getLeftRoot((View) v.getParent());
				Log.i(TAG, "getLeftRootelse"+vl);
				return v.getLeft()+getLeftRoot((View) v.getParent());
			
			}
			
		}*/

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			final ViewHolder viewHolder;
			if (null == convertView) {
				viewHolder = new ViewHolder();
				convertView = getLayoutInflater().inflate(R.layout.hsview,
						parent, false);

				viewHolder.hsv = (HorizontalScrollView) convertView
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

				// if assign the position as ID to the HorizontalScrollView in
				// "if", the ID in Tag confirm that the view is
				// reused, as
				// the ScrollX is remembered in the HorizontalScrollView which
				// not
				// consider by ListView, the ListView reuse the
				// HorizontalScrollView to recreate Row views, therefore
				// whichever the HorizontalScrollView is reused, the ScrollX
				// value will be the same.
				// viewHolder.hsv.setId(position);

				// the sub viewHolder tag will share the same ID with
				// viewHolder, so this can not put after convertView.setTag,
				// otherwise the view can not reuse.
				// viewHolder.hsv.setTag(position);

				// this tag the convertView created here for reusing.
				convertView.setTag(viewHolder);
			} else
				// reuse created view
				viewHolder = (ViewHolder) convertView.getTag();

			// The viewHolder and sub view in viewHolder shared the scroll value
			// The scrollTo set here will ensure the initial scroll value to
			// 0,0, when reused the scroll value will change accordingly
			// convertView.scrollTo(300, 0);

			convertView.scrollTo(getScrollX(position), 0);
			Log.i(TAG, "scrollTo" + getScrollX(position) + "P" + position);

			// set OnTouchListener to obtain the scrollX value
			convertView.setOnTouchListener(new View.OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					final int scrollX;

					// get viewHolder
					ViewHolder touchedViewHolder = (ViewHolder) v.getTag();
					// Reassign touch event to HorizontalScrollView's
					// onTouchEvent, in order to scroll
					touchedViewHolder.hsv.onTouchEvent(event);
					scrollX = (int) touchedViewHolder.hsv.getScrollX();
					// this is fine
					// scrollX = (int) v.getScrollX();

					// get touched point x value
					// event.getX();

					// the action of scroll is ACTION_MOVE
					/*
					 * switch (event.getAction()) { case
					 * MotionEvent.ACTION_MOVE: Log.i(TAG,
					 * "case move"+getScrollX(position));
					 * 
					 * break; default: break; }
					 */

/*					// TODO Scroller might work
					Scroller mScroller = new Scroller(getApplicationContext());
					Log.i(TAG,
							"getFinalX" + mScroller.getFinalX()
									+ mScroller.getCurrX());*/
					
					/*//this is practically the same as getScrollX
					int[] location1 = new int[2] ;
					touchedViewHolder.btn1.getLocationOnScreen(location1);
					int[] location2 = new int[2] ;
					touchedViewHolder.btn1.getLocationInWindow(location2);
					Log.i(TAG, "getLocationOnScreen" + location1[0]+"	getLocationInWindow"+location2[0]);*/

					Log.i(TAG, "btn1ScrollX"+touchedViewHolder.btn1.getScrollX());
					
					
					setScrollX(position, scrollX);

					Log.i(TAG, "Row" + position + "ScrollX"
							+ touchedViewHolder.hsv.getScrollX() + "&"
							+ scrollX + "&" + getScrollX(position));
					// return true;
					return false;
				}
			});

			// Debug
			String viewID = viewHolder.hsv.getTag().toString()
					.replace("com.example.hscroll.MainActivity$ViewHolder", "");
			viewHolder.btn3.setText("R" + position + " V" + viewID);
			Log.i(TAG, " position " + position + " viewID " + viewID
					+ " ScrollX " + viewHolder.hsv.getScrollX() + "+"
					+ convertView.getScrollX());
			Log.i(TAG, "convertViewID" + viewID);
			// Debug end

			return convertView;
		}

		/************************ W/O viewHolder ************************/
		/*
		 * //getView w/o using viewHolder.
		 * 
		 * @Override public View getView(final int position, View convertView,
		 * ViewGroup parent) { // TODO Auto-generated method stub ViewHolder
		 * viewHolder = null; if (null == convertView) { viewHolder = new
		 * ViewHolder(); convertView =
		 * getLayoutInflater().inflate(R.layout.hsview, parent, false);
		 * 
		 * convertView.setTag(viewHolder); }
		 * 
		 * convertView.setOnTouchListener(new View.OnTouchListener() {
		 * 
		 * @Override public boolean onTouch(View v, MotionEvent event) { // TODO
		 * Auto-generated method stub
		 * 
		 * v.onTouchEvent(event);
		 * 
		 * 
		 * 
		 * Log.i(TAG, "Row"+position+"ScrollX"+v.getScrollX()); return true;
		 * //return false; } });
		 * 
		 * return convertView; }
		 */

	}

}
