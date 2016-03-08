package com.elvircrn.TankTrouble.android.Blue;

import android.bluetooth.BluetoothAdapter;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.elvircrn.TankTrouble.android.AndroidLauncher;
import com.elvircrn.TankTrouble.android.Button;
import com.elvircrn.TankTrouble.android.Input;

import java.util.UUID;

/**
 * Created by elvircrn on 3/2/2016.
 */

public class BTManager {
    public static BluetoothAdapter bluetoothAdapter;
    public static Button someButton;
    public static final int REQUEST_ENABLE_BT = 1;
    public static UUID myUUID;
    public static final String uuidString = "31490b26-2cae-423a-b518-1736290908e2";

    public static ClientThread clientThread = null;
    public static ServerThread serverThread = null;
    public static ManageConnectThread handshake;

    public static void init() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        myUUID = UUID.fromString(uuidString);

        serverThread = new ServerThread();
    }

    public static void update() {
        someButton.update(Input.touchList);

        if (someButton.justPressed()) {
            AndroidLauncher.tenkici.myGameCallback.onStartActivityBTActivity();

        }
    }

    public static void draw(SpriteBatch batch) {
        someButton.draw(batch);
    }
}
