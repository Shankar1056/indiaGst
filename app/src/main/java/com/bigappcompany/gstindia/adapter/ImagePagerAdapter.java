package com.bigappcompany.gstindia.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bigappcompany.gstindia.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.bigappcompany.gstindia.adapter.ImagePagerAdapter.ImageFragment.ARG_IMAGE_URL;

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 2017-02-15 at 3:14 PM
 * @signed_off_by Samuel Robert <sam@spotsoon.com>
 */

public class ImagePagerAdapter extends FragmentPagerAdapter {
	private ArrayList<ImageFragment> mFragmentList;

	public ImagePagerAdapter(FragmentManager fm, ArrayList<String> urlList) {
		super(fm);

		mFragmentList = new ArrayList<>();

		for (String url : urlList) {
			Bundle args = new Bundle();
			args.putString(ARG_IMAGE_URL, url);
			ImageFragment fragment = new ImageFragment();
			fragment.setArguments(args);
			mFragmentList.add(fragment);
		}
	}

	@Override
	public Fragment getItem(int position) {
		return mFragmentList.get(position);
	}

	@Override
	public int getCount() {
		return mFragmentList.size();
	}

	@SuppressWarnings("WeakerAccess")
	public static class ImageFragment extends Fragment {
		static final String ARG_IMAGE_URL = "arg_image_url";

		@Nullable
		@Override
		public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
			String imageUrl = getArguments().getString(ARG_IMAGE_URL);

			ImageView imageView = (ImageView) inflater.inflate(R.layout.fragment_image, container, false);
			Picasso.with(getContext())
			    .load(imageUrl)
			    .fit()
			    .into(imageView);

			return imageView;
		}
	}
}
