package com.bigappcompany.gstindia.model;

import com.bigappcompany.gstindia.api.ApiUrl;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 11 Mar 2017 at 3:31 PM
 */

public class TPFormsModel {
	private final String title, pdfLink, desc;
	
	public TPFormsModel(JSONObject object) throws JSONException {
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
