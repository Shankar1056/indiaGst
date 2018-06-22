package com.bigappcompany.gstindia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bigappcompany.gstindia.R;

public class RegistrationActivity extends GSTActivity {

	TextView register_txt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_registration);
		super.onCreate(savedInstanceState);

		register_txt=(TextView) findViewById(R.id.txt_click_register);

		register_txt.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

			}
		});

	}

}
