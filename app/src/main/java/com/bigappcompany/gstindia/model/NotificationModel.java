package com.bigappcompany.gstindia.model;

import com.bigappcompany.gstindia.api.ApiUrl;

import org.json.JSONException;
import org.json.JSONObject;

public class NotificationModel {
    private final String notification_no, pdfLink, subject, notification_type;

    public NotificationModel(JSONObject object) throws JSONException {
        notification_no = object.getString("notification_no");
        pdfLink = ApiUrl.URL_BASE + object.getString("pdf_link");
        subject = object.getString("subject");
        notification_type = object.getString("notification_type");
    }


    public String getNotification_no() {
        return notification_no;
    }

    public String getSubject() {
        return subject;
    }

    public String getNotification_type() {
        return notification_type;
    }

    public String getPdfLink() {
        return pdfLink;
    }
}
