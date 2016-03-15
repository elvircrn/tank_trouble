package com.elvircrn.TankTrouble.android;

import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.elvircrn.TankTrouble.android.Blue.ClientActivity;
import com.elvircrn.TankTrouble.android.Blue.ServerActivity;

public class AndroidLauncher extends AndroidApplication implements Tenkici.MyGameCallback {

    public static Tenkici tenkici;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		config.useAccelerometer = false;
		config.useCompass = false;

		tenkici = new Tenkici();
		tenkici.setMyGameCallback(this);

		initialize(tenkici, config);
	}

	@Override
	public void onStartActivityServer() {
		Intent intent = new Intent(this, ServerActivity.class);
		startActivity(intent);
	}

	@Override
	public void onStartActivityClient() {
		Intent intent = new Intent(this, ClientActivity.class);
		startActivity(intent);
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
