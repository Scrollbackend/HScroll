package com.example.hscroll;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.HorizontalScrollView;

//There will be as many object as the system needs when convertView is created
//the first time, the position need to be pass here then feed back to take
//advantage of multiple HSV object, therefore when scroll multiple rows the
//scrollX will store correctly.
//It is been created many instances while convertView == null
public class HSV extends HorizontalScrollView {
	private static final String TAG = "hscroll";
	// private MainActivity mainActivity = MainActivity.getInstance();

	private int position;
	private ViewHolder touchedViewHolder = new ViewHolder();
	private String alignMode;

	private enum AlignSetting {
		NO_ALIGN, ALIGN_TO_RIGHT, ALIGN_TO_BOTH;
		public static AlignSetting getAlignSetting(String alignMode) {
//			Log.i(TAG, "valueOf" + valueOf(alignMode));
			return valueOf(alignMode);

		}
	};
	private enum ContinuesScrollSetting {
		Enable,Disable;
		public static ContinuesScrollSetting getCSrollSetting(String CScrollMode) {
//			Log.i(TAG, "valueOf" + valueOf(CScrollMode));
			return valueOf(CScrollMode);

		}
	};
	private String cSrollMode;

	private ListHorizontalScrollViewAdapter LHSVAdapter = ListHorizontalScrollViewAdapter
			.getInstance();

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
	}

	// this will run
	public HSV(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.alignMode = LHSVAdapter.getmAlignMode();
		this.cSrollMode=LHSVAdapter.getCSrollMode();
	}

	@Override
	public void scrollTo(int x, int y) {
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
		// TODO
		LHSVAdapter.setScrollX(position, l);
		// Log.i(TAG, "l:" + l + " ol:" + oldl + " P:" + position);

		// getLocationOnScreen and getScrollX both provide accurate result. But
		// the boundaries need calculate as the scroll happening since the views
		// could be shifted a bit for each row if the child width is not the
		// same.
		touchedViewHolder = (ViewHolder) this.getTag();
		int childCount = touchedViewHolder.hll.getChildCount();
		// Log.i(TAG, "childCount"+childCount);
		// childCount -1 or will exceed, remember the last digit of i never
		// reach in for loop.
		for (int i = 0; i < childCount - 1; i++) {
			// FIXME separate the getLocationOnScreen method, return a int
			int[] locationLow = new int[2];
			int[] locationUpper = new int[2];
			int[] locationRef = new int[2];
			touchedViewHolder.hll.getChildAt(i)
					.getLocationOnScreen(locationLow);
			touchedViewHolder.hll.getChildAt(i + 1).getLocationOnScreen(
					locationUpper);
			touchedViewHolder.hll.getChildAt(0)
					.getLocationOnScreen(locationRef);
			int lowerBoundary = locationLow[0];
			// Log.i(TAG, "Lower" + lowerBoundary);
			// upper has -1 pixel off accurate
			int upperBoundary = locationUpper[0];
			// Log.i(TAG, "Upper" + upperBoundary);
			int refBoundary = locationRef[0];
			// Log.i(TAG, "Boundary Ref" + refBoundary);
			// Log.i(TAG, "Lower-Ref"+(lowerBoundary-refBoundary));
			int lowerWidth = touchedViewHolder.hll.getChildAt(i).getWidth();
			int upperWidth = touchedViewHolder.hll.getChildAt(i + 1).getWidth();
			// Log.i(TAG, "i "+i);
			
//			Control the aligning mode 
			switch (AlignSetting.getAlignSetting(alignMode)) {
			case NO_ALIGN:
				LHSVAdapter.setBoundaryX(position, l);
				break;
			case ALIGN_TO_RIGHT:
//				Only align to the right
				if (0 >= lowerBoundary && 0 < upperBoundary)
					LHSVAdapter.setBoundaryX(position, upperBoundary
							- refBoundary);
				break;
			case ALIGN_TO_BOTH:
//				Align to itself when the edge is on the left half of child
				if (0 >= lowerBoundary
						&& 0 < (lowerBoundary + (lowerWidth / 2))) {
					// Log.i(TAG, "P"+position+" Lower Boundary " +
					// (lowerBoundary -
					// refBoundary)
					// + " Upper Boundary " + (upperBoundary - refBoundary));
					// TODO
					LHSVAdapter.setBoundaryX(position, lowerBoundary
							- refBoundary);

					// mainActivity.touchedViewHolder.hll.scrollTo(lowerBoundary-refBoundary,
					// t);
					Log.i(TAG, "ALIGN_TO_BOTH");
					break;
				}
//				Align to right when the edge is on the right half of child
				if (0 >= (lowerBoundary + (lowerWidth / 2))
						&& 0 < (upperBoundary + (lowerWidth / 2))) {
					// TODO
					LHSVAdapter.setBoundaryX(position, upperBoundary
							- refBoundary);
					break;
				}
				break;
			}

		}
		// Log.i(TAG, mainActivity.)

		// There will be problems if the two sets of elements set in getView()
		// have noticeable width difference, through in what we intent to do,
		// there should be no significant difference.
		int[] lastChildLoc = new int[2];
		touchedViewHolder.hll.getChildAt(childCount - 1).getLocationOnScreen(
				lastChildLoc);
		 Log.i(TAG,
		 "childCount"+touchedViewHolder.hll.getChildCount());
		int lastChildWidth = touchedViewHolder.hll.getChildAt((childCount) - 1)
				.getWidth();
		int firstChildWidth = touchedViewHolder.hll.getChildAt(0).getWidth();
		int HSVWidth = touchedViewHolder.hll.getWidth();
		int[] midChildLoc = new int[2];
		touchedViewHolder.hll.getChildAt((childCount/2) - 1).getLocationOnScreen(
				midChildLoc);

		DisplayMetrics dm = getResources().getDisplayMetrics();
		int screenWidth = dm.widthPixels;
		// Log.i(TAG,
		// "l "+l+" ?iseuqalto"+(lastChildWidth+lastChildLoc[0]-HSVWidth));
		 Log.i(TAG, "l " + l + " lastLeft" + lastChildLoc[0] + " HSVWidth "
		 + HSVWidth + " lv " + lastChildWidth +" \nmidleft "+midChildLoc[0]+" \nscreen-width "
		 + (HSVWidth - lastChildWidth) + "screenWidth" + screenWidth);

		// Debug
		touchedViewHolder.hll.getChildAt((childCount) - 1).setBackgroundColor(
				Color.BLUE);
		 Log.i(TAG, "screenWidth" + screenWidth + " difference "
		 + (HSVWidth - l - lastChildWidth));

		// Setting up continues scroll, because there is align mechanism, if not
		// the elements may jump a little
		switch (ContinuesScrollSetting.getCSrollSetting(cSrollMode)) {
		case Disable:
			
			break;

		default:
			if (position != -1) {
				if (screenWidth >= (HSVWidth - l - lastChildWidth)) {
					Log.i(TAG, ">= true");
					touchedViewHolder.hsv.scrollBy(-(HSVWidth / 2), 0);
				}
				if (l <= firstChildWidth) {
					Log.i(TAG, "<= true");
					touchedViewHolder.hsv.scrollBy((HSVWidth / 2), 0);
				}
			}
			break;
		}
	

	}

}
