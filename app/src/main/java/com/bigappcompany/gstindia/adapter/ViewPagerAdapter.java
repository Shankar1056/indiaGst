package com.bigappcompany.gstindia.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 2017-01-11 at 4:15 PM
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
	private ArrayList<Fragment> mFragments;
	private CharSequence[] mTitles;

	public ViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments, CharSequence[] titles) {
		super(fm);

		mFragments = fragments;
		mTitles = titles;
	}

	@Override
	public Fragment getItem(int position) {
		return mFragments.get(position);
	}

	@Override
	public int getCount() {
		return mFragments.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return mTitles == null ? super.getPageTitle(position) : mTitles[position];
	}
}
