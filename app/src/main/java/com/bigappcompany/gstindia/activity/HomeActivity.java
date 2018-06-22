package com.bigappcompany.gstindia.activity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.bigappcompany.gstindia.R;
import com.bigappcompany.gstindia.adapter.ViewPagerAdapter;
import com.bigappcompany.gstindia.fragment.HomeFragment;
import com.bigappcompany.gstindia.fragment.NewsFragment;

import java.util.ArrayList;

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 2017-01-11 at 4:15 PM
 */

@SuppressWarnings("ConstantConditions")
public class HomeActivity extends GSTActivity {

	private TabLayout mTabLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setHome(true);

		setContentView(R.layout.activity_home);
		super.onCreate(savedInstanceState);

		String titles[] = getResources().getStringArray(R.array.titles_tab_home);
		ViewPager homeVP = (ViewPager) findViewById(R.id.vp_home);
		ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), getFragments(), titles);
		homeVP.setAdapter(adapter);

		mTabLayout = (TabLayout) findViewById(R.id.tl_home);
		mTabLayout.setupWithViewPager(homeVP, true);
		setupTabIcons();
	}

	private ArrayList<Fragment> getFragments() {
		ArrayList<Fragment> fragments = new ArrayList<>();
		fragments.add(new HomeFragment());
		fragments.add(new NewsFragment());
		//fragments.add(new HomeFragment());

		return fragments;
	}

	@SuppressWarnings("deprecation")
	private void setupTabIcons() {
		mTabLayout.getTabAt(0).setIcon(R.drawable.ic_home_unselected);
		mTabLayout.getTabAt(1).setIcon(R.drawable.ic_news_unselected);
		//mTabLayout.getTabAt(2).setIcon(R.drawable.ic_account_unselected);

		mTabLayout.getTabAt(0).getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
		mTabLayout.getTabAt(1).getIcon().setColorFilter(getResources().getColor(R.color.colorUnselected),
		    PorterDuff.Mode.SRC_IN);
		//mTabLayout.getTabAt(2).getIcon().setColorFilter(getResources().getColor(R.color.colorUnselected),
		  //  PorterDuff.Mode.SRC_IN);

		mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
			@Override
			public void onTabSelected(TabLayout.Tab tab) {
				tab.getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
			}

			@Override
			public void onTabUnselected(TabLayout.Tab tab) {
				tab.getIcon().setColorFilter(getResources().getColor(R.color.colorUnselected), PorterDuff.Mode.SRC_IN);
			}

			@Override
			public void onTabReselected(TabLayout.Tab tab) {

			}
		});
	}
}
