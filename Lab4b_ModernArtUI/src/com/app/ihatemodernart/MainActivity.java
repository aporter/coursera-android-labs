package com.app.ihatemodernart;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.graphics.Color;
import android.graphics.PorterDuff;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//main = (ActionBarActivity) findViewById(R.id.container);
		//getWindow().getDecorView().setBackgroundColor(Color.WHITE);
		//getWindow().getDecorView().getBackground().setColorFilter(Color.parseColor(colorCode), PorterDuff.Mode.DARKEN);
		setContentView(R.layout.activity_main);
		//ListAdapter mAdapter = new BaseAdapter(getApplicationContext());
		SystemClock.sleep(3000);
		setBackGroundColor(Color.CYAN, getWindow().getDecorView());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void setBackGroundColor (int color, View view) {
		view.getBackground().setColorFilter(color, PorterDuff.Mode.DARKEN);
	}
	/**
	 * A placeholder fragment containing a simple view.
	 */
}
