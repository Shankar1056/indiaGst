package com.bigappcompany.gstindia.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigappcompany.gstindia.R;
import com.bigappcompany.gstindia.activity.GSTActivity;
import com.bigappcompany.gstindia.adapter.OtherAdapter;
import com.bigappcompany.gstindia.api.ApiTask;
import com.bigappcompany.gstindia.api.ApiUrl;
import com.bigappcompany.gstindia.model.OtherModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 2017-02-25 at 11:50 AM
 */

public class OtherFragment extends Fragment implements ApiTask.OnResponseListener, OtherAdapter.OnItemClickListener {
	private static final String TAG = "OtherFragment";
	private OtherAdapter mAdapter;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		mAdapter = new OtherAdapter(this);

		RecyclerView otherRV = (RecyclerView) inflater.inflate(R.layout.fragment_other, container, false);
		otherRV.setLayoutManager(new LinearLayoutManager(getContext()));
		otherRV.setAdapter(mAdapter);

		new ApiTask(this).execute(ApiUrl.URL_OTHER_LAWS);

		return otherRV;
	}

	@Override
	public void onSuccess(JSONObject response) {
		try {
			JSONArray data = response.getJSONArray("data");

			for (int i = 0; i < data.length(); i++) {
				mAdapter.addItem(new OtherModel(data.getJSONObject(i)));
			}
		} catch (JSONException e) {
			Log.e(TAG, "onSuccess: ", e);
		}
	}

	@Override
	public void onFailure() {

	}

	@Override
	public void onItemClick(OtherModel item, int position) {
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getPdfUrl()));
		startActivity(browserIntent);
	}

	@Override
	public void onDownload(OtherModel item, int position) {
		((GSTActivity) getActivity()).downloadFile(item.getPdfUrl(), item.getTitle() + ".pdf");
	}
}
