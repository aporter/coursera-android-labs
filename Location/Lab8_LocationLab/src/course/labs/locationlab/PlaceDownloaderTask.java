package course.labs.locationlab;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.util.Log;

public class PlaceDownloaderTask extends AsyncTask<Location, Void, PlaceRecord> {

	// False if you don't have network access
	private boolean mHasNetwork = false;

	// Log TAG
	private static final String TAG = "PlaceDownloaderTask";

	// Optional TODO - Put your www.geonames.org account name here if you want to
	// use the geonames.org web service. To use this service, you must register for a free account.
	private static String USERNAME = "YOUR_ACCOUNT_NAME";

	private HttpURLConnection mHttpUrl;
	private WeakReference<PlaceViewActivity> mParent;
	private static Bitmap sStubBitmap = null;
	private static final Location sMockLoc1 = new Location(
			LocationManager.NETWORK_PROVIDER);
	private static final Location sMockLoc2 = new Location(
			LocationManager.NETWORK_PROVIDER);
	private static final Location sMockLoc3 = new Location(
			LocationManager.NETWORK_PROVIDER);
	private static String sMockCountryName1, sMockCountryNameInvalid,
			sMockPlaceName1, sMockPlaceName2, sMockPlaceNameInvalid;

	public PlaceDownloaderTask(PlaceViewActivity parent, boolean hasNetwork) {
		super();

		mParent = new WeakReference<PlaceViewActivity>(parent);
		mHasNetwork = hasNetwork;

		if (null != parent) {
			sStubBitmap = BitmapFactory.decodeResource(parent.getResources(),
					R.drawable.stub);

			sMockLoc1.setLatitude(37.422);
			sMockLoc1.setLongitude(-122.084);
			sMockCountryName1 = parent
					.getString(R.string.mock_name_united_states_string);
			sMockPlaceName1 = parent.getString(R.string.the_greenhouse_string);
			sMockLoc2.setLatitude(38.996667);
			sMockLoc2.setLongitude(-76.9275);
			sMockPlaceName2 = parent.getString(R.string.berwyn_string);
			sMockLoc3.setLatitude(0);
			sMockLoc3.setLongitude(0);
			sMockCountryNameInvalid = "";
			sMockPlaceNameInvalid = "";

		}
	}

	@Override
	protected PlaceRecord doInBackground(Location... location) {
		PlaceRecord place = null;

		if (mHasNetwork) {

			// Get the PlaceBadge information
			place = getPlaceFromURL(generateURL(USERNAME, location[0]));
			place.setLocation(location[0]);

			if ("" != place.getCountryName()) {
				place.setFlagBitmap(getFlagFromURL(place.getFlagUrl()));
			} else {
				place.setFlagBitmap(sStubBitmap);
			}
		} else {

			place = new PlaceRecord();
			place.setLocation(location[0]);
			place.setFlagBitmap(sStubBitmap);
			
			if (place.intersects(sMockLoc1)) {
				place.setCountryName(sMockCountryName1);
				place.setPlace(sMockPlaceName1);
			} else if (place.intersects(sMockLoc2)) {
				place.setCountryName(sMockCountryName1);
				place.setPlace(sMockPlaceName2);
			} else {
				place.setCountryName(sMockCountryNameInvalid);
				place.setPlace(sMockPlaceNameInvalid);
			}
		}

		return place;

	}

	@Override
	protected void onPostExecute(PlaceRecord result) {

		// If you've acquired Place data and the parent is non-null
		// call parent to add the PlaceBadge
		if (null != result && null != mParent.get()) {
			mParent.get().addNewPlace(result);
		}
	}

	private PlaceRecord getPlaceFromURL(String... params) {
		String result = null;
		BufferedReader in = null;

		try {
			URL url = new URL(params[0]);
			mHttpUrl = (HttpURLConnection) url.openConnection();
			in = new BufferedReader(new InputStreamReader(
					mHttpUrl.getInputStream()));

			StringBuffer sb = new StringBuffer("");
			String line = "";
			while ((line = in.readLine()) != null) {
				sb.append(line + "\n");
			}
			result = sb.toString();

		} catch (MalformedURLException e) {
			Log.e(TAG, "MalformedURLException");
		} catch (IOException e) {
			Log.e(TAG, "IOException");
		} finally {
			try {
				if (null != in) {
					in.close();
				}
			} catch (IOException e) {
				Log.e(TAG, "IOException");
			}
			mHttpUrl.disconnect();
		}
		return placeDataFromXml(result);
	}

	private Bitmap getFlagFromURL(String flagUrl) {
		InputStream in = null;
		Log.i("temp", flagUrl);
		try {
			URL url = new URL(flagUrl);
			mHttpUrl = (HttpURLConnection) url.openConnection();
			in = mHttpUrl.getInputStream();
			return BitmapFactory.decodeStream(in);
		} catch (MalformedURLException e) {
			Log.e(TAG, "MalformedURLException");
		} catch (IOException e) {
			Log.e(TAG, "IOException");
		} finally {
			try {
				if (null != in) {
					in.close();
				}
			} catch (IOException e) {
				Log.e(TAG, "IOException");
			}
			mHttpUrl.disconnect();
		}

		return BitmapFactory.decodeResource(mParent.get().getResources(),
				R.drawable.stub);
	}

	private static PlaceRecord placeDataFromXml(String xmlString) {
		DocumentBuilder builder;
		String countryName = "";
		String countryCode = "";
		String placeName = "";

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try {
			builder = factory.newDocumentBuilder();
			Document document = builder.parse(new InputSource(new StringReader(
					xmlString)));
			NodeList list = document.getDocumentElement().getChildNodes();
			for (int i = 0; i < list.getLength(); i++) {
				Node curr = list.item(i);

				NodeList list2 = curr.getChildNodes();

				for (int j = 0; j < list2.getLength(); j++) {

					Node curr2 = list2.item(j);
					if (curr2.getNodeName() != null) {
						if (curr2.getNodeName().equals("countryName")) {
							countryName = curr2.getTextContent();
						} else if (curr2.getNodeName().equals("countryCode")) {
							countryCode = curr2.getTextContent();
						} else if (curr2.getNodeName().equals("name")) {
							placeName = curr2.getTextContent();
						}
					}
				}
			}
		} catch (DOMException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new PlaceRecord(generateFlagURL(countryCode), countryName,
				placeName);
	}

	// URL for acquiring Place data
	private static String generateURL(String username, Location location) {
		return "http://www.geonames.org/findNearbyPlaceName?username="
				+ username + "&style=full&lat=" + location.getLatitude()
				+ "&lng=" + location.getLongitude();
	}

	// URL for acquiring flag image based on country code
	private static String generateFlagURL(String countryCode) {
		return "http://www.geonames.org/flags/x/"
				+ countryCode.toLowerCase(Locale.US) + ".gif";
	}

}
