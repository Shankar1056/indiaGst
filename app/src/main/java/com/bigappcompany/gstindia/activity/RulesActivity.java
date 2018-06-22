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
import com.bigappcompany.gstindia.adapter.RuleListAdapter;
import com.bigappcompany.gstindia.api.ApiTask;
import com.bigappcompany.gstindia.api.ApiUrl;
import com.bigappcompany.gstindia.api.JsonParser;
import com.bigappcompany.gstindia.model.RulesModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RulesActivity extends GSTActivity implements ApiTask.OnResponseListener, RuleListAdapter.OnItemClickListener, AdapterView.OnItemSelectedListener {
    private static final String TAG = "RulesActivity";

    private RuleListAdapter mAdapter;
    private String mState, type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_rules);
        super.onCreate(savedInstanceState);

        // Init rules recycler and adapter
        RecyclerView rulesRV = (RecyclerView) findViewById(R.id.rv_rules);
        rulesRV.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RuleListAdapter(this);
        rulesRV.setAdapter(mAdapter);

        // Read type from intent
        type = getIntent().getStringExtra(EXTRA_TYPE);

        if (type.equals(IGST) || type.equals(UTGST) || type.equals(CGST)) {
            findViewById(R.id.acs_states).setVisibility(View.GONE);
            new ApiTask(this).execute(ApiUrl.getRulesUrl(type));
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
                                        RulesActivity.this,
                                        R.layout.spinner_text,
                                        stateList
                                )
                        );
                        stateACS.setOnItemSelectedListener(RulesActivity.this);
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
        mAdapter.clearItems();
        try {
            String str_status = response.optString("status");
            if (str_status.equals("-1")) {
                Toast.makeText(this, "No records", Toast.LENGTH_SHORT).show();
            } else {
                JSONArray data = response.getJSONArray(JsonParser.DATA);

                for (int i = 0; i < data.length(); i++) {
                    mAdapter.addItem(new RulesModel(data.getJSONObject(i)));
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
            new ApiTask(this).execute(ApiUrl.getRulesUrl(type), object.toString());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onItemClick(RulesModel item, int position) {
        Intent i = new Intent(RulesActivity.this, WebViewActivity.class);
        i.putExtra(WebViewActivity.STR_TITLE, item.getTitle());
        i.putExtra(WebViewActivity.STR_URL, item.getPdfLink());
        startActivity(i);
    }

    @Override
    public void onDownload(RulesModel item, int position) {
        downloadFile(item.getPdfLink(), item.getTitle() + ".pdf");
    }
}
