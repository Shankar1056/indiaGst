package com.bigappcompany.gstindia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.bigappcompany.gstindia.R;
import com.bigappcompany.gstindia.adapter.ProcedureAdapter;
import com.bigappcompany.gstindia.api.ApiTask;
import com.bigappcompany.gstindia.api.ApiUrl;
import com.bigappcompany.gstindia.model.ProcedureModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProcedureActivity extends GSTActivity implements ApiTask.OnResponseListener, ProcedureAdapter.OnItemClickListener, AdapterView.OnItemSelectedListener {
	private static final String TAG = "ProcedureActivity";
	private ProcedureAdapter mAdapter;
	private AppCompatSpinner stateACS;
	private String mType, mState;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_procedure);
		super.onCreate(savedInstanceState);
		
		mAdapter = new ProcedureAdapter(this);
		RecyclerView procedureRV = (RecyclerView) findViewById(R.id.rv_procedure);
		procedureRV.setLayoutManager(new LinearLayoutManager(this));
		procedureRV.setAdapter(mAdapter);
		
		stateACS = (AppCompatSpinner) findViewById(R.id.acs_states);
		mType = getIntent().getStringExtra(EXTRA_TYPE);
		
		// REST request
		if (mType.equalsIgnoreCase(IGST)) {
			stateACS.setVisibility(View.GONE);
			new ApiTask(this).execute(ApiUrl.getProcedureUrl(mType));
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
							ProcedureActivity.this,
							R.layout.spinner_text,
							stateList
						    )
						);
						stateACS.setOnItemSelectedListener(ProcedureActivity.this);
					} catch (JSONException e) {
						Log.e(TAG, e.getMessage(), e);
					}
				}
				
				@Override
				public void onFailure() {
					finish();
				}
			}).execute(ApiUrl.URL_STATES);
		}
	}
	
	@Override
	public void onItemClick(ProcedureModel item) {
		Intent intent = new Intent(this, WebActivity.class);
		intent.putExtra(EXTRA_HTML, item.getDesc());
		intent.putExtra(EXTRA_IS_URL, false);
		startActivity(intent);
	}
	
	@Override
	public void onSuccess(JSONObject response) {
		try {
			JSONArray data = response.getJSONArray("data");
			
			for (int i = 0; i < data.length(); i++) {
				mAdapter.addItem(new ProcedureModel(data.getJSONObject(i)));
			}
		} catch (JSONException e) {
			Log.e(TAG, e.getMessage(), e);
		}
	}
	
	@Override
	public void onDownload(ProcedureModel item) {
		downloadFile(item.getPdfLink(), item.getTitle() + ".pdf");
	}
	
	@Override
	public void onFailure() {
		finish();
	}
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		try {
			mState = parent.getItemAtPosition(position).toString();
			JSONObject object = new JSONObject();
			object.put("state", mState);
			object.put("index", 0);
			object.put("offset", 50);
			new ApiTask(this).execute(ApiUrl.getProcedureUrl(mType), object.toString());
		} catch (JSONException e) {
			Log.e(TAG, e.getMessage(), e);
		}
	}
	
	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		
	}
	
	
}
