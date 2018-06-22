package com.bigappcompany.gstindia.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 01 Mar 2017 at 3:35 PM
 */
public class PresentationModel {
	private final String title, presentationUrl;

	public PresentationModel(JSONObject object) throws JSONException {
		title = object.getString("presntation_title");
		presentationUrl = object.getString("presentation_link");
	}

	public String getTitle() {
		return title;
	}

	public String getPresentationUrl() {
		return presentationUrl;
	}
}
