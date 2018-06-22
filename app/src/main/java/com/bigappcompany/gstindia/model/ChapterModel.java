package com.bigappcompany.gstindia.model;

import com.bigappcompany.gstindia.api.ApiUrl;
import com.bigappcompany.gstindia.api.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 2017-02-24 at 5:18 PM
 */
public class ChapterModel {
	private final String chapterName,pdfLink;

	public ChapterModel(JSONObject object) throws JSONException {
		chapterName = object.getString(JsonParser.CHAPTER_NAME);
		pdfLink = ApiUrl.URL_BASE + object.getString(JsonParser.PDF_LINK);
	}

	public String getChapterName() {
		return chapterName;
	}

	public String getPDFLink() {
		return pdfLink;
	}
}
