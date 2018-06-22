package com.bigappcompany.gstindia.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bigappcompany.gstindia.R;

public class WebViewActivity extends AppCompatActivity {

    ProgressBar pb;
    Toolbar tb;
    ImageView back_btn;
    TextView title_txt;

    public static String STR_URL = "str_url";
    public static String STR_TITLE = "str_title";

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        tb = findViewById(R.id.toolbar);
        back_btn = tb.findViewById(R.id.back_btn);
        title_txt = tb.findViewById(R.id.title_txt);

        title_txt.setText(getIntent().getStringExtra(STR_TITLE));

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Log.e("url",getIntent().getStringExtra(STR_URL).replaceAll(" ", "%20"));

        pb = (ProgressBar) findViewById(R.id.progress_bar);
        pb.setVisibility(View.VISIBLE);
        pb.getIndeterminateDrawable().setColorFilter(Color.parseColor("#006967"), android.graphics.PorterDuff.Mode.SRC_ATOP);
        WebView mWebView = (WebView) findViewById(R.id.web_view);
        WebSettings settings = mWebView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setJavaScriptEnabled(true);
        mWebView.setVisibility(View.GONE);
        mWebView.setWebViewClient(new WebClient());
        mWebView.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=" + getIntent().getStringExtra(STR_URL).replaceAll(" ", "%20"));

    }

    private class WebClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            pb.setVisibility(View.GONE);
            view.setVisibility(View.VISIBLE);

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            pb.setVisibility(View.VISIBLE);
        }
    }
}
