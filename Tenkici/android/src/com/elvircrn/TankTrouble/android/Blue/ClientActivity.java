package com.elvircrn.TankTrouble.android.Blue;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.badlogic.gdx.Gdx;
import com.elvircrn.TankTrouble.android.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class ClientActivity extends Activity {
    public static final int REQUEST_ENABLE_BT = 1;

    private ListView list;
    private ArrayList<String> deviceList;
    private ArrayList<BluetoothDevice> remoteDevices;
    private ArrayAdapter<String> listAdapter;

    private void initButtons() {
        android.widget.Button button = (android.widget.Button)findViewById(R.id.sendHelloButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = "Hello World";
                try {
                    BTManager.handshake.sendData(s.getBytes());
                }
                catch (IOException e) {
                    Log.d("CLIENTACTIVITY send", e.getMessage());
                }
            }
        });
    }

    private void addDevice(BluetoothDevice device) {
        String candidate = device.getName() + " " + device.getAddress();

        Gdx.app.log("device found: ", device.getName() + " " + device.getAddress());

        for (int i = 0; i < listAdapter.getCount(); i++)
            if (listAdapter.getItem(i) == candidate)
                return;

        listAdapter.add(device.getName() + " " + device.getAddress());
        remoteDevices.add(device);
    }

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                addDevice(device);
            }
        }
    };

    protected void initializeList() {
        list = (ListView)findViewById(R.id.listView);
        deviceList = new ArrayList<String>();
        remoteDevices = new ArrayList<BluetoothDevice>();
        listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, deviceList);
        list.setAdapter(listAdapter);

        Set<BluetoothDevice> bonded = BTManager.bluetoothAdapter.getBondedDevices();

        for (BluetoothDevice device : bonded)
            addDevice(device);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (BTManager.clientThread == null)
                    BTManager.clientThread = new ClientThread();

                if (BTManager.clientThread.getState() != Thread.State.NEW) {
                    BTManager.clientThread.cancel();
                    BTManager.clientThread.init(remoteDevices.get(i));
                    BTManager.clientThread.start();
                }
                else if (BTManager.clientThread.getState() == Thread.State.NEW) {
                    BTManager.clientThread.init(remoteDevices.get(i));
                    BTManager.clientThread.start();
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        registerReceiver(broadcastReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));

        BTManager.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        initializeList();

        if (!BTManager.bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        BTManager.bluetoothAdapter.startDiscovery();
        initButtons();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        finishActivity(REQUEST_ENABLE_BT);
    }
}
