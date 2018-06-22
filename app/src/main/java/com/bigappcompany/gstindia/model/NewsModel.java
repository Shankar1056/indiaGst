package com.bigappcompany.gstindia.model;

import com.bigappcompany.gstindia.api.ApiUrl;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by sam on 27/2/17.
 */
public class NewsModel {
        private final String longDesc, featureImageUrl, date, title, author, shortDesc;

        public NewsModel(JSONObject item) throws JSONException, ParseException {
                featureImageUrl = ApiUrl.URL_NEWS_IMAGE + item.getString("image_link");
                title = item.getString("news_title");

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                Date date = sdf.parse(item.getString("date"));
                this.date = new SimpleDateFormat("dd MMM yyyy", Locale.US).format(date).toUpperCase();

                author = item.optString("author");
                shortDesc = item.getString("short_desc");
                longDesc = item.getString("long_desc");
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
