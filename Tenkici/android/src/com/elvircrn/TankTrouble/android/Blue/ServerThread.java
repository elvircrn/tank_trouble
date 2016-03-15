package com.elvircrn.TankTrouble.android.Blue;

import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.elvircrn.TankTrouble.android.GameMaster;

import java.io.IOException;

public class ServerThread extends Thread {
    private BluetoothServerSocket serverSocket;

    public ServerThread() {

    }

    public void init() throws Exception {
        if (isAlive())
            return;

        if (BTManager.bluetoothAdapter == null)
            throw new Exception("SERVER init: BluetoothAdapter doesn't exist");

        BluetoothServerSocket tmp = null;

        try {
            tmp = BTManager.bluetoothAdapter.listenUsingRfcommWithServiceRecord("elvircrn", BTManager.myUUID);
        }
        catch (IOException e) {
            Log.d("SERVER init", e.getMessage());
        }

        serverSocket = tmp;
        GameMaster.setMode(GameMaster.Mode.SERVER);
    }

    public void run() {
        BluetoothSocket bluetoothSocket;

        while (true) {
            try {
                bluetoothSocket = serverSocket.accept();
            }
            catch (IOException e) {
                Log.d("SERVERCONNECT", "Could not accept an incoming connection.");
                break;
            }

            if (bluetoothSocket != null) {
                BTManager.handshake = new ManageConnectThread(bluetoothSocket);
                BTManager.handshake.start();

                try {
                    serverSocket.close();
                }
                catch (IOException e) {
                    Log.d("SERVER run", e.getMessage());
                }
                break;
            }
        }
    }

    /** Will cancel the listening socket, and cause the thread to finish */
    public void cancel() {
        try {
            serverSocket.close();
        } catch (IOException e) { }
    }

}
