package com.bigappcompany.gstindia.model;

import com.bigappcompany.gstindia.api.ApiUrl;
import com.bigappcompany.gstindia.api.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 2017-02-23 at 6:22 PM
 */
public class RulesModel {
	private final String title, pdfLink;

	public RulesModel(JSONObject object) throws JSONException {
		title = object.getString(JsonParser.RULE_TITLE);
		pdfLink = ApiUrl.URL_BASE + object.getString(JsonParser.PDF_LINK);
	}

	public String getTitle() {
		return title;
	}

	public String getPdfLink() {
		return pdfLink;
	}
}
