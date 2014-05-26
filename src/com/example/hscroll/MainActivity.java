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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView) findViewById(R.id.listview_main);
		myAdapter = new ListHorizontalScrollViewAdapter(itemViews,
				innerItemViews, getApplicationContext(), listView,
				"ALIGN_TO_BOTH", "Enable");
		listView.setAdapter(myAdapter);
	}

}
