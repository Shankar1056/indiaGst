package com.bigappcompany.gstindia.model;

import com.bigappcompany.gstindia.api.ApiUrl;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 28 Feb 2017 at 4:43 PM
 */
public class OtherModel {
        private final String title, pdfUrl;

        public OtherModel(JSONObject item) throws JSONException {
                title = item.getString("pdf_title");
                pdfUrl = ApiUrl.URL_BASE + item.getString("pdf_link");
        }

        public String getTitle() {
                return title;
        }

        public String getPdfUrl() {
                return pdfUrl;
        }
}
