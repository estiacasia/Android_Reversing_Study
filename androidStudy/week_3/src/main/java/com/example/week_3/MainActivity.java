package com.example.week_3;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MainActivity extends AppCompatActivity implements  OnMapReadyCallback, GoogleMap.OnCameraMoveListener, GoogleMap.OnCameraIdleListener {

	GoogleMap map;

	GoogleApiClient googleApiClient;
	FusedLocationProviderApi fusedLocationProviderApi;
	LocationManager locationManager;
	Location location;
	Marker marker;
	TextView addressView;
	String resultAddress;
	double mLat;
	double mLng;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		addressView = (TextView) findViewById(R.id.m1_address);
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.m1_map)).getMapAsync(this);
	}


	@Override
	public void onMapReady(GoogleMap googleMap) {
		map = googleMap;
//		if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
//			location=fusedLocationProviderApi.getLastLocation(googleApiClient);
//		}
//		else {
//			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
//		}
		String locationProvider = LocationManager.NETWORK_PROVIDER;
		location = locationManager.getLastKnownLocation(locationProvider);
		LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
		CameraPosition position = new CameraPosition.Builder().target(latLng).zoom(16f).build();
		map.moveCamera(CameraUpdateFactory.newCameraPosition(position));
		marker=map.addMarker(new MarkerOptions().position(latLng).title("Location").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_start)));
		map.setOnCameraIdleListener(this);
		map.setOnCameraMoveListener(this);

		MyGeocodingThread thread=new MyGeocodingThread(latLng);
		thread.start();
	}

//	private void dragMap(){
//		if (location != null && map != null) {
//			LatLng latLng = new LatLng(37, 126);
//			CameraPosition position = new CameraPosition.Builder().target(latLng).zoom(16f).build();
//			map.moveCamera(CameraUpdateFactory.newCameraPosition(position));
//
//			marker=map.addMarker(new MarkerOptions().position(latLng).title("Location").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_start)));
//
//			MyGeocodingThread thread=new MyGeocodingThread(latLng);
//			thread.start();
//		}
//	}

//	@Override
//	public void onConnected(@Nullable Bundle bundle) {
//		if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
//			location=fusedLocationProviderApi.getLastLocation(googleApiClient);
//			LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//			CameraPosition position = new CameraPosition.Builder().target(latLng).zoom(16f).build();
//			map.moveCamera(CameraUpdateFactory.newCameraPosition(position));
//			marker=map.addMarker(new MarkerOptions().position(latLng).title("Location").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_start)));
//			map.setOnCameraIdleListener(this);
//			map.setOnCameraMoveListener(this);
//			MyGeocodingThread thread=new MyGeocodingThread(latLng);
//			thread.start();
//		}
//		else {
//			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
//		}
//	}
//
//	@Override
//	public void onConnectionSuspended(int i) {
//		showToast("위치 사용 불가");
//	}
//
//	@Override
//	public void onConnectionFailed(ConnectionResult connectionResult) {
//		showToast("위치 얻기 실패");
//	}

	@Override
	public void onCameraIdle() {
		MyGeocodingThread thread=new MyGeocodingThread(map.getCameraPosition().target);
		thread.start();
		mLat=map.getCameraPosition().target.latitude;
		mLng=map.getCameraPosition().target.longitude;
	}

	@Override
	public void onCameraMove() {
		marker.setPosition(map.getCameraPosition().target);
	}

	//아래 주소
	class MyGeocodingThread extends Thread {
		LatLng latLng;

		public MyGeocodingThread(LatLng latLng) {
			this.latLng = latLng;
		}

		@Override
		public void run() {
			Geocoder geocoder = new Geocoder(MainActivity.this);
			List<Address> addresses = null;
			String addressText = "";
			try {
				addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
				Thread.sleep(500);
				if (addresses != null && addresses.size() > 0) {
					Address address = addresses.get(0);
					addressText = address.getAdminArea() + " " + (address.getMaxAddressLineIndex() > 0 ?
							address.getAddressLine(0) : address.getLocality()) + " ";
					String txt = address.getSubLocality();
					if (txt != null)
						addressText += txt + " ";
					addressText += address.getThoroughfare() + " " + address.getSubThoroughfare();

					Message msg = new Message();
					msg.what = 100;
					msg.obj = addressText;
					handler.sendMessage(msg);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 100: {
					addressView.setText((String) msg.obj);
				}
			}
		}
	};

	private void showToast(String message){
		Toast toast=Toast.makeText(this, message, Toast.LENGTH_LONG);
		toast.show();
	}


}