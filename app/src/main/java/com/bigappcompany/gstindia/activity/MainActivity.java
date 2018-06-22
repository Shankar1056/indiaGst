package com.bigappcompany.gstindia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bigappcompany.gstindia.R;

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 2017-01-11 at 4:15 PM
 * @signed_off_by Samuel Robert <sam@spotsoon.com>
 */

@SuppressWarnings("ConstantConditions")
public class MainActivity extends AppCompatActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);

                startActivity(new Intent(this, HomeActivity.class));
                MainActivity.this.finish();
        }
}
