package com.bigappcompany.gstindia.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.bigappcompany.gstindia.R;
import com.bigappcompany.gstindia.adapter.VideoAdapter;
import com.bigappcompany.gstindia.api.ApiTask;
import com.bigappcompany.gstindia.api.ApiUrl;
import com.bigappcompany.gstindia.model.VideoModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

public class VideoActivity extends GSTActivity implements ApiTask.OnResponseListener {
        private static final String TAG = "VideoActivity";

        private VideoAdapter mAdapter;
        private String mTutorialType;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                setContentView(R.layout.activity_video);
                super.onCreate(savedInstanceState);

                mAdapter = new VideoAdapter();

                RecyclerView videoRV = (RecyclerView) findViewById(R.id.rv_video);
                videoRV.setLayoutManager(new LinearLayoutManager(this));
                videoRV.setAdapter(mAdapter);

                mTutorialType = getIntent().getStringExtra(EXTRA_CATEGORY);
                new ApiTask(this).execute(ApiUrl.URL_VIDEO, getPages(0, 10));
        }

        @Override
        public void onSuccess(JSONObject response) {
                try {
                        JSONArray data = response.getJSONArray("data");

                        for (int i = 0; i < data.length(); i++) {
                                mAdapter.addItem(new VideoModel(data.getJSONObject(i)));
                        }
                } catch (JSONException | ParseException e) {
                        Log.e(TAG, e.getMessage(), e);
                }
        }

        @Override
        public void onFailure() {

        }

        private String getPages(int index, int offset) {
                try {
                        JSONObject object = new JSONObject();
                        object.put("index", index);
                        object.put("offset", offset);
                        object.put("tutorial_type", mTutorialType);
                        return object.toString();
                } catch (JSONException e) {
                        Log.e(TAG, "getPages: " + e.getMessage(), e);
                        return null;
                }
        }
}
