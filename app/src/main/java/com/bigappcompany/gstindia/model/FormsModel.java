package com.bigappcompany.gstindia.model;

import com.bigappcompany.gstindia.api.ApiUrl;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 2017-02-25 at 12:29 PM
 */
public class FormsModel {
	private final String title, pdfLink, desc;

	public FormsModel(JSONObject object) throws JSONException {
		title = object.getString("form_type");
		pdfLink = ApiUrl.URL_BASE + object.getString("pdf_link");
		desc = object.getString("brief_desc");
	}


	public String getTitle() {
		return title;
	}

	public String getPdfLink() {
		return pdfLink;
	}
}
