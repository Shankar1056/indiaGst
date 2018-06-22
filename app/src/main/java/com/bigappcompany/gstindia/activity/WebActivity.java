package com.bigappcompany.gstindia.activity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.bigappcompany.gstindia.R;
import com.bigappcompany.gstindia.api.ApiTask;
import com.bigappcompany.gstindia.api.ApiUrl;

import org.json.JSONException;
import org.json.JSONObject;

public class WebActivity extends GSTActivity implements ApiTask.OnResponseListener {
        private static final String TAG = "WebActivity";

        private WebView mWebView;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                setContentView(R.layout.activity_web);
                super.onCreate(savedInstanceState);

                mWebView = (WebView) findViewById(R.id.web_view);
                WebSettings settings = mWebView.getSettings();
                settings.setUseWideViewPort(true);
                settings.setLoadWithOverviewMode(true);
                settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
                settings.setBuiltInZoomControls(true);
                settings.setDisplayZoomControls(false);

                if (getIntent().getBooleanExtra(EXTRA_IS_URL, true)) {
                        try {
                                String type = getIntent().getStringExtra(EXTRA_TYPE);

                                JSONObject object = new JSONObject();
                                object.put("state", getIntent().getStringExtra(EXTRA_STATE));
                                object.put("act_name", getIntent().getStringExtra(EXTRA_ACT_NAME));
                                object.put("chapter_name", getIntent().getStringExtra(EXTRA_CHAPTER_NAME));

                                new ApiTask(this).execute(ApiUrl.getChapterDetailsUrl(type), object.toString());
                        } catch (JSONException e) {
                                Log.e(TAG, e.getMessage(), e);
                        }
                } else {
                        mWebView.loadData(getIntent().getStringExtra(EXTRA_HTML), "text/html", "UTF-8");
                }
        }

        @Override
        public void onSuccess(JSONObject response) {
                try {
                        mWebView.loadData(
                            response.getJSONArray("data").getJSONObject(0).getString("brief_desc"),
                            "text/html",
                            "UTF-8"
                        );
                } catch (JSONException e) {
                        Log.e(TAG, e.getMessage(), e);
                }
        }

        @Override
        public void onFailure() {

        }
}
