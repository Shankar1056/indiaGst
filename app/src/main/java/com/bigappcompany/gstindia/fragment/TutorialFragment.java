package com.bigappcompany.gstindia.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigappcompany.gstindia.R;
import com.bigappcompany.gstindia.activity.ArticleActivity;
import com.bigappcompany.gstindia.activity.GSTActivity;
import com.bigappcompany.gstindia.activity.PresentationActivity;
import com.bigappcompany.gstindia.activity.VideoActivity;

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 27 Feb 2017 at 5:59 PM
 */
public class TutorialFragment extends Fragment implements View.OnClickListener {
        public static final String ARG_CATEGORY = "arg_category";
        private String mCategory;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
                mCategory = getArguments().getString(ARG_CATEGORY);

                View rootView = inflater.inflate(R.layout.fragment_tutorial, container, false);

                rootView.findViewById(R.id.tv_article).setOnClickListener(this);
                rootView.findViewById(R.id.tv_videos).setOnClickListener(this);
                rootView.findViewById(R.id.tv_presentations).setOnClickListener(this);

                return rootView;
        }

        @Override
        public void onClick(View v) {
                Class<? extends GSTActivity> activity = null;

                switch (v.getId()) {
                        case R.id.tv_article:
                                activity = ArticleActivity.class;
                                break;

                        case R.id.tv_videos:
                                activity = VideoActivity.class;
                                break;

                        case R.id.tv_presentations:
                                activity = PresentationActivity.class;
                                break;
                }

                Intent intent = new Intent(getContext(), activity);
                intent.putExtra(GSTActivity.EXTRA_CATEGORY, mCategory);
                startActivity(intent);
        }
}
