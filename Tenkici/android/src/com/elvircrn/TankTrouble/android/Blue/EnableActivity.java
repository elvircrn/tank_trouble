package com.elvircrn.TankTrouble.android.Blue;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;

import com.elvircrn.TankTrouble.android.R;

public class EnableActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enable);

        if (!BTManager.bluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, BTManager.REQUEST_ENABLE_BT);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
