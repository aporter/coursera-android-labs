package course.labs.notificationslab;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TestFrontEndActivity extends Activity {

	private final static long DAWN_OF_TIME = 0;
	static final String NOTIFICATION_POSTED = "notification_posted";
	
	@SuppressWarnings("unused")
	private static final String TAG = "TestFrontEndActivity";
	private static String sFileName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_front_end);

		sFileName = getFilesDir() + "/" + MainActivity.TWEET_FILENAME;

		Button ageTweetsButton = (Button) findViewById(R.id.age_tweets_button);
		ageTweetsButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setFileAge(DAWN_OF_TIME);
			}

		});

		Button rejuvTweetsButton = (Button) findViewById(R.id.rejuv_tweets_button);
		rejuvTweetsButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setFileAge(System.currentTimeMillis());
			}

		});

		Button startMainActivityButton = (Button) findViewById(R.id.start_main_button);
		startMainActivityButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(TestFrontEndActivity.this,
						MainActivity.class));
			}
		});

		createTweetFileIfMissing();

	}

	private void createTweetFileIfMissing() {

		File file = new File(sFileName);
		if (!file.exists()) {

			PrintWriter out = null;
			BufferedReader in = null;

			try {
				out = new PrintWriter(new BufferedWriter(
						new OutputStreamWriter(openFileOutput(
								MainActivity.TWEET_FILENAME,
								Context.MODE_PRIVATE))));

				for (int resId : MainActivity.sRawTextFeedIds) {
					in = new BufferedReader(new InputStreamReader(
							getResources().openRawResource(resId)));

					String line;
					StringBuffer buffer = new StringBuffer();

					while ((line = in.readLine()) != null) {
						buffer.append(line);
					}

					out.println(buffer);

				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (Resources.NotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (null != in) {
						in.close();
					}
					if (null != out) {
						out.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void setFileAge(long timestamp) {
		File file = new File(sFileName);
		if (file.exists()) {
			file.setLastModified(timestamp);
		}
	}

}
