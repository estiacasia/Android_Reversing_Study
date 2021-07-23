package com.example.week_2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
	boolean callPermission;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if(ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED){
			callPermission=true;
		}

		if(!callPermission){
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 200);
		}

		ListView listView=(ListView)findViewById(R.id.custom_listview);

		DBHelper helper=new DBHelper(this);
		SQLiteDatabase db=helper.getWritableDatabase();
		Cursor cursor=db.rawQuery("select name, photo, date, phone from tb_calllog", null);

		ArrayList<CallVO> datas=new ArrayList<>();
		while (cursor.moveToNext()){
			CallVO vo=new CallVO();
			vo.name=cursor.getString(0);
			vo.call=cursor.getString(1);
			vo.date=cursor.getString(2);
			vo.callnb=cursor.getString(3);
			datas.add(vo);
		}
		db.close();

		CallAdapter adapter=new CallAdapter(this, R.layout.custom_item, datas);
		listView.setAdapter(adapter);
	}
}