//package com.example.hscroll;
//
//import android.content.Context;
//import android.util.AttributeSet;
//import android.widget.HorizontalScrollView;
//
//public class HSV extends HorizontalScrollView {
//	private static final String TAG = "hscroll";
//	private MainActivity mainActivity = MainActivity.getInstance();
//
//	private int position;
//
//	public void setPosition(int position) {
//		this.position = position;
//		// Log.i(TAG, "HSVSetP" + position);
//	}
//
//	public int getPosition() {
//		return position;
//	}
//
//	public HSV(Context context) {
//		super(context);
//		// TODO Auto-generated constructor stub
//	}
//
//	public HSV(Context context, AttributeSet attrs) {
//		super(context, attrs);
//		// TODO Auto-generated constructor stub
//	}
//
//	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
//		super.onScrollChanged(l, t, oldl, oldt);
//		// The position is passed to here when the onTouch is triggered,
//		// however,
//		// when the new HSV is inflated (recycled) the onScrollChanged method is
//		// called.
//		// The "position" value is not changed, but the new view's left is zero.
//		// So the new view's left value and the "old position" accidently set to
//		// the arraylist.
//
//		mainActivity.setScrollX(position, l);
//		// Log.i(TAG, "l:" + l +" ol"+oldl+" P:" + position);
//	}
//
//}
