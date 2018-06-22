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
import android.widget.Toast;

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

public class ActActivity extends GSTActivity implements
        ApiTask.OnResponseListener, ActAdapter.OnItemClickListener, AdapterView.OnItemSelectedListener {
    private static final String TAG = "ActActivity";

    private ActAdapter mAdapter;
    private String mType, mState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_act);
        super.onCreate(savedInstanceState);

        // Read type from intent
        mType = getIntent().getStringExtra(EXTRA_TYPE);

        // Set adapter
        mAdapter = new ActAdapter(this);
        RecyclerView actRV = (RecyclerView) findViewById(R.id.rv_act);
        actRV.setLayoutManager(new LinearLayoutManager(this));
        actRV.setAdapter(mAdapter);

        // REST request
        if (mType.equals(IGST) || mType.equals(UTGST) || mType.equals(CGST)) {
            findViewById(R.id.acs_states).setVisibility(View.GONE);
            new ApiTask(this).execute(ApiUrl.getActsUrl(mType));
        } else {
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
                                        ActActivity.this,
                                        R.layout.spinner_text,
                                        stateList
                                )
                        );
                        stateACS.setOnItemSelectedListener(ActActivity.this);
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
    public void onSuccess(JSONObject response) {
        try {
            mAdapter.clearItems();
            String str_status = response.optString("status");
            if (str_status.equals("-1")) {
                Toast.makeText(this, "No records", Toast.LENGTH_SHORT).show();
            } else {
                JSONArray data = response.getJSONArray(JsonParser.DATA);

                for (int i = 0; i < data.length(); i++) {
                    mAdapter.addItem(new ActModel(data.getJSONObject(i)));
                }
            }

        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    @Override
    public void onFailure() {

    }

    @Override
    public void onItemClick(ActModel item, int position) {
        Intent intent = new Intent(this, ChapterActivity.class);
        intent.putExtra(EXTRA_TYPE, mType);
        intent.putExtra(EXTRA_STATE, mState);
        intent.putExtra(EXTRA_ACT_NAME, item.getActName());
        startActivity(intent);
    }

    @Override
    public void onDownload(ActModel item, int position) {
        downloadFile(item.getPdfLink(), item.getActName()+ ".pdf");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        try {
            mState = parent.getItemAtPosition(position).toString();
            JSONObject object = new JSONObject();
            object.put("state", mState);
            new ApiTask(this).execute(ApiUrl.getActsUrl(mType), object.toString());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
