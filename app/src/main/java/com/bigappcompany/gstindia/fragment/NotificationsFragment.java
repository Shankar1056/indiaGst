package com.bigappcompany.gstindia.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bigappcompany.gstindia.R;
import com.bigappcompany.gstindia.activity.WebInterfaceActivity;
import com.bigappcompany.gstindia.activity.WebViewActivity;
import com.bigappcompany.gstindia.adapter.NotificationAdapter;
import com.bigappcompany.gstindia.api.ApiTask;
import com.bigappcompany.gstindia.api.ApiUrl;
import com.bigappcompany.gstindia.model.NotificationModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

import static com.bigappcompany.gstindia.activity.GSTActivity.EXTRA_URL;

public class NotificationsFragment extends Fragment implements NotificationAdapter.OnItemClickListener, ApiTask.OnResponseListener {

    RecyclerView notificationRv;
    NotificationAdapter notificationAdapter;
    public static final String ARG_NOTIFICATION_TYPE = "arg_notification_type";
    public static final String ARG_TYPE = "arg_type";
    public static final String ARG_STATE = "arg_state";

    public NotificationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        String notification_type = getArguments().getString(ARG_NOTIFICATION_TYPE);
        String gst_type = getArguments().getString(ARG_TYPE);
        String state = getArguments().getString(ARG_STATE);

        Log.e("n_state",state);

        new ApiTask(this).execute(ApiUrl.getNotificationDetailsUrl(gst_type), getPages(notification_type,state));

        notificationRv = view.findViewById(R.id.notification_list);
        notificationAdapter = new NotificationAdapter(this);
        notificationRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        notificationRv.setAdapter(notificationAdapter);
        return view;
    }

    private String getPages(String str_type,String state) {
        try {
            JSONObject object = new JSONObject();
            object.put("notification_type", str_type);
            object.put("state", state);
            return object.toString();
        } catch (JSONException e) {
            return null;
        }
    }

    @Override
    public void onItemClick(NotificationModel item, int position) {
        Intent i = new Intent(getActivity(), WebViewActivity.class);
        i.putExtra(WebViewActivity.STR_TITLE, item.getNotification_no());
        i.putExtra(WebViewActivity.STR_URL, item.getPdfLink());
        startActivity(i);
    }

    @Override
    public void onSuccess(JSONObject response) {
        notificationAdapter.clear();
        try {
            String str_status = response.optString("status");
            if (str_status.equals("-1")) {
                Toast.makeText(getActivity(), "No records", Toast.LENGTH_SHORT).show();
            } else {
                JSONArray data = response.getJSONArray("data");

                for (int i = 0; i < data.length(); i++) {
                    notificationAdapter.addItem(new NotificationModel(data.getJSONObject(i)));
                }
            }

        } catch (JSONException e) {

        }
    }

    @Override
    public void onFailure() {

    }
}
