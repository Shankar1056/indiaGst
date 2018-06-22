package com.bigappcompany.gstindia.activity;

import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.bigappcompany.gstindia.R;
import com.bigappcompany.gstindia.adapter.ActAdapter;
import com.bigappcompany.gstindia.api.ApiTask;
import com.bigappcompany.gstindia.api.ApiUrl;
import com.bigappcompany.gstindia.api.JsonParser;
import com.bigappcompany.gstindia.model.ActModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EnrollmentActivity extends GSTActivity
    implements ActAdapter.OnItemClickListener, AdapterView.OnItemSelectedListener, ApiTask.OnResponseListener {
	private static final String TAG = "EnrollmentActivity";
	private ActAdapter mAdapter;
	private String mState;
	private boolean isEnrollment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_enrollment);
		super.onCreate(savedInstanceState);
		
		// Read type from intent
		isEnrollment = getIntent().getBooleanExtra(EXTRA_IS_ENROLLMENT, true);
		
		// Set adapter
		mAdapter = new ActAdapter(this);
		RecyclerView actRV = (RecyclerView) findViewById(R.id.rv_enrollment);
		actRV.setLayoutManager(new LinearLayoutManager(this));
		actRV.setAdapter(mAdapter);
		
		final AppCompatSpinner stateACS = (AppCompatSpinner) findViewById(R.id.acs_states);
		new ApiTask(new ApiTask.OnResponseListener() {
			@Override
			public void onSuccess(JSONObject response) {
				try {
					JSONArray data = response.getJSONArray("data");
					ArrayList<String> stateList = new ArrayList<>();
					for (int i = 0; i < data.length(); i++) {
						stateList.add(data.getJSONObject(i).getString("state_name"));
					}
					
					stateACS.setAdapter(
					    new ArrayAdapter<>(
						EnrollmentActivity.this,
						R.layout.spinner_text,
						stateList
					    )
					);
					stateACS.setOnItemSelectedListener(EnrollmentActivity.this);
				} catch (JSONException e) {
					Log.e(TAG, e.getMessage(), e);
				}
			}
			
			@Override
			public void onFailure() {
				
			}
		}).execute(ApiUrl.URL_STATES);
	}
	
	@Override
	public void onItemClick(ActModel item, int position) {
		
	}
	
	@Override
	public void onDownload(ActModel item, int position) {
		
	}
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		try {
			mState = parent.getItemAtPosition(position).toString();
			new ApiTask(this).execute(isEnrollment ? ApiUrl.URL_ENROLLMENT :
			    ApiUrl.URL_NEW_REGISTRATION, getPages());
		} catch (JSONException e) {
			Log.e(TAG, e.getMessage(), e);
		}
	}
	
	private String getPages() throws JSONException {
		JSONObject object = new JSONObject();
		object.put("state", mState);
		object.put("index", 0);
		object.put("offset", 10);
		return object.toString();
	}
	
	
	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		
	}
	
	@Override
	public void onSuccess(JSONObject response) {
		try {
			mAdapter.clearItems();
			JSONArray data = response.getJSONArray(JsonParser.DATA);
			
			for (int i = 0; i < data.length(); i++) {
				mAdapter.addItem(new ActModel(data.getJSONObject(i)));
			}
		} catch (JSONException e) {
			Log.e(TAG, e.getMessage(), e);
		}
	}
	
	@Override
	public void onFailure() {
		
	}
}
