package com.bigappcompany.gstindia.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.bigappcompany.gstindia.R;

@SuppressWarnings("ConstantConditions")
public class WebInterfaceActivity extends GSTActivity {

	ProgressBar pb;
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_web_interface);
		super.onCreate(savedInstanceState);
		getSupportActionBar().setTitle(getIntent().getStringExtra(Intent.EXTRA_TITLE));

		pb=(ProgressBar)findViewById(R.id.progress_bar);
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
		mWebView.loadUrl(getIntent().getStringExtra(EXTRA_URL));

	}
	
	private class WebClient extends WebViewClient {
		@Override
		public void onPageFinished(WebView view, String url) {
			pb.setVisibility(View.GONE);
			view.setVisibility(View.VISIBLE);
			view.loadUrl("javascript:" +
			    "var styleTag = document.createElement(\"style\");\n" +
			    "var styleTag2 = document.createElement(\"style\");\n" +
			    "var styleTag3 = document.createElement(\"style\");\n" +
			    "var styleTag4 = document.createElement(\"style\");\n" +
			    "styleTag.textContent = 'section.inner-banner-container.block.whitecolor.aboutus-bg.text-center {display:none;}';\n" +
			    "styleTag2.textContent = 'div.header-menucontainer.flex.transition {display:none;}';\n" +
			    "styleTag3.textContent = 'section.footerdiv.block.paddintopbottom40.whitecolor {display:none;}';\n" +
			    "styleTag4.textContent = 'footer.block.copyright-container.text-center {display:none;}';\n" +
			    "document.documentElement.appendChild(styleTag);\n" +
			    "document.documentElement.appendChild(styleTag2);\n" +
			    "document.documentElement.appendChild(styleTag3);\n" +
			    "document.documentElement.appendChild(styleTag4);"
			);
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			pb.setVisibility(View.VISIBLE);
		}
	}
}
