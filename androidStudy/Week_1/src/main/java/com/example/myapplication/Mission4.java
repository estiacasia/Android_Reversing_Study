package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.databinding.ActivityMission4ResultBinding;

public class Mission4 extends AppCompatActivity implements View.OnClickListener {

	EditText nameView;
	EditText phoneView;
	EditText emailView;
	Button addBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mission4);

		nameView = (EditText) findViewById(R.id.name);
		phoneView = (EditText) findViewById(R.id.phone);
		emailView = (EditText) findViewById(R.id.email);

		addBtn = (Button) findViewById(R.id.btn_add);
		addBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == addBtn) {

			String name = nameView.getText().toString();
			String email = emailView.getText().toString();
			String phone = phoneView.getText().toString();

			if (name == null || name.equals("")) {
				Toast t = Toast.makeText(this, "이름이 입력되지 않았습니다. ", Toast.LENGTH_SHORT);
				t.show();
			} else {
				DBHelper helper = new DBHelper(this);
				SQLiteDatabase db = helper.getWritableDatabase();
				db.execSQL("insert into tb_contact (name, phone, email) values (?,?,?)", new String[]{name, phone, email});
				db.close();

				Intent intent = new Intent(this, Mission4Result.class);
				startActivity(intent);
			}
		}
	}
}
