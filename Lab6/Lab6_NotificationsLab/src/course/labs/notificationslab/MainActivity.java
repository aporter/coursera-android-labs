package course.labs.notificationslab;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends Activity implements SelectionListener,
		DownloadFinishedListener {

	private static final String TAG_NAME = "name";
	private static final String TAG_USER = "user";
	private static final String TAG_TEXT = "text";
	private static final String TAG_FRIENDS_FRAGMENT = "friends_fragment";
	private static final String TAG_FEED_FRAGMENT = "feed_fragment";
	private static final String TAG_DOWNLOADER_FRAGMENT = "downloader_fragment";
	private static final String TAG_IS_DATA_AVAILABLE = "is_data_available";
	private static final String TAG_PROCESSED_FEEDS = "processed_feeds";
	static final String TAG_TWEET_DATA = "data";
	static final String TAG_FRIEND_RES_IDS = "friends";

	public static final String TWEET_FILENAME = "tweets.txt";
	public final static String[] FRIENDS_NAMES = { "taylorswift13",
			"msrebeccablack", "ladygaga" };
	public static final int IS_ALIVE = Activity.RESULT_FIRST_USER;
	public static final String DATA_REFRESHED_ACTION = "course.labs.notificationslabnew.DATA_REFRESHED";
	private static final String TAG = "Lab-Notifications";

	// Raw feed file IDs used to reference stored tweet data
	public static final ArrayList<Integer> sRawTextFeedIds = new ArrayList<Integer>(
			Arrays.asList(R.raw.tswift, R.raw.rblack, R.raw.lgaga));

	private FragmentManager mFragmentManager;
	private FriendsFragment mFriendsFragment;
	private FeedFragment mFeedFragment;
	private DownloaderTaskFragment mDownloaderFragment;
	private boolean mIsInteractionEnabled;
	private String[] mFormattedFeeds = new String[sRawTextFeedIds.size()];;
	private boolean mIsFresh;
	private BroadcastReceiver mRefreshReceiver;
	private static final long TWO_MIN = 2 * 60 * 1000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mFragmentManager = getFragmentManager();

		// Reset instance state on reconfiguration
		if (null != savedInstanceState) {
			restoreState(savedInstanceState);
		} else {
			setupFragments();
		}
	}

	// One time setup of UI and retained (headless) Fragment
	private void setupFragments() {
		installFriendsFragment();

		// The feed is fresh if it was downloaded less than 2 minutes ago
		mIsFresh = (System.currentTimeMillis() - getFileStreamPath(
				TWEET_FILENAME).lastModified()) < TWO_MIN;
		if (!mIsFresh) {
			installDownloaderTaskFragment();

			// TODO: Show a Toast message displaying
			// R.string.download_in_progress string


			
			
			// Set up a BroadcastReceiver to receive an Intent when download
			// finishes.
			mRefreshReceiver = new BroadcastReceiver() {
				@Override
				public void onReceive(Context context, Intent intent) {

					// TODO:
					// Check to make sure this is an ordered broadcast
					// Let sender know that the Intent was received
					// by setting result code to MainActivity.IS_ALIVE


					
					
					
				}
			};

		} else {
			// Process Twitter data taken from stored file
			parseJSON(loadTweetsFromFile());

			// Enable user interaction
			mIsInteractionEnabled = true;
		}
	}

	// Add Friends Fragment to Activity
	private void installFriendsFragment() {

		// Make new Fragment
		mFriendsFragment = new FriendsFragment();

		// Give Fragment to the FragmentManager
		FragmentTransaction transaction = mFragmentManager.beginTransaction();
		transaction.replace(R.id.fragment_container, mFriendsFragment,
				TAG_FRIENDS_FRAGMENT);
		transaction.commit();
	}

	// Add DownloaderTaskFragment to Activity
	private void installDownloaderTaskFragment() {

		// Make new Fragment
		mDownloaderFragment = new DownloaderTaskFragment();

		// Set DownloaderTaskFragment arguments
		Bundle args = new Bundle();
		args.putIntegerArrayList(TAG_FRIEND_RES_IDS, sRawTextFeedIds);
		mDownloaderFragment.setArguments(args);

		// Give Fragment to the FragmentManager
		mFragmentManager.beginTransaction()
				.add(mDownloaderFragment, TAG_DOWNLOADER_FRAGMENT).commit();
	}

	// Register the BroadcastReceiver
	@Override
	protected void onResume() {
		super.onResume();

		// TODO:
		// Register the BroadcastReceiver to receive a
		// DATA_REFRESHED_ACTION broadcast

		
		
		
	}

	@Override
	protected void onPause() {

		// TODO:
		// Unregister the BroadcastReceiver if it has been registered
		// Note: check that mRefreshReceiver is not null before attempting to
		// unregister in order to work around an Instrumentation issue


		
		
		
		super.onPause();

	}

	/*
	 * DownloadFinishedListener method
	 */

	// Called back by DownloaderTask after data has been loaded
	public void notifyDataRefreshed(String[] feeds) {

		// Process downloaded data
		parseJSON(feeds);

		// Enable user interaction
		mIsInteractionEnabled = true;
		allowUserClicks();

	};

	// Enable user interaction with FriendFragment
	private void allowUserClicks() {
		mFriendsFragment.setAllowUserClicks(true);
	}

	/*
	 * SelectionListener methods
	 */

	// Report whether users interaction is enabled
	public boolean canAllowUserClicks() {
		return mIsInteractionEnabled;
	}

	// Installs the FeedFragment when a Friend name is
	// selected in the FriendsFragment
	@Override
	public void onItemSelected(int position) {
		installFeedFragment(mFormattedFeeds[position]);
	}

	// Add FeedFragment to Activity
	private void installFeedFragment(String tweetData) {
		// Make new Fragment
		mFeedFragment = new FeedFragment();

		// Set Fragment arguments
		Bundle args = new Bundle();
		args.putString(TAG_TWEET_DATA, tweetData);
		mFeedFragment.setArguments(args);

		// Give Fragment to the FragmentManager
		FragmentTransaction transaction = mFragmentManager.beginTransaction();
		transaction.replace(R.id.fragment_container, mFeedFragment,
				TAG_FEED_FRAGMENT);
		transaction.addToBackStack(null);
		transaction.commit();
	}

	@Override
	protected void onSaveInstanceState(Bundle savedInstanceState) {
		if (null != mFriendsFragment) {
			savedInstanceState.putString(TAG_FRIENDS_FRAGMENT,
					mFriendsFragment.getTag());
		}
		if (null != mFeedFragment) {
			savedInstanceState.putString(TAG_FEED_FRAGMENT,
					mFeedFragment.getTag());
		}
		if (null != mDownloaderFragment) {
			savedInstanceState.putString(TAG_DOWNLOADER_FRAGMENT,
					mDownloaderFragment.getTag());
		}
		savedInstanceState.putBoolean(TAG_IS_DATA_AVAILABLE,
				mIsInteractionEnabled);
		savedInstanceState.putStringArray(TAG_PROCESSED_FEEDS, mFormattedFeeds);

		super.onSaveInstanceState(savedInstanceState);

	}

	// Restore saved instance state
	private void restoreState(Bundle savedInstanceState) {

		// Fragments tags were saved in onSavedInstanceState
		mFriendsFragment = (FriendsFragment) mFragmentManager
				.findFragmentByTag(savedInstanceState
						.getString(TAG_FRIENDS_FRAGMENT));

		mFeedFragment = (FeedFragment) mFragmentManager
				.findFragmentByTag(savedInstanceState
						.getString(TAG_FEED_FRAGMENT));

		mDownloaderFragment = (DownloaderTaskFragment) mFragmentManager
				.findFragmentByTag(savedInstanceState
						.getString(TAG_DOWNLOADER_FRAGMENT));

		mIsInteractionEnabled = savedInstanceState
				.getBoolean(TAG_IS_DATA_AVAILABLE);
		if (mIsInteractionEnabled) {
			mFormattedFeeds = savedInstanceState
					.getStringArray(TAG_PROCESSED_FEEDS);
		}
	}

	// Convert raw data (in JSON format) into text for display
	private void parseJSON(String[] feeds) {
		JSONArray[] JSONFeeds = new JSONArray[feeds.length];
		for (int i = 0; i < JSONFeeds.length; i++) {
			try {
				JSONFeeds[i] = new JSONArray(feeds[i]);
			} catch (JSONException e) {
				e.printStackTrace();
			}

			String name = "";
			String tweet = "";
			JSONArray tmp = JSONFeeds[i];

			// string buffer for feeds
			StringBuffer tweetRec = new StringBuffer("");
			for (int j = 0; j < tmp.length(); j++) {
				try {
					tweet = tmp.getJSONObject(j).getString(TAG_TEXT);
					JSONObject user = (JSONObject) tmp.getJSONObject(j).get(
							TAG_USER);
					name = user.getString(TAG_NAME);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				tweetRec.append(name + " - " + tweet + "\n\n");
			}
			mFormattedFeeds[i] = tweetRec.toString();
		}
	}

	// Retrieve feeds text from a file
	// Store them in mRawTextFeed[]

	private String[] loadTweetsFromFile() {
		BufferedReader reader = null;
		ArrayList<String> rawFeeds = new ArrayList<String>();
		try {
			FileInputStream fis = openFileInput(TWEET_FILENAME);
			reader = new BufferedReader(new InputStreamReader(fis));
			String s = null;
			int i = 0;
			while (null != (s = reader.readLine())) {
				rawFeeds.add(i, s);
				i++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != reader) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return rawFeeds.toArray(new String[rawFeeds.size()]);
	}
}
