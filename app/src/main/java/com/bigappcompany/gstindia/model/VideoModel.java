package com.bigappcompany.gstindia.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 28 Feb 2017 at 12:42 PM
 */
public class VideoModel {
        private final String title, videoId;

        public VideoModel(JSONObject item) throws JSONException, ParseException {

                title = item.getString("video_title");
                videoId = item.getString("video_id");
        }

        public String getTitle() {
                return title;
        }

        public String getVideoId() {
                return videoId;
        }
}
