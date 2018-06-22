package com.bigappcompany.gstindia.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bigappcompany.gstindia.R;
import com.bigappcompany.gstindia.activity.AboutUsActivity;
import com.bigappcompany.gstindia.activity.ContactUs;
import com.bigappcompany.gstindia.activity.LawActivity;
import com.bigappcompany.gstindia.activity.RegistrationActivity;
import com.bigappcompany.gstindia.activity.TransitionalProvisionalActivity;
import com.bigappcompany.gstindia.activity.TutorialActivity;
import com.bigappcompany.gstindia.activity.WebInterfaceActivity;
import com.bigappcompany.gstindia.adapter.ImagePagerAdapter;
import com.bigappcompany.gstindia.api.ApiTask;
import com.bigappcompany.gstindia.api.ApiUrl;
import com.bigappcompany.gstindia.api.JsonParser;
import com.bigappcompany.gstindia.view.ViewPagerCustomDuration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.bigappcompany.gstindia.activity.GSTActivity.EXTRA_URL;

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 2017-02-13 at 12:40 PM
 */

@SuppressWarnings("ConstantConditions")
public class HomeFragment extends Fragment implements Runnable, ApiTask.OnResponseListener, View.OnClickListener {
	private static final String TAG = "HomeFragment";

	private ViewPagerCustomDuration mSliderVP;
	private Handler mHandler;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_home, container, false);

		// Custom ViewPager for less scroller animation duration
		mSliderVP = (ViewPagerCustomDuration) rootView.findViewById(R.id.vp_slider_home);
		mSliderVP.setScrollDurationFactor(4);
		mHandler = new Handler();

		rootView.findViewById(R.id.tv_igst).setOnClickListener(this);
		rootView.findViewById(R.id.tv_sgst).setOnClickListener(this);
		rootView.findViewById(R.id.tv_cgst).setOnClickListener(this);
		rootView.findViewById(R.id.tv_utgst).setOnClickListener(this);
		rootView.findViewById(R.id.tv_other).setOnClickListener(this);

		rootView.findViewById(R.id.tv_registration).setOnClickListener(this);
		rootView.findViewById(R.id.tv_calculator).setOnClickListener(this);
		rootView.findViewById(R.id.tv_rate).setOnClickListener(this);
		rootView.findViewById(R.id.tv_tutorials).setOnClickListener(this);
		rootView.findViewById(R.id.tv_transitional_provisional).setOnClickListener(this);
		rootView.findViewById(R.id.tv_about_us).setOnClickListener(this);
		rootView.findViewById(R.id.tv_contact_us).setOnClickListener(this);

		// REST request for slider images
		new ApiTask(this).execute(ApiUrl.URL_HOME_SLIDER_IMAGES);

		return rootView;
	}

	@Override
	public void onResume() {
		super.onResume();

		mHandler.postDelayed(this, 3000);
	}

	@Override
	public void onPause() {
		super.onPause();

		mHandler.removeCallbacks(this);
	}

	/**
	 * throws divided by zero {@link ArithmeticException} if the #getChildCount() returns 0
	 */
	@Override
	public void run() {
		try {
			int nextPosition = (mSliderVP.getCurrentItem() + 1) % mSliderVP.getChildCount();
			mSliderVP.setCurrentItem(nextPosition, true);
			mHandler.postDelayed(this, 3000);
		} catch (ArithmeticException e) {
			Log.w(TAG, e.getMessage(), e);
		}
	}

	@Override
	public void onSuccess(JSONObject response) {
		try {
			ArrayList<String> urlList = new ArrayList<>();
			JSONArray data = response.getJSONArray(JsonParser.DATA);

			for (int i = 0; i < data.length(); i++) {
				urlList.add(ApiUrl.URL_BASE + data.getJSONObject(i).getString(JsonParser.IMAGE_LINK));
			}

			ImagePagerAdapter adapter = new ImagePagerAdapter(getChildFragmentManager(), urlList);
			mSliderVP.setAdapter(adapter);
		} catch (JSONException e) {
			Log.e(TAG, e.getMessage(), e);
		}
	}

	@Override
	public void onFailure() {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_igst:
			case R.id.tv_sgst:
			case R.id.tv_cgst:
			case R.id.tv_utgst:
			case R.id.tv_other:
				Intent intent = new Intent(getContext(), LawActivity.class);
				intent.putExtra(LawActivity.EXTRA_LAW_TYPE, Integer.parseInt(v.getTag().toString()));
				startActivity(intent);
				break;
			case R.id.tv_tutorials:
				startActivity(new Intent(getContext(), TutorialActivity.class));
				break;
			case R.id.tv_registration:
				startActivity(new Intent(getContext(), RegistrationActivity.class));
				break;
			case R.id.tv_transitional_provisional:
				startActivity(new Intent(getContext(), TransitionalProvisionalActivity.class));
				break;
			case R.id.tv_calculator:
				intent = new Intent(getContext(), WebInterfaceActivity.class);
				intent.putExtra(Intent.EXTRA_TITLE, getString(R.string.title_calculator));
				intent.putExtra(EXTRA_URL, ApiUrl.URL_CALCULATOR);
				startActivity(intent);
				break;
			case R.id.tv_rate:
				intent = new Intent(getContext(), WebInterfaceActivity.class);
				intent.putExtra(Intent.EXTRA_TITLE, getString(R.string.title_rate));
				intent.putExtra(EXTRA_URL, ApiUrl.URL_RATE_FINDER);
				startActivity(intent);
				break;
			case R.id.tv_about_us:
				intent = new Intent(getContext(), AboutUsActivity.class);
				startActivity(intent);
				break;
			case R.id.tv_contact_us:
				intent = new Intent(getContext(), ContactUs.class);
				startActivity(intent);
				break;
			default:
				Toast.makeText(getContext(), "Not yet implemented", Toast.LENGTH_SHORT).show();
		}
	}
}
