package com.example.hscroll;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class MainActivity extends Activity {

	private static final String TAG = "hscroll";
	private ListHorizontalScrollViewAdapter myAdapter;
	private ListView listView;
	private View[] itemViews = new View[100];
	private View[] innerItemViews = new View[10];

	// private ArrayList<View> listItems;

	// public ArrayList<View> addItems(View item) {
	// listItems = new ArrayList<View>();
	// listItems.add(item);
	// return listItems;
	// }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView) findViewById(R.id.listview_main);
		// myAdapter = new ListHorizontalScrollViewAdapter(itemViews,
		// innerItemViews, getApplicationContext(), listView,
		// "ALIGN_TO_BOTH", "Enable");
		// listView.setAdapter(myAdapter);
		myAdapter = new MyAdapter(itemViews, innerItemViews,
				getApplicationContext(), listView, "ALIGN_TO_BOTH", "Enable");
		listView.setAdapter(myAdapter);

		// addItems(inibuts());
	}

	// private View inibuts() {
	// LinearLayout layout = (LinearLayout) findViewById(R.id.hlinear);
	// for (int i = 0; i < innerItemViews.length; i++) {
	// Button button = new Button(this);
	// layout.addView(button);
	// }
	// return layout;
	// }
}
