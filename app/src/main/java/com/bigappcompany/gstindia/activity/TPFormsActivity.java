package com.bigappcompany.gstindia.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.bigappcompany.gstindia.R;
import com.bigappcompany.gstindia.adapter.TPFormsAdapter;
import com.bigappcompany.gstindia.api.ApiTask;
import com.bigappcompany.gstindia.api.ApiUrl;
import com.bigappcompany.gstindia.model.TPFormsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TPFormsActivity extends GSTActivity
    implements ApiTask.OnResponseListener, TPFormsAdapter.OnItemClickListener, AdapterView.OnItemSelectedListener {
	private static final String TAG = "TPFormsActivity";
	
	private TPFormsAdapter mAdapter;
	private String mType, mState;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_tpforms);
		super.onCreate(savedInstanceState);
		
		mAdapter = new TPFormsAdapter(this);
		RecyclerView formsRV = (RecyclerView) findViewById(R.id.rv_forms);
		formsRV.setLayoutManager(new LinearLayoutManager(this));
		formsRV.setAdapter(mAdapter);
		
		mType = getIntent().getStringExtra(EXTRA_TYPE);
		final AppCompatSpinner stateACS = (AppCompatSpinner) findViewById(R.id.acs_states);
		
		// REST request
		if (mType.equalsIgnoreCase(IGST)) {
			stateACS.setVisibility(View.GONE);
			new ApiTask(this).execute(ApiUrl.getTPFormsUrl(mType));
		} else {
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
							TPFormsActivity.this,
							R.layout.spinner_text,
							stateList
						    )
						);
						stateACS.setOnItemSelectedListener(TPFormsActivity.this);
					} catch (JSONException e) {
						Log.e(TAG, e.getMessage(), e);
					}
				}
				
				@Override
				public void onFailure() {
					
				}
			}).execute(ApiUrl.URL_STATES);
		}
	}
	
	@Override
	public void onItemClick(TPFormsModel item, int position) {
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getPdfLink()));
		startActivity(browserIntent);
	}
	
	@Override
	public void onDownload(TPFormsModel item, int position) {
		downloadFile(item.getPdfLink(), item.getTitle() + ".pdf");
	}
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		try {
			mState = parent.getItemAtPosition(position).toString();
			JSONObject object = new JSONObject();
			object.put("state", mState);
			object.put("index", 0);
			object.put("offset", 50);
			new ApiTask(this).execute(ApiUrl.getTPFormsUrl(mType), object.toString());
		} catch (JSONException e) {
			Log.e(TAG, e.getMessage(), e);
		}
	}
	
	@Override
	public void onSuccess(JSONObject response) {
		mAdapter.clear();

		try {
			String str_status=response.optString("status");
			if(str_status.equals("-1")){
				Toast.makeText(this, "No records", Toast.LENGTH_SHORT).show();
			}else{
				JSONArray data = response.getJSONArray("data");

				for (int i = 0; i < data.length(); i++) {
					mAdapter.addItem(new TPFormsModel(data.getJSONObject(i)));
				}
			}

		} catch (JSONException e) {
			Log.e(TAG, e.getMessage(), e);
		}
	}
	
	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		
	}
	
	@Override
	public void onFailure() {
		
	}
	
	
}
