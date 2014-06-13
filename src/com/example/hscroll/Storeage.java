//package com.example.hscroll;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//
//import android.util.Log;
//import android.webkit.WebView.FindListener;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//
//public class Storeage {
//	
//	private static final String TAG = "hscroll Storeage";
//	private ArrayList<Integer> scrollXList;
//	private ArrayList<Integer> BoundaryList;
//	private int mFirstVisiblePosition;
//	private int mLastVisiblePosition;
//	// Remember to use Integer instead of int to avoid Arrays.asList.contains
//	// return wrong result.
//	private Integer[] mVisiblePosition;
//	private ListView listView;
//	private LinearLayout linearLayout;
//	
//	private static Storeage INSTANCE = null;
//	
//	// public method enable other to call, singleton mode
//		public static Storeage getInstance() {
//			return INSTANCE;
//		}
//		
//		// TODO if the continues scroll to left is needed at start, in theory the
//		// midChild left can be calculated and set to the ArrayList beforehand,
//		// Through this require more work especially when the child number in each
//		// item is different, as that child width and number need input. Unless
//		// there is a way that get midChild left onPreDraw.
//		public ArrayList<Integer> iniScrollXList() {
//			scrollXList = new ArrayList<Integer>();
//			for (int i = 0; i < itemViews.length + 1; i++) {
//
//				scrollXList.add(0);
//			}
//			return scrollXList;
//		}
//
//		public int getScrollX(int position) {
//			int scrollX = scrollXList.get(position + 1);
//			Log.i(TAG, "MgetScrollX" + Arrays.asList(scrollXList).toString());
//			return scrollX;
//		}
//
//		public void setScrollX(int position, int scrollX) {
//			// this create a list of current visible view's position
//			mFirstVisiblePosition = mListView.getFirstVisiblePosition();
//			mLastVisiblePosition = mListView.getLastVisiblePosition();
//			calVisiblePositions(mFirstVisiblePosition, mLastVisiblePosition);
//
//			// *****Debug*****
//			// Log.i(TAG,
//			// "getFirst"+listView.getFirstVisiblePosition()+"getLast"+listView.getLastVisiblePosition());
//			// Log.i(TAG, "MsetScrollX");
//			// Log.i(TAG,
//			// "P"+position+" contain"+Arrays.toString(mVisiblePosition));
//
//			// ensure the scrollX value of the position is not invisible
//			if (Arrays.asList(mVisiblePosition).contains(position)) {
//				scrollXList.set(position + 1, scrollX);
//				// Log.i(TAG, "MSetS" + " P " + position + " X " + scrollX);
//				Log.i(TAG, "MsetScrollX" + Arrays.asList(scrollXList).toString());
//			}
//		}
//
//		public ArrayList<Integer> iniBoundaryList() {
//			BoundaryList = new ArrayList<Integer>();
//			for (int i = 0; i < itemViews.length + 1; i++) {
//
//				BoundaryList.add(0);
//			}
//			// Log.i(TAG, "iniBoundaryList"
//			// + Arrays.asList(BoundaryList).toString());
//			return BoundaryList;
//		}
//
//		public int getBoundaryX(int position) {
//
//			int lowerBoundary = BoundaryList.get(position + 1);
//			// Log.i(TAG, "getBoundaryX"
//			// + Arrays.asList(BoundaryList).toString());
//			return lowerBoundary;
//
//		}
//
//		public void setBoundaryX(int position, int lowerBoundary) {
//
//			BoundaryList.set(position + 1, lowerBoundary);
//			Log.i(TAG, "setBoundaryList" + Arrays.asList(BoundaryList).toString());
//
//		}
//
//		public void calVisiblePositions(int firstP, int lastP) {
//			int count = lastP - firstP + 1;
//			// Log.i(TAG, "cal.count"+count);
//			Integer[] positions = new Integer[count];
//			// a list from firstVisiblePosition to the lastVisiblePosition
//			for (int i = 0; i < count; i++) {
//				positions[i] = firstP;
//				firstP++;
//			}
//			mVisiblePosition = positions;
//			// Log.i(TAG, "Contain"+Arrays.asList(positions).contains(firstP));
//		}
//	
//	public Storeage(){
//		INSTANCE = this;
//		iniScrollXList();
//		iniBoundaryList();
//		listView = (ListView) MainActivity.this.findViewById
//	}
//}
