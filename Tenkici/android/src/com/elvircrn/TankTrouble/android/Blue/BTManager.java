package com.elvircrn.TankTrouble.android.Blue;

import android.bluetooth.BluetoothAdapter;
import android.util.Log;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.elvircrn.TankTrouble.android.Button;
import com.elvircrn.TankTrouble.android.CodeManager;
import com.elvircrn.TankTrouble.android.GameMaster;
import com.elvircrn.TankTrouble.android.Input;

import java.io.IOException;
import java.util.UUID;

public class BTManager {
    //region Bluetooth stuff
    public static BluetoothAdapter bluetoothAdapter;
    public static final int REQUEST_ENABLE_BT = 1;
    public static UUID myUUID;
    public static final String uuidString = "31490b26-2cae-423a-b518-1736290908e2";

    public static ClientThread clientThread = null;
    public static ServerThread serverThread = null;
    public static ManageConnectThread handshake;

    //endregion

    //region UI Stuff
    public static Button serverButton;
    public static Button clientButton;
    public static Button syncButton;
    //endregion

    public static void init() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        myUUID = UUID.fromString(uuidString);

        serverThread = new ServerThread();
    }

    public static void update() {
        serverButton.update(Input.touchList);
        clientButton.update(Input.touchList);
        syncButton.update(Input.touchList);
    }

    public static void draw(SpriteBatch batch) {
        serverButton.draw(batch);
        clientButton.draw(batch);
        syncButton.draw(batch);
    }

    public static void sendData(byte[] data) throws IOException {
        handshake.sendData(data);
    }

    public static void receiveData(int bytes, byte[] data) throws IOException {
        String string = new String(data);

        if (string.charAt(0) == CodeManager.NewGameResponse) {
            int seed = Integer.parseInt(string.substring(1));
            GameMaster.initNewRound(seed);
        }
        else if (string.charAt(0) == CodeManager.RequestNewGame) {
            int seed = (int)System.currentTimeMillis() / 1000;
            string = String.valueOf(CodeManager.NewGameResponse) + Integer.toString(seed);
            while (true) {
                try {
                    sendData(string.getBytes());
                    continue;
                }
                catch (IOException e) {
                    Log.d("BTRECEIVEDAT", e.getMessage());
                    break;
                }
            }
        }
    }
}
