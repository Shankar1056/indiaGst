package com.bigappcompany.gstindia.model;

import com.bigappcompany.gstindia.api.ApiUrl;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 11 Mar 2017 at 12:59 PM
 */

public class ProcedureModel {
	private final String title, pdfLink, desc;
	
	public ProcedureModel(JSONObject object) throws JSONException {
		title = object.getString("procedure_title");
		pdfLink = ApiUrl.URL_BASE + object.getString("pdf_link");
		desc = object.getString("procedure_desc");
	}
	
	
	public String getTitle() {
		return title;
	}
	
	public String getPdfLink() {
		return pdfLink;
	}
	
	public String getDesc() {
		return desc;
	}
}
