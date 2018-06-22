package com.bigappcompany.gstindia.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 27 Feb 2017 at 6:06 PM
 */
public class FragmentAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> mItemList;
        private ArrayList<String> mTitleList;

        public FragmentAdapter(FragmentManager fm) {
                super(fm);

                mItemList = new ArrayList<>();
                mTitleList = new ArrayList<>();
        }

        public void clear() {
                mItemList.clear();
                mTitleList.clear();
                notifyDataSetChanged();
        }

        @Override
        public Fragment getItem(int position) {
                return mItemList.get(position);
        }

        @Override
        public int getCount() {
                return mItemList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
                if (mTitleList.size() > 0) {
                        return mTitleList.get(position);
                }

                return super.getPageTitle(position);
        }

        public void addItem(Fragment fragment, String title) {
                mItemList.add(fragment);
                mTitleList.add(title);
                notifyDataSetChanged();
        }
}
