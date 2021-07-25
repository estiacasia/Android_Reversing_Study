package com.example.week_2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class part5 extends AppCompatActivity implements View.OnClickListener {
	ImageView callImage;
	ImageView locationImage;
	ImageView internetImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_part5);

		callImage = (ImageView) findViewById(R.id.mission2_call);
		locationImage = (ImageView) findViewById(R.id.mission2_location);
		internetImage = (ImageView) findViewById(R.id.mission2_internet);

		callImage.setOnClickListener(this);
		locationImage.setOnClickListener(this);
		internetImage.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == callImage) {
			if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:02-731-2120"));
				startActivity(intent);
			} else {
				ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 200);
			}
		} else if (v == locationImage) {
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:37.5662952,126.9779451?q=37.5662952,126.9779451"));
			startActivity(intent);
		} else if (v == internetImage) {
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.seoul.go.kr/main/index.jsp"));
			startActivity(intent);
		}
	}
}

