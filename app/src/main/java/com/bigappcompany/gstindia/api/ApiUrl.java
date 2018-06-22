package com.bigappcompany.gstindia.api;

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 2017-01-11 at 4:15 PM
 */

public final class ApiUrl {
	public static final String URL_BASE = "http://bigappcompany.co.in/demos/indiagst/";
	public static final String URL_HOME_SLIDER_IMAGES = URL_BASE + "gst_mobile_banner";
	public static final String URL_STATES = URL_BASE + "states_list";
	public static final String URL_NEWS = URL_BASE + "latest-news-api";
	public static final String URL_NEWS_IMAGE = URL_BASE + "uploads/news/";
	public static final String URL_TUTORIALS = URL_BASE + "tutorial-type";
	public static final String URL_ARTICLE = URL_BASE + "tutorial-article-api";
	public static final String URL_VIDEO = URL_BASE + "tutorial-videos-api";
	public static final String URL_PRESENTATION = URL_BASE + "tutorial-ppt-api";
	public static final String URL_ENROLLMENT = URL_BASE + "enrollment-process";
	public static final String URL_NEW_REGISTRATION = URL_BASE + "new-registration";
	public static final String URL_RATE_FINDER = URL_BASE + "gst-tax-rates";
	public static final String URL_CALCULATOR = URL_BASE + "calculator";
	private static final String URL_LIST = URL_BASE + "list_post/";
	public static final String URL_OTHER_LAWS = URL_LIST + "other_laws_details";
	private static final String URL_ACTS = URL_LIST + "%s_acts_list";
	private static final String URL_RULES = URL_LIST + "%s_rules";
	private static final String URL_FORMS = URL_LIST + "%s_forms";
	private static final String URL_NOTIFICATION_TYPE = URL_LIST + "%s_notification_type";
	private static final String URL_NOTIFICATION_DETAILS = URL_LIST + "%s_notification_list_details";
	private static final String URL_CHAPTER = URL_LIST + "%s_acts_chapter_list";
	private static final String URL_CHAPTER_DETAILS = URL_LIST + "%s_acts_chapter_details";
	private static final String URL_PROCEDURE = URL_BASE + "tp-%s-procedure-api";
	private static final String URL_TP_FORMS = URL_BASE + "tp-%s-forms-api";
	
	public static String getActsUrl(String type) {
		return String.format(URL_ACTS, type);
	}
	
	public static String getChapterUrl(String type) {
		return String.format(URL_CHAPTER, type);
	}
	
	public static String getChapterDetailsUrl(String type) {
		return String.format(URL_CHAPTER_DETAILS, type);
	}
	
	public static String getRulesUrl(String type) {
		return String.format(URL_RULES, type);
	}
	
	public static String getFormsUrl(String type) {
		return String.format(URL_FORMS, type);
	}
	
	public static String getProcedureUrl(String type) {
		return String.format(URL_PROCEDURE, type);
	}
	
	public static String getTPFormsUrl(String type) {
		return String.format(URL_TP_FORMS, type);
	}

	public static String getNotificationTypeUrl(String type) {
		return String.format(URL_NOTIFICATION_TYPE, type);
	}

	public static String getNotificationDetailsUrl(String type) {
		return String.format(URL_NOTIFICATION_DETAILS, type);
	}
}
