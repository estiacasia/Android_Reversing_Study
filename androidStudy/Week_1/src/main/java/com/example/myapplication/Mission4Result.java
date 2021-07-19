package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

public class Mission4Result extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mission4_result);

		TextView nameView = (TextView) findViewById(R.id.result_name);
		TextView phoneView = (TextView) findViewById(R.id.result_phone);
		TextView emailView = (TextView) findViewById(R.id.result_email);

		DBHelper helper = new DBHelper(this);
		SQLiteDatabase db = helper.getWritableDatabase();

		Cursor cursor = db.rawQuery("select name, phone, email from tb_contact order by _id desc limit 1", null);
		while (cursor.moveToNext()) {
			nameView.setText(cursor.getString(0));
			phoneView.setText(cursor.getString(1));
			emailView.setText(cursor.getString(2));
		}
		db.close();
	}
}