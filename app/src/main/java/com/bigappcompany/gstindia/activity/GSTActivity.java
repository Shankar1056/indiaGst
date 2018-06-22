package com.bigappcompany.gstindia.activity;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.bigappcompany.gstindia.R;

import java.io.File;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 2017-01-11 at 4:15 PM
 * @signed_off_by Samuel Robert <sam@spotsoon.com>
 * <p>
 * <p>
 * Parent activity for all activities.
 * Add common functionalities for all activities in this file
 * </p>
 */

@SuppressWarnings("ConstantConditions")
public abstract class GSTActivity extends AppCompatActivity {
    public static final String IGST = "igst";
    public static final String CGST = "cgst";
    public static final String SGST = "sgst";
    public static final String UTGST = "utgst";

    public static final String EXTRA_TYPE = "extra_type";
    public static final String EXTRA_STATE = "extra_state";
    public static final String EXTRA_ACT_NAME = "extra_act_name";
    public static final String EXTRA_CHAPTER_NAME = "extra_chapter_name";
    public static final String EXTRA_IS_URL = "extra_is_url";
    public static final String EXTRA_HTML = "extra_html";
    public static final String EXTRA_CATEGORY = "extra_category";
    public static final String EXTRA_IS_ENROLLMENT = "extra_is_enrollment";
    public static final String EXTRA_URL = "extra_url";

    private static final int PC_EXTERNAL_WRITE = 32;
    private static boolean isDownloading = false;
    protected Toolbar mToolbar;
    private boolean isHome = false;
    private String url, fileName;
    private BroadcastReceiver mDownloadReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            // Unregister the receiver and reset the flag
            isDownloading = false;
            unregisterReceiver(mDownloadReceiver);

            // Show the confirmation dialog to open the downloaded file
            new AlertDialog.Builder(GSTActivity.this, R.style.AlertDialogCustom)
                    .setTitle("Open File?")
                    .setMessage("Download completed!\nWould you like to open it now?")
                    .setPositiveButton(R.string.yes_go_ahead, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            openFile(
                                    Environment.getExternalStorageDirectory() + "/" +
                                            DIRECTORY_DOWNLOADS + "/" + fileName.replaceAll(" ","")
                            );
                        }
                    })
                    .setNegativeButton(R.string.may_be_later, null).show();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Toolbar init
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        if (!isHome) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (isDownloading) {
            registerReceiver(mDownloadReceiver, new IntentFilter(DownloadManager
                    .ACTION_DOWNLOAD_COMPLETE));
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (isDownloading) {
            unregisterReceiver(mDownloadReceiver);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PC_EXTERNAL_WRITE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startDownload(url.replaceAll(" ", "%20"), fileName.replaceAll(" ", ""));
                } else {
                    Toast.makeText(this, R.string.perm_not_granted, Toast.LENGTH_LONG).show();
                }
        }
    }

    private void startDownload(String url, String fileName) {
        // Set the downloading flag and register the receiver
        isDownloading = true;
        registerReceiver(mDownloadReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        Log.e("download url", url);
        // Create download request
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDescription("Download " + fileName);
        request.setTitle(fileName);
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(DIRECTORY_DOWNLOADS, fileName);

        // Add the request to the queue
        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }

    public void downloadFile(String url, String fileName) {
        // Store the parameters
        this.url = url;
        this.fileName = fileName;

        // Check storage permission
        int permStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permStatus == PackageManager.PERMISSION_GRANTED) {
            startDownload(url.replaceAll(" ", "%20"), fileName.replaceAll(" ", ""));
        } else {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PC_EXTERNAL_WRITE
            );
        }
    }

    protected void openFile(String fileName) {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (fileName.contains(".pdf")) {
            intent.setDataAndType(Uri.fromFile(new File(fileName)), "application/pdf");
        } else if (fileName.contains(".xls") || fileName.contains(".xlsx")) {
            intent.setDataAndType(Uri.fromFile(new File(fileName)), "application/vnd.ms-excel");
        } else {
            intent.setDataAndType(Uri.fromFile(new File(fileName)), "*/*");
        }
        startActivity(intent);
    }

    protected void setHome(boolean isHome) {
        this.isHome = isHome;
    }

    public enum LAW {
        IGST, SGST, CGST, UTGST, OTHER
    }
}
