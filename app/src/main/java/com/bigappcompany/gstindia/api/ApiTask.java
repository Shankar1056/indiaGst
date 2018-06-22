package com.bigappcompany.gstindia.api;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.bigappcompany.gstindia.R;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 2017-01-11 at 4:15 PM
 * @signed_off_by Samuel Robert <sam@spotsoon.com>
 * <p>
 * Wrapper class over the OkHttp library.
 * This class can be used for api calls over HTTP or HTTPS protocols
 * </p>
 */

@SuppressWarnings("ConstantConditions")
public final class ApiTask extends AsyncTask<String, Integer, String> {
	private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	private static final String TAG = "ApiTask";
	private static final String TAG_REQUEST = "Request";
	private static final String TAG_RESPONSE = "Response";

	private Context mContext;
	private CharSequence mProgressMessage;
	private OnResponseListener mListener;

	private Dialog mDialog;

	/**
	 * @param listener : Callback listener
	 */
	@SuppressWarnings("unused")
	public ApiTask(@NonNull OnResponseListener listener) {
		this();

		mListener = listener;
	}

	/**
	 * Default constructor for no loader and no callbacks
	 */
	@SuppressWarnings("WeakerAccess")
	public ApiTask() {

	}

	/**
	 * @param context         : mContext for the loader mDialog, null for no loader
	 * @param progressMessage : Progress message, null for no progress loader
	 * @param listener        : Callback listener
	 */
	public ApiTask(@NonNull Context context, @NonNull CharSequence progressMessage, @NonNull OnResponseListener listener) {
		this(context, progressMessage);

		mListener = listener;
	}

	/**
	 * @param context         : mContext for the loader mDialog, null for no loader
	 * @param progressMessage : Progress message, null for no progress loader
	 */
	@SuppressWarnings("WeakerAccess")
	public ApiTask(@NonNull Context context, @NonNull CharSequence progressMessage) {
		this();

		mContext = context;
		mProgressMessage = progressMessage;
	}

	/**
	 * Does POST request to the specified URL if there is a request body specified by params[1]
	 * If there is no body then it does GET request
	 *
	 * @param params [0] mandatory argument specifies URL to be requested.
	 * @return server response, probably a string in JSON format
	 */
	@Override
	protected String doInBackground(String... params) {
		try {
			try {
				return doPostRequest(params[0], params[1]);
			} catch (ArrayIndexOutOfBoundsException e) {
				return doPostRequest(params[0], "");
			}
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
			return null;
		}
	}

	/**
	 * Post method request api
	 *
	 * @param url  url of the request
	 * @param json request json
	 * @return response from the server
	 * @throws IOException
	 */
	@NonNull
	private String doPostRequest(String url, String json) throws IOException {
		RequestBody body = RequestBody.create(JSON, json);
		Request request = new Request.Builder()
		    .url(url)
		    .post(body)
		    .build();
		OkHttpClient client = new OkHttpClient();
		Response response = client.newCall(request).execute();

		String responseStr = response.body().string();

		Log.e(TAG_REQUEST, json + ":" + url);
		Log.e(TAG_RESPONSE, "Response: " + responseStr);

		return responseStr;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

		//Progress mDialog
		if (mProgressMessage != null) {
			mDialog = new Dialog(mContext);
			mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			View view = View.inflate(mContext, R.layout.dialog_progress, null);
			((TextView) view.findViewById(R.id.tv_progress_msg)).setText(mProgressMessage);
			mDialog.setContentView(view);
			mDialog.setCanceledOnTouchOutside(true);
			mDialog.show();
			mDialog.getWindow().setGravity(Gravity.CENTER);
			mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					if (mListener != null) {
						// User cancelled request
						cancel(true);
						mListener.onFailure();
					}
				}
			});
		}
	}

	@Override
	protected void onPostExecute(String response) {
		super.onPostExecute(response);

		// Dismiss the mDialog
		if (mDialog != null && mDialog.isShowing()) {
			mDialog.dismiss();
		}

		if (mListener != null) {
			if (response != null) {
				// on success, convert response into JSON object
				try {
					JSONObject object = new JSONObject(response);
					mListener.onSuccess(object);
				} catch (JSONException e) {
					Log.e(TAG, e.getMessage(), e);
				}
			} else {
				mListener.onFailure();
			}
		}
	}

	public interface OnResponseListener {
		void onSuccess(JSONObject response);

		void onFailure();
	}
}