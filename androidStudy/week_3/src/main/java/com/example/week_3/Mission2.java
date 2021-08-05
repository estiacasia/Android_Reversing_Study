package com.example.week_3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class Mission2 extends AppCompatActivity implements OnMapReadyCallback {

	GoogleMap map;
	Location location;
	LocationManager locationManager;
	ArrayList<BankData> bankList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mission2);

		bankList=GeoData.getAddressData();
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.m2_map)).getMapAsync(this);
	}

	@Override
	public void onMapReady(GoogleMap googleMap) {
		map = googleMap;
		String locationProvider = LocationManager.NETWORK_PROVIDER;
		location = locationManager.getLastKnownLocation(locationProvider);
		if(location != null && map != null) {
			LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
			CameraPosition position = new CameraPosition.Builder().target(latLng).zoom(16f).build();
			map.moveCamera(CameraUpdateFactory.newCameraPosition(position));
			map.addMarker(new MarkerOptions().position(latLng).title("Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
			drawCircle();
		} else {
			LatLng latLng = new LatLng(37.37456533047803, 126.80999180442507);
			CameraPosition position = new CameraPosition.Builder().target(latLng).zoom(16f).build();
			map.moveCamera(CameraUpdateFactory.newCameraPosition(position));
			map.addMarker(new MarkerOptions().position(latLng).title("Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
			drawCircle();
		}

	}


	private static final double EARTH_RADIUS = 6378100.0;
	private int offset;
	//return pixel length
	private int convertMetersToPixels(double lat, double lng, double radiusInMeters) {
		double lat1 = radiusInMeters / EARTH_RADIUS;
		double lng1 = radiusInMeters / (EARTH_RADIUS * Math.cos((Math.PI * lat / 180)));
		double lat2 = lat + lat1 * 180 / Math.PI;
		double lng2 = lng + lng1 * 180 / Math.PI;

		Point p1 = map.getProjection().toScreenLocation(new LatLng(lat, lng));
		Point p2 = map.getProjection().toScreenLocation(new LatLng(lat2, lng2));
		return Math.abs(p1.x - p2.x);
	}
	//원 하단아닌 중심으로 세팅
	private LatLng getCoords(double lat, double lng) {
		LatLng latLng = new LatLng(lat, lng);
		Projection projection = map.getProjection();
		Point p = projection.toScreenLocation(latLng);
		p.set(p.x, p.y + offset);
		return projection.fromScreenLocation(p);
	}
	// 확대 수준에 따라 원 크기 결정
	private Bitmap getCircleBitmap(LatLng latLng, int r) {
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(0x110000FF);
		paint.setStyle(Paint.Style.FILL);

		Paint paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint1.setColor(0xFF0000FF);
		paint1.setStyle(Paint.Style.STROKE);

		int radius = offset = convertMetersToPixels(latLng.latitude, latLng.longitude, r);

		Bitmap bitmap = Bitmap.createBitmap(radius * 2, radius * 2, Bitmap.Config.ARGB_8888);
		Canvas c = new Canvas(bitmap);
		c.drawCircle(radius, radius, radius, paint);
		c.drawCircle(radius, radius, radius, paint1);

		return bitmap;
	}

	private void drawCircle(){
		LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
		int r = 500;
		Bitmap bitmap = getCircleBitmap(latLng, r);
		map.addMarker(new MarkerOptions()
				.position(getCoords(latLng.latitude, latLng.longitude))
				.icon(BitmapDescriptorFactory.fromBitmap(bitmap)));
	}
}