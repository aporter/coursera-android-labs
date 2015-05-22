package com.app.ihatemodernart;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.graphics.Color;
import android.content.DialogInterface;
import android.content.Intent;

public class MainActivity extends ActionBarActivity {
	//Initializing variables
	private static final int DECREMENT = 0;
	private static final int INCREMENT = 1;
	private static final int LAUNCH_DIALOG = Menu.FIRST;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final String mainTitle = getResources().getString(R.string.mainTitle);
		setTitle(mainTitle);
		final String initialColorTextView1 = getResources().getString(R.string.initialColorTextView1);
		final String initialColorTextView2 = getResources().getString(R.string.initialColorTextView2);
		final String initialColorTextView3 = getResources().getString(R.string.initialColorTextView3);
		final String initialColorTextView4 = getResources().getString(R.string.initialColorTextView4);
		final String initialColorTextView5 = getResources().getString(R.string.initialColorTextView5);
		final String initialColorTextView6 = getResources().getString(R.string.initialColorTextView6);
		final String initialColorTextView7 = getResources().getString(R.string.initialColorTextView7);
		// Initializing color background of the 7 text views
		TextView textView1 = (TextView) findViewById(R.id.textView1);
		setBackGroundColor(initialColorTextView1, textView1, 0, DECREMENT);
		TextView textView2 = (TextView) findViewById(R.id.textView2);
		setBackGroundColor(initialColorTextView2, textView2, 0, DECREMENT);
		TextView textView3 = (TextView) findViewById(R.id.textView3);
		setBackGroundColor(initialColorTextView3, textView3, 0, DECREMENT);
		TextView textView4 = (TextView) findViewById(R.id.textView4);
		setBackGroundColor(initialColorTextView4, textView4, 0, DECREMENT);
		TextView textView5 = (TextView) findViewById(R.id.textView5);
		setBackGroundColor(initialColorTextView5, textView5, 0, DECREMENT);
		TextView textView6 = (TextView) findViewById(R.id.textView6);
		setBackGroundColor(initialColorTextView6, textView6, 0, DECREMENT);
		TextView textView7 = (TextView) findViewById(R.id.textView7);
		setBackGroundColor(initialColorTextView7, textView7, 0, DECREMENT);
		// Overriding the methods of the seekbar
		SeekBar seekBar = (SeekBar) findViewById(R.id.slide);
		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			// Everytime the user slides the progress bar, we increment/decrement the
			// G component of the RGB color matrix of each textview background
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
				TextView textView1 = (TextView) findViewById(R.id.textView1);
				setBackGroundColor(initialColorTextView1, textView1, progress, DECREMENT);
				TextView textView2 = (TextView) findViewById(R.id.textView2);
				setBackGroundColor(initialColorTextView2, textView2, progress, DECREMENT);
				TextView textView4 = (TextView) findViewById(R.id.textView4);
				setBackGroundColor(initialColorTextView4, textView4, progress, DECREMENT);
				TextView textView6 = (TextView) findViewById(R.id.textView6);
				setBackGroundColor(initialColorTextView6, textView6, progress, INCREMENT);
				TextView textView7 = (TextView) findViewById(R.id.textView7);
				setBackGroundColor(initialColorTextView7, textView7, progress, DECREMENT);
			}
			@Override
			// Nothing to do here
			public void onStartTrackingTouch(SeekBar seekBar) {
			}
			@Override
			// Nothing to do here
			public void onStopTrackingTouch(SeekBar seekBar) {
			}
		});
	}
	// Increment/decrement the G component of the RGB color matrix of the view background by colorOffset,
	// depending on the colorFlag (INCREMENT or DECREMENT)
	public void setBackGroundColor (String colorHexString, View view, int colorOffset, int colorFlag) {
		int newColor = 0;
		int colorDecimal = Integer.parseInt(colorHexString, 16);
		if (colorFlag == DECREMENT ) {
			newColor = colorDecimal - colorOffset*256;
		} else if (colorFlag == INCREMENT) {
			newColor = colorDecimal+colorOffset*256;
		}
		String colorCode = String.format("#%06x", newColor);
		Log.d("DEBUG","Color code is "+colorCode);
		view.setBackgroundColor(Color.parseColor(colorCode));
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(Menu.NONE, LAUNCH_DIALOG, Menu.NONE, "More information");
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case LAUNCH_DIALOG:
			// Call the alert dialog builder
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
			alertDialogBuilder.setTitle("Click bellow to learn more about Modern Art!");
			// Set positive button and call browser with the MoMa url
			alertDialogBuilder.setPositiveButton("Visit the MOMA site now!",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					Uri webpage = Uri.parse(getResources().getString(R.string.momaUrl));
					Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
					if (intent.resolveActivity(getPackageManager()) != null) {
						startActivity(intent);
					}
				}
			});
			// Set negative button and cancel the dialog
			alertDialogBuilder.setNegativeButton("Not Now",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					// cancel the alert box and put a Toast to the user
					dialog.cancel();
					Toast.makeText(getApplicationContext(), "Thanks! Maybe next time?",Toast.LENGTH_LONG).show();
				}
			});
			AlertDialog alertDialog = alertDialogBuilder.create();
			// Show the alert Dialog
			alertDialog.show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
