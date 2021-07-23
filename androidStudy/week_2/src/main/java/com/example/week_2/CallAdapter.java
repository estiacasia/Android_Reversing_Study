package com.example.week_2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;

public class CallAdapter extends ArrayAdapter<CallVO> {
	Context context;
	int resId;
	ArrayList<CallVO> datas;

	public CallAdapter(Context context, int resId, ArrayList<CallVO> datas) {
		super(context, resId);
		this.context = context;
		this.resId = resId;
		this.datas = datas;
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(resId, null);

			CallHolder holder = new CallHolder(convertView);
			convertView.setTag(holder);
		}

		CallHolder holder = (CallHolder)convertView.getTag();

		ImageView imageView = holder.imageView;
		TextView nameView= holder.nameView;
		TextView dateView=holder.dateView;
		ImageView callImageView= holder.callImageView;

		final CallVO vo = datas.get(position);

		nameView.setText(vo.name);
		dateView.setText(vo.date);

		if(vo.name.equals("홍길동")){
			imageView.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(),R.drawable.hong,null));
		}
		else{
			imageView.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(),R.drawable.ic_person,null));
		}

		callImageView.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_CALL);
				intent.setData(Uri.parse("tel:" + vo.callnb));
				context.startActivity(intent);
			}
		});



		return convertView;
	}
}
