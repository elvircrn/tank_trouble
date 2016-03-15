package com.elvircrn.TankTrouble.android.Blue;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.elvircrn.TankTrouble.android.R;

public class ServerActivity extends Activity {
    public static final int REQUEST_ENABLE_BT = 1;


    public void initButton() {
        android.widget.Button button = (android.widget.Button)findViewById(R.id.startServerButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    BTManager.serverThread.init();
                }
                catch (Exception e) {
                    //Achievement get: Encounter builder pattern in the wild!
                    //This should never happen.
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(getApplicationContext())
                            .setTitle("Critical error")
                            .setMessage("Bluetooth device doesn't exist")
                            .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    dlgAlert.create();
                    dlgAlert.show();
                }

                if (!BTManager.serverThread.isAlive())
                    BTManager.serverThread.start();
            }
        });
    }

    public void init() {
        initButton();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);

        init();

        BTManager.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (!BTManager.bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        Intent discoverableIntent = new
                Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);

        BTManager.serverThread = new ServerThread();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        finishActivity(REQUEST_ENABLE_BT);
    }
}
