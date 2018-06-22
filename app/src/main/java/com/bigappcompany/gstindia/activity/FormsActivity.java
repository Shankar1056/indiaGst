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
import com.bigappcompany.gstindia.adapter.FormsAdapter;
import com.bigappcompany.gstindia.api.ApiTask;
import com.bigappcompany.gstindia.api.ApiUrl;
import com.bigappcompany.gstindia.model.FormsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FormsActivity extends GSTActivity
        implements FormsAdapter.OnItemClickListener, ApiTask.OnResponseListener, AdapterView.OnItemSelectedListener {
    private static final String TAG = "FormsActivity";

    private FormsAdapter mAdapter;
    private String mType, mState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_forms);
        super.onCreate(savedInstanceState);

        mAdapter = new FormsAdapter(this);
        RecyclerView formsRV = (RecyclerView) findViewById(R.id.rv_forms);
        formsRV.setLayoutManager(new LinearLayoutManager(this));
        formsRV.setAdapter(mAdapter);

        mType = getIntent().getStringExtra(EXTRA_TYPE);
        final AppCompatSpinner stateACS = (AppCompatSpinner) findViewById(R.id.acs_states);

        // REST request
        if (mType.equals(IGST) || mType.equals(UTGST) || mType.equals(CGST)) {
            stateACS.setVisibility(View.GONE);
            new ApiTask(this).execute(ApiUrl.getFormsUrl(mType));
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
                                        FormsActivity.this,
                                        R.layout.spinner_text,
                                        stateList
                                )
                        );
                        stateACS.setOnItemSelectedListener(FormsActivity.this);
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
    public void onItemClick(FormsModel item, int position) {
        Intent i = new Intent(FormsActivity.this, WebViewActivity.class);
        i.putExtra(WebViewActivity.STR_TITLE, item.getTitle());
        i.putExtra(WebViewActivity.STR_URL, item.getPdfLink());
        startActivity(i);
    }

    @Override
    public void onDownload(FormsModel item, int position) {
        downloadFile(item.getPdfLink(), item.getTitle() + ".pdf");
    }

    @Override
    public void onSuccess(JSONObject response) {
        mAdapter.clear();

        try {
            String str_status = response.optString("status");
            if (str_status.equals("-1")) {
                Toast.makeText(this, "No records", Toast.LENGTH_SHORT).show();
            } else {
                JSONArray data = response.getJSONArray("data");

                for (int i = 0; i < data.length(); i++) {
                    mAdapter.addItem(new FormsModel(data.getJSONObject(i)));
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        try {
            mState = parent.getItemAtPosition(position).toString();
            JSONObject object = new JSONObject();
            object.put("state", mState);
            new ApiTask(this).execute(ApiUrl.getFormsUrl(mType), object.toString());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
