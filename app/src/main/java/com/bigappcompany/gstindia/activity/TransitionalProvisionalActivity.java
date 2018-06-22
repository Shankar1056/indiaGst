package com.bigappcompany.gstindia.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.bigappcompany.gstindia.R;
import com.bigappcompany.gstindia.adapter.ViewPagerAdapter;
import com.bigappcompany.gstindia.fragment.TransitionalProvisionalFragment;

import java.util.ArrayList;

public class TransitionalProvisionalActivity extends GSTActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_transitional_provisional);
		super.onCreate(savedInstanceState);

		ViewPager mLawVP = (ViewPager) findViewById(R.id.vp_transitional_provision);
		ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), getFragments(),
		    getResources().getStringArray(R.array.titles_transitional_provision));
		mLawVP.setAdapter(adapter);

		TabLayout tabLayout = (TabLayout) findViewById(R.id.tl_transitional_provision);
		tabLayout.setupWithViewPager(mLawVP);
	}

	private ArrayList<Fragment> getFragments() {
		ArrayList<Fragment> fragments = new ArrayList<>();
		
		for (String type : getResources().getStringArray(R.array.values_transitional_provision)) {
			Bundle args = new Bundle();
			args.putString(EXTRA_TYPE, type);

			Fragment fragment = new TransitionalProvisionalFragment();
			fragment.setArguments(args);

			fragments.add(fragment);
		}

		return fragments;
	}
}
