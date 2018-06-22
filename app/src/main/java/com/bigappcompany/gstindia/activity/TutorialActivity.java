package com.bigappcompany.gstindia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bigappcompany.gstindia.R;
import com.bigappcompany.gstindia.api.ApiUrl;

public class TutorialActivity extends GSTActivity {

    TextView tv_article, tv_video, tv_presentation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_tutorial);
        super.onCreate(savedInstanceState);

        tv_article = findViewById(R.id.tv_article);
        tv_video = findViewById(R.id.tv_videos);
        tv_presentation = findViewById(R.id.tv_presentations);

        tv_article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TutorialActivity.this, WebViewActivity.class);
                i.putExtra(WebViewActivity.STR_TITLE, "Article");
                i.putExtra(WebViewActivity.STR_URL, ApiUrl.URL_BASE + "tutorial/articles");
                startActivity(i);
            }
        });

        tv_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TutorialActivity.this, WebViewActivity.class);
                i.putExtra(WebViewActivity.STR_TITLE, "Video");
                i.putExtra(WebViewActivity.STR_URL, ApiUrl.URL_BASE + "tutorial/videos");
                startActivity(i);
            }
        });

        tv_presentation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TutorialActivity.this, WebViewActivity.class);
                i.putExtra(WebViewActivity.STR_TITLE, "Presentation");
                i.putExtra(WebViewActivity.STR_URL, ApiUrl.URL_BASE + "tutorial/ppts");
                startActivity(i);
            }
        });

    }

}
