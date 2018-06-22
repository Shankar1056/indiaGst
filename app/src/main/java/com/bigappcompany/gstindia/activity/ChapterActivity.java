package com.bigappcompany.gstindia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.bigappcompany.gstindia.R;
import com.bigappcompany.gstindia.adapter.ChapterAdapter;
import com.bigappcompany.gstindia.api.ApiTask;
import com.bigappcompany.gstindia.api.ApiUrl;
import com.bigappcompany.gstindia.api.JsonParser;
import com.bigappcompany.gstindia.model.ChapterModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ChapterActivity extends GSTActivity implements ApiTask.OnResponseListener, ChapterAdapter.OnItemClickListener {
    private static final String TAG = "ChapterActivity";

    private String mType, mState, mActName;
    private ChapterAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_chapter);
        super.onCreate(savedInstanceState);

        // init chapter list and adapter
        mAdapter = new ChapterAdapter(this);
        RecyclerView chapterRV = (RecyclerView) findViewById(R.id.rv_chapter);
        chapterRV.setLayoutManager(new LinearLayoutManager(this));
        chapterRV.setAdapter(mAdapter);

        // Read extras
        mType = getIntent().getStringExtra(EXTRA_TYPE);
        mState = getIntent().getStringExtra(EXTRA_STATE);
        mActName = getIntent().getStringExtra(EXTRA_ACT_NAME);

        // Create request JSON and make API call
        try {
            JSONObject object = new JSONObject();
            object.put("state", mState);
            object.put("act_name", mActName);

            new ApiTask(this).execute(ApiUrl.getChapterUrl(mType), object.toString());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    @Override
    public void onSuccess(JSONObject response) {
        try {
            String str_status = response.optString("status");
            if (str_status.equals("-1")) {
                Toast.makeText(this, "No records", Toast.LENGTH_SHORT).show();
            } else {
                JSONArray data = response.getJSONArray(JsonParser.DATA);

                for (int i = 0; i < data.length(); i++) {
                    mAdapter.addItem(new ChapterModel(data.getJSONObject(i)));
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
    public void onDownload(ChapterModel item, int position) {
        downloadFile(item.getPDFLink(), item.getChapterName()+ ".pdf");
    }

    @Override
    public void onItemClick(ChapterModel chapterModel, int position) {
        Intent intent = new Intent(this, WebActivity.class);
        intent.putExtra(EXTRA_TYPE, mType);
        intent.putExtra(EXTRA_STATE, mState);
        intent.putExtra(EXTRA_ACT_NAME, mActName);
        intent.putExtra(EXTRA_CHAPTER_NAME, chapterModel.getChapterName());
        startActivity(intent);
    }
}
