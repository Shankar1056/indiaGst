package com.bigappcompany.gstindia.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.bigappcompany.gstindia.R;
import com.bigappcompany.gstindia.adapter.FragmentAdapter;
import com.bigappcompany.gstindia.api.ApiTask;
import com.bigappcompany.gstindia.api.ApiUrl;
import com.bigappcompany.gstindia.fragment.NotificationsFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.bigappcompany.gstindia.activity.GSTActivity.EXTRA_TYPE;

public class NotificationActivity extends AppCompatActivity implements ApiTask.OnResponseListener, AdapterView.OnItemSelectedListener {

    private FragmentAdapter mAdapter;
    private String mType, mState = "", state = "";
    ViewPager tutorialVP;
    ArrayList<String> stateList;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        mType = getIntent().getStringExtra(EXTRA_TYPE);

        tabLayout = findViewById(R.id.tl_notification);
        tutorialVP = (ViewPager) findViewById(R.id.vp_notification);

        mAdapter = new FragmentAdapter(getSupportFragmentManager());
        tutorialVP.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(tutorialVP);

        if (mType.equals("igst") || mType.equals("utgst") || mType.equals("cgst")) {
            findViewById(R.id.acs_states).setVisibility(View.GONE);
            new ApiTask(this).execute(ApiUrl.getNotificationTypeUrl(mType));
        } else {
            final AppCompatSpinner stateACS = (AppCompatSpinner) findViewById(R.id.acs_states);
            stateACS.setVisibility(View.VISIBLE);
            new ApiTask(new ApiTask.OnResponseListener() {
                @Override
                public void onSuccess(JSONObject response) {
                    try {
                        JSONArray data = response.getJSONArray("data");
                        stateList = new ArrayList<>();
                        for (int i = 0; i < data.length(); i++) {
                            stateList.add(data.getJSONObject(i).getString("state_name"));
                        }

                        stateACS.setAdapter(
                                new ArrayAdapter<>(
                                        NotificationActivity.this,
                                        R.layout.spinner_text,
                                        stateList
                                )
                        );
                        stateACS.setOnItemSelectedListener(NotificationActivity.this);
                    } catch (JSONException e) {

                    }
                }

                @Override
                public void onFailure() {

                }
            }).execute(ApiUrl.URL_STATES);
        }


    }

    @Override
    public void onSuccess(JSONObject response) {
        try {
            mAdapter.clear();

            String str_status = response.optString("status");
            if (str_status.equals("-1")) {

                tutorialVP.setVisibility(View.GONE);
                Toast.makeText(this, "No records", Toast.LENGTH_SHORT).show();

            } else {
                JSONArray data = response.getJSONArray("data");
                tutorialVP.setVisibility(View.VISIBLE);

                mAdapter = new FragmentAdapter(getSupportFragmentManager());
                tutorialVP.setAdapter(mAdapter);
                tabLayout.setupWithViewPager(tutorialVP);

                for (int i = 0; i < data.length(); i++) {
                    String title = data.getJSONObject(i).getString("notification_type");
                    Bundle args = new Bundle();
                    args.putString(NotificationsFragment.ARG_NOTIFICATION_TYPE, title);
                    args.putString(NotificationsFragment.ARG_TYPE, mType);
                    args.putString(NotificationsFragment.ARG_STATE, state);
                    Fragment fragment = new NotificationsFragment();
                    fragment.setArguments(args);
                    mAdapter.addItem(fragment, title);
                    Log.e("state", state + " " + i);
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure() {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        try {
            state = stateList.get(position);
            mState = parent.getItemAtPosition(position).toString();
            JSONObject object = new JSONObject();
            object.put("state", mState);
            new ApiTask(this).execute(ApiUrl.getNotificationTypeUrl(mType), object.toString());

        } catch (JSONException e) {

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
