package com.example.hscroll;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class MyAdapter extends ListHorizontalScrollViewAdapter {

	public MyAdapter(View[] itemViews, View[] innerItemViews, Context context,
			ListView listView, String alignMode, String cSrollMode) {
		super(itemViews, innerItemViews, context, listView, alignMode,
				cSrollMode);
		// TODO Auto-generated constructor stub
	}

	public View[] setViews() {
		// TODO Auto-generated method stub
		View[] views = null;
		for (int i = 0; i < 30; i++) {
			for (int j = 0; j < 6; j++) {
				Button button = new Button(mContext);
				button.setText("This is a Button " + (i + 1) + " " + (j + 1));

				views[i] = button;
			}
		}
		return views;
	}

}