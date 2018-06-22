package com.bigappcompany.gstindia.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.bigappcompany.gstindia.R;
import com.bigappcompany.gstindia.adapter.ArticleAdapter;
import com.bigappcompany.gstindia.api.ApiTask;
import com.bigappcompany.gstindia.api.ApiUrl;
import com.bigappcompany.gstindia.model.ArticleModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

public class ArticleActivity extends GSTActivity implements ApiTask.OnResponseListener {
        private static final String TAG = "ArticleActivity";

        private String mTutorialType;
        private ArticleAdapter mAdapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                setContentView(R.layout.activity_article);
                super.onCreate(savedInstanceState);

                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_article);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                mAdapter = new ArticleAdapter();
                recyclerView.setAdapter(mAdapter);

                mTutorialType = getIntent().getStringExtra(EXTRA_CATEGORY);
                new ApiTask(this).execute(ApiUrl.URL_ARTICLE, getPages(0, 10));
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

        @Override
        public void onSuccess(JSONObject response) {
                try {
                        JSONArray data = response.getJSONArray("data");

                        for (int i = 0; i < data.length(); i++) {
                                mAdapter.addItem(new ArticleModel(data.getJSONObject(i)));
                        }
                } catch (JSONException | ParseException e) {
                        Log.e(TAG, e.getMessage(), e);
                }
        }

        @Override
        public void onFailure() {

        }
}
