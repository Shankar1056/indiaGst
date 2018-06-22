package com.bigappcompany.gstindia.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigappcompany.gstindia.R;
import com.bigappcompany.gstindia.activity.ActActivity;
import com.bigappcompany.gstindia.activity.FormsActivity;
import com.bigappcompany.gstindia.activity.GSTActivity;
import com.bigappcompany.gstindia.activity.NotificationActivity;
import com.bigappcompany.gstindia.activity.RulesActivity;

import static com.bigappcompany.gstindia.activity.GSTActivity.EXTRA_TYPE;

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 2017-02-24 at 6:05 PM
 */

public class LawFragment extends Fragment implements View.OnClickListener {
    private String mType;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mType = getArguments().getString(EXTRA_TYPE);

        int layoutId = getResources().getIdentifier(
                String.format("fragment_%s_law", mType),
                "layout",
                getActivity().getPackageName()
        );

        View rootView = inflater.inflate(layoutId, container, false);

        rootView.findViewById(R.id.tv_act).setOnClickListener(this);
        rootView.findViewById(R.id.tv_rules).setOnClickListener(this);
        rootView.findViewById(R.id.tv_forms).setOnClickListener(this);
        rootView.findViewById(R.id.tv_notification).setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_act:
                Intent intent = new Intent(getContext(), ActActivity.class);
                intent.putExtra(GSTActivity.EXTRA_TYPE, mType);
                startActivity(intent);
                break;

            case R.id.tv_rules:
                intent = new Intent(getContext(), RulesActivity.class);
                intent.putExtra(GSTActivity.EXTRA_TYPE, mType);
                startActivity(intent);
                break;

            case R.id.tv_forms:
                intent = new Intent(getContext(), FormsActivity.class);
                intent.putExtra(GSTActivity.EXTRA_TYPE, mType);
                startActivity(intent);
                break;

            case R.id.tv_notification:
                intent = new Intent(getContext(), NotificationActivity.class);
                intent.putExtra(GSTActivity.EXTRA_TYPE, mType);
                startActivity(intent);
                break;
        }
    }
}
