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
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.PorterDuff;

public class MainActivity extends ActionBarActivity {

	// IDs for menu items
	private static final int MENU_DELETE = Menu.FIRST;
	private static final int LAUNCH_DIALOG = Menu.FIRST + 1;
	static private final String mainTitle = "Modern Art UI";
	static private final String seekBarStartTextView1 = this.getString(R.string.seekBarStartTextView1);
	static private final String seekBarStopTextView1 = this.getString(R.string.seekBarStopTextView1);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setTitle(mainTitle);
		seekBar = (SeekBar) findViewById(R.id.seekBar);
		//ListAdapter mAdapter = new BaseAdapter(getApplicationContext());
		//		volumeControl = (SeekBar) findViewById(R.id.volume_bar);
		//
		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			int progressChanged = 0;
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
				Log.d("DEBUG", "Progress is: "+progress);
				textView1 = (TextView) view.findViewById(R.id.textView1);
				setBackGroundColor(seekBarStartTextView1, progress, textView);
				textView2 = (TextView) view.findViewById(R.id.textView2);
				setBackGroundColor(Color.GREEN, textView);
				textView3 = (TextView) view.findViewById(R.id.textView3);
				setBackGroundColor(Color.GREEN, textView);
				textView4 = (TextView) view.findViewById(R.id.textView4);
				setBackGroundColor(Color.GREEN, textView);
				textView5 = (TextView) view.findViewById(R.id.textView5);
				setBackGroundColor(Color.GREEN, textView);
				textView5 = (TextView) view.findViewById(R.id.textView6);
				setBackGroundColor(Color.GREEN, textView);
				
			}
			//@Override
			//public void onStartTrackingTouch(SeekBar seekBar) {
			//	// TODO Auto-generated method stub
			//}
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				//Toast.makeText(SeekbarActivity.this,"seek bar progress:"+progressChanged, 
				//Toast.LENGTH_SHORT).show();
				textView1 = (TextView) view.findViewById(R.id.textView1);
				setBackGroundColor(seekBarStopTextView1, 0,  textView);
				textView2 = (TextView) view.findViewById(R.id.textView2);
				setBackGroundColor(Color.WHITE, textView);
				textView3 = (TextView) view.findViewById(R.id.textView3);
				setBackGroundColor(Color.WHITE, textView);
				textView4 = (TextView) view.findViewById(R.id.textView4);
				setBackGroundColor(Color.WHITE, textView);
				textView5 = (TextView) view.findViewById(R.id.textView5);
				setBackGroundColor(Color.WHITE, textView);
				textView5 = (TextView) view.findViewById(R.id.textView6);
				setBackGroundColor(Color.WHITE, textView);
			}

			private void setBackGroundColor (String colorHexString, View view, int colorOffset, String colorFlag) {
			//private void setBackGroundColor (int color, View view) {
				int colorDecimal = Integer.parseInt(colorHexString, 16);
				if (colorFlag == DECREMENT ) {
					int newColor = colorDecimal - colorOffset;
				} else if (colorFlag == INCREMENT) {
					int newColor = colorDecimal+colorOffset;
				}
				String colorCode = String.format("#%06x", newColor);
				view.getBackground().setColorFilter(colorCode, PorterDuff.Mode.DARKEN);
			}
		}
	}

		SystemClock.sleep(3000);
		setBackGroundColor(Color.CYAN, getWindow().getDecorView());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		super.onCreateOptionsMenu(menu);
		menu.add(Menu.NONE, MENU_DELETE, Menu.NONE, "Delete all");
		menu.add(Menu.NONE, LAUNCH_DIALOG, Menu.NONE, "Dump to log");
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_DELETE:
			return true;
		case LAUNCH_DIALOG:
			DialogFragment momaDialog = new MomaDialogFragment();
			momaDialog.show(getSupportFragmentManager(), "moma");
			//MomaDialogFragment momaDialog = new MomaDialogFragment(context);
			//momaDialog.show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
	}

	class MomaDialogFragment extends DialogFragment {
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the Builder class for convenient dialog construction
			//final Dialog dialog = new Dialog(context);
			setContentView(R.layout.dialog_fragment);
			setMessage(R.string.dialog_fragment_msg);
			final Button dismissDialogButton = (Button) findViewById(R.id.dismiss_dialog_button);
			dismissDialogButton.setTitle(R.string.dismiss_dialog_button_msg);
			dismissDialogButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO - Indicate result and finish
					//setResult(RESULT_CANCELED);
					//finish();
					v.dismiss();
				}
			final Button openWebPageButton = (Button) findViewById(R.id.openPageButton);
			openWebPageButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO - Indicate result and finish
					openWebPage(WEB_PAGE);
					//setResult(RESULT_CANCELED);
					finish();
				}
			});
			public void openWebPage(String url) {
				Uri webpage = Uri.parse(url);
				Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
				if (intent.resolveActivity(getPackageManager()) != null) {
					startActivity(intent);
				}
			}
	}

}
