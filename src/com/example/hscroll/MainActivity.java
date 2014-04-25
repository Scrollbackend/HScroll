package com.example.hscroll;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

public class MainActivity extends Activity {
	//Set up singleton mode to allow HSV use method in this activity
	private static MainActivity INSTANCE = null;

	
	private static final String TAG = "hscroll";
	private MyAdapter myAdapter;
	private ArrayList<Integer> scrollXList;
	private ListView listView;
	private int mFirstVisiblePosition;
	private int mLastVisiblePosition;
	//Remember to use Integer instead of int to avoid Arrays.asList.contains return wrong result.
	private Integer[] mVisiblePosition;
//	private Singleton singleton =Singleton.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView) findViewById(R.id.listview_main);
		INSTANCE = this;
		myAdapter = new MyAdapter();
		listView.setAdapter(myAdapter);
		
		
		
	}
	public static MainActivity getInstance(){
		return INSTANCE;
	}
	public void setScrollX(int position, int scrollX) {
		mFirstVisiblePosition=listView.getFirstVisiblePosition();
		mLastVisiblePosition=listView.getLastVisiblePosition();
		myAdapter.calVisiblePositions(mFirstVisiblePosition,mLastVisiblePosition);
		Log.i(TAG, "getFirst"+listView.getFirstVisiblePosition()+"getLast"+listView.getLastVisiblePosition());
		Log.i(TAG, "MsetScrollX");
		Log.i(TAG, "P"+position+" contain"+Arrays.toString(mVisiblePosition));
		if(Arrays.asList(mVisiblePosition).contains(position)){
		scrollXList.set(position+1, scrollX);
		Log.i(TAG, "MSetS");
		
		}
//		Integer[] test = new Integer[]{1,2,3,4,5,6};
//		int test1= 3;
//		Log.i(TAG, "Test"+Arrays.asList(test).contains(test1));
		
	}
	class ViewHolder {
		public com.example.hscroll.HSV hsv;
		public LinearLayout	 hll;
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

		private Button[] buttonViews = new Button[6];
		private LinearLayout hsvLinear;
//		Singleton singleton = Singleton.getInstance();

		

		public MyAdapter() {
			iniScrollXList();
//			buttonsInit();
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
			for (int i = 0; i < itemViews.length+1; i++) {

				scrollXList.add(0);
			}
			return scrollXList;
		}

		

		public int getScrollX(int position) {
			int scrollX = scrollXList.get(position+1);
			return scrollX;
		}
		
		public void calVisiblePositions (int firstP, int lastP){
			int count = lastP-firstP+1;
			Log.i(TAG, "cal.count"+count);
			Integer[] positions= new Integer[count];
			for (int i= 0; i<count;i++){
				positions[i]=firstP;
				firstP++;
			}
			mVisiblePosition=positions;
//			Log.i(TAG, "Contain"+Arrays.asList(positions).contains(firstP));
		}
		/*public void buttonsInit (){
			hsvLinear = (LinearLayout)findViewById(R.id.hlinear);
			for (int i=0; i<buttonViews.length;i++){
//				Button buttonView = (Button)findViewById(R.id.btnview_btn);
				TextView buttonView = new TextView(getApplicationContext());
				buttonView.setText(""+(i+1));
				hsvLinear.addView(buttonView);
			}
			for (int i=0; i<buttonViews.length;i++){
				Button buttonView = (Button)findViewById(R.id.btnview_btn);
				hsvLinear.addView(buttonView);
			}
		}*/

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
				viewHolder.hll = (LinearLayout) convertView.findViewById(R.id.hlinear);

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
			} else{
				// reuse created view
				viewHolder = (ViewHolder) convertView.getTag();
//				after recycle a view, reset the position value in HSV.
				viewHolder.hsv.setPosition(-1);}

			// The viewHolder and sub view in viewHolder shared the scroll value
			// The scrollTo set here will ensure the initial scroll value to
			// 0,0, when reused the scroll value will change accordingly
			// convertView.scrollTo(300, 0);
//			mFirstVisiblePosition=listView.getFirstVisiblePosition();
//			mLastVisiblePosition=listView.getLastVisiblePosition();
//			calVisiblePositions(mFirstVisiblePosition,mLastVisiblePosition);
//			Log.i(TAG, "getFirst"+listView.getFirstVisiblePosition()+"getLast"+listView.getLastVisiblePosition());
			Log.i(TAG, "Array"+Arrays.asList(scrollXList).toString());
			convertView.scrollTo(getScrollX(position), 0);
			Log.i(TAG, "getViewscrollTo" + getScrollX(position) + " P" + position);

			// set OnTouchListener to obtain the scrollX value
			convertView.setOnTouchListener(new View.OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					Log.i(TAG, "onTouch");
					
//					final int scrollX;

					// get viewHolder
					ViewHolder touchedViewHolder = (ViewHolder) v.getTag();
					// Reassign touch event to HorizontalScrollView's
//					 onTouchEvent, in order to scroll, if the onTouch set to true ???try pass to different container(not working)
					touchedViewHolder.hsv.setPosition(position);
					Log.i(TAG, "TouchArray"+Arrays.asList(scrollXList).toString());
					touchedViewHolder.hsv.onTouchEvent(event);
					
					
//					Log.i(TAG, "MSetP"+position+" getFirstVisiblePosition"+listView.getFirstVisiblePosition());
					// TODO Scroller might work
					
/*//					not working
 * 
					Scroller mScroller3 = new Scroller(touchedViewHolder.hll.getContext());
					HorizontalScrollView viewTest1 = (HorizontalScrollView) findViewById(R.id.hsview);
					Scroller mScroller2 = new Scroller(viewTest1.getContext());*/
				/*	Scroller mScroller = new Scroller(touchedViewHolder.hsv.getContext());
					Log.i(TAG,
							"getFinalX" + mScroller.getFinalX()
									+ "|"+mScroller.getCurrX());*/
					
					
					/*Scroller mScroller = new Scroller(getApplicationContext());
					if (!mScroller.computeScrollOffset())
					setScrollX(position, viewHolder.hsv.getHSVScrollLeft());
					Log.i(TAG, "setScrollX"+viewHolder.hsv.getHSVScrollLeft()+" P"+position);*/
					
//					scrollX = (int) touchedViewHolder.hsv.getHSVScrollLeft();
//					Log.i(TAG, "LEFT"+touchedViewHolder.hsv.getHSVScrollLeft());
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

					
					
					
					
					
					/*// TODO Scroller might work
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

//					Log.i(TAG, "btn1ScrollX"+touchedViewHolder.btn1.getScrollX());
					
//					setScrollX(position, scrollX);

/*					Log.i(TAG, "Row" + position + "ScrollX"
							+ touchedViewHolder.hsv.getScrollX() + "&"
							+ scrollX + "&" + getScrollX(position));*/
					// return true;
					return true;
				}
			});

			// Debug
			String viewID = viewHolder.hsv.getTag().toString()
					.replace("com.example.hscroll.MainActivity$ViewHolder", "");
			viewHolder.btn3.setText("R" + position + " V" + viewID);
			/*Log.i(TAG, " position " + position + " viewID " + viewID
					+ " ScrollX " + viewHolder.hsv.getScrollX() + "+"
					+ convertView.getScrollX());
			Log.i(TAG, "convertViewID" + viewID);*/
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
