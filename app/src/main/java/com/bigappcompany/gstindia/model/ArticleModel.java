package com.bigappcompany.gstindia.model;

import com.bigappcompany.gstindia.api.ApiUrl;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 28 Feb 2017 at 12:00 PM
 */
public class ArticleModel {
        private final String longDesc, featureImageUrl, date, title, author, shortDesc;

        public ArticleModel(JSONObject item) throws JSONException, ParseException {
                featureImageUrl = ApiUrl.URL_BASE + item.getString("image_link");
                title = item.getString("article_title");

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                Date date = sdf.parse(item.getString("date"));
                this.date = new SimpleDateFormat("dd MMM yyyy", Locale.US).format(date).toUpperCase();

                author = item.optString("author");
                shortDesc = item.getString("short_desc");
                longDesc = item.getString("article_desc");
        }

        public String getFeatureImageUrl() {
                return featureImageUrl;
        }

        public String getDate() {
                return date;
        }

        public String getTitle() {
                return title;
        }

        public String getAuthor() {
                return author;
        }

        public String getShortDesc() {
                return shortDesc;
        }

        public String getLongDesc() {
                return longDesc;
        }
}
