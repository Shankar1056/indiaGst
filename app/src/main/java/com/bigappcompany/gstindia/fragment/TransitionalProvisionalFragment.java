package com.bigappcompany.gstindia.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigappcompany.gstindia.R;
import com.bigappcompany.gstindia.activity.ProcedureActivity;
import com.bigappcompany.gstindia.activity.TPFormsActivity;

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 01 Mar 2017 at 8:05 PM
 */
public class TransitionalProvisionalFragment extends Fragment implements View.OnClickListener {
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_transitional_provisional, container, false);
		
		rootView.findViewById(R.id.tv_procedure).setOnClickListener(this);
		rootView.findViewById(R.id.tv_forms).setOnClickListener(this);
		
		return rootView;
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_procedure:
				Intent intent = new Intent(getContext(), ProcedureActivity.class);
				intent.putExtras(getArguments());
				startActivity(intent);
				break;
			case R.id.tv_forms:
				intent = new Intent(getContext(), TPFormsActivity.class);
				intent.putExtras(getArguments());
				startActivity(intent);
				break;
		}
	}
}
