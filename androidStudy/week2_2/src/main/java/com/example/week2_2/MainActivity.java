package com.example.week2_2;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

public class MainActivity extends AppCompatActivity {

	DrawerLayout drawer;
	ActionBarDrawerToggle toggle;
	boolean isDrawerOpened;
	Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayShowTitleEnabled(false);

		drawer = (DrawerLayout) findViewById(R.id.main_drawer);
		toggle = new ActionBarDrawerToggle(this, drawer, R.string.drawer_open, R.string.drawer_close) {
			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				isDrawerOpened = true;
			}

			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
				isDrawerOpened = false;
			}
		};
		drawer.addDrawerListener(toggle);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		toggle.syncState();


	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (toggle.onOptionsItemSelected(item)) {
			return false;
		}
		return super.onOptionsItemSelected(item);
	}

	public void onBackPressed() {
		if (isDrawerOpened) {
			drawer.closeDrawers();
		} else {
			super.onBackPressed();
		}
	}
}