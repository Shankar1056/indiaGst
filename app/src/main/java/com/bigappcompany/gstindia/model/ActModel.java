package com.bigappcompany.gstindia.model;

import com.bigappcompany.gstindia.api.ApiUrl;
import com.bigappcompany.gstindia.api.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 2017-02-24 at 4:10 PM
 */
public class ActModel {
	private final String actName, pdfLink;

	public ActModel(JSONObject object) throws JSONException {
		actName = object.getString(JsonParser.ACT_NAME);
		pdfLink = ApiUrl.URL_BASE + object.getString(JsonParser.PDF_LINK);
	}

	public String getActName() {
		return actName;
	}

	public String getPdfLink() {
		return pdfLink;
	}
}
