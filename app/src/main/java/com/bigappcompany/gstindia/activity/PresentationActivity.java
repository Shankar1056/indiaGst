package com.bigappcompany.gstindia.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.bigappcompany.gstindia.R;
import com.bigappcompany.gstindia.adapter.PresentationAdapter;
import com.bigappcompany.gstindia.api.ApiTask;
import com.bigappcompany.gstindia.api.ApiUrl;
import com.bigappcompany.gstindia.model.PresentationModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PresentationActivity extends GSTActivity
    implements ApiTask.OnResponseListener, PresentationAdapter.OnItemClickListener {
	private static final String TAG = "PresentationActivity";
	private PresentationAdapter mAdapter;
	private String mType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_presentation);
		super.onCreate(savedInstanceState);

		mAdapter = new PresentationAdapter(this);
		RecyclerView presentationRV = (RecyclerView) findViewById(R.id.rv_presentation);
		presentationRV.setLayoutManager(new LinearLayoutManager(this));
		presentationRV.setAdapter(mAdapter);

		mType = getIntent().getStringExtra(EXTRA_CATEGORY);
		try {
			new ApiTask(this).execute(ApiUrl.URL_PRESENTATION, getPages(0, 10));
		} catch (JSONException e) {
			Log.e(TAG, e.getMessage(), e);
		}
	}

	private String getPages(int index, int offset) throws JSONException {
		JSONObject object = new JSONObject();
		object.put("index", index);
		object.put("offset", offset);
		object.put("tutorial_type", mType);
		return object.toString();
	}

	@Override
	public void onSuccess(JSONObject response) {
		try {
			JSONArray data = response.getJSONArray("data");

			for (int i = 0; i < response.length(); i++) {
				mAdapter.addItem(new PresentationModel(data.getJSONObject(i)));
			}
		} catch (JSONException e) {
			Log.e(TAG, e.getMessage(), e);
		}
	}

	@Override
	public void onFailure() {

	}

	@Override
	public void onItemClick(PresentationModel item) {
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getPresentationUrl()));
		startActivity(browserIntent);
	}
}
