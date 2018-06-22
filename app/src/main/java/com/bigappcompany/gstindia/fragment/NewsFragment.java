package com.bigappcompany.gstindia.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigappcompany.gstindia.R;
import com.bigappcompany.gstindia.adapter.NewsAdapter;
import com.bigappcompany.gstindia.api.ApiTask;
import com.bigappcompany.gstindia.api.ApiUrl;
import com.bigappcompany.gstindia.model.NewsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

/**
 * Created by sam on 27/2/17.
 */

@SuppressWarnings("ConstantConditions")
public class NewsFragment extends Fragment implements ApiTask.OnResponseListener {
        private static final String TAG = "NewsFragment";
        private NewsAdapter mAdapter;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
                View rootView = inflater.inflate(R.layout.fragment_news, container, false);
                RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_news);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                mAdapter = new NewsAdapter();
                recyclerView.setAdapter(mAdapter);

                new ApiTask(this).execute(ApiUrl.URL_NEWS, getPages(0, 10));
                return rootView;
        }

        private String getPages(int index, int offset) {
                try {
                        JSONObject object = new JSONObject();
                        object.put("index", index);
                        object.put("offset", offset);
                        return object.toString();
                } catch (JSONException e) {
                        Log.e(TAG, "getPages: " + e.getMessage(), e);
                        return null;
                }
        }

        @Override
        public void onSuccess(JSONObject response) {
                try {
                        JSONArray data = response.getJSONArray("data");
                        mAdapter.clear();

                        for (int i = 0; i < data.length(); i++) {
                                mAdapter.addItem(new NewsModel(data.getJSONObject(i)));
                        }

                        getView().findViewById(R.id.clpb_news).setVisibility(View.GONE);
                } catch (JSONException | ParseException e) {
                        Log.e(TAG, "onSuccess: ", e);
                }
        }

        @Override
        public void onFailure() {

        }
}
