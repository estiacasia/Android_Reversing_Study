package com.example.week_2;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class CallHolder {
	public ImageView imageView;
	public TextView nameView;
	public TextView dateView;
	public ImageView callImageView;
	public CallHolder(View root){
		imageView=(ImageView)root.findViewById(R.id.custom_item_image);
		nameView=(TextView)root.findViewById(R.id.custom_item_name);
		dateView=(TextView)root.findViewById(R.id.custom_item_date);
		callImageView=(ImageView)root.findViewById(R.id.custom_item_call);
	}
}
