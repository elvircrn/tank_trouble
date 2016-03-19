package com.elvircrn.TankTrouble.android.Blue;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.elvircrn.TankTrouble.android.FPSCounter;
import com.elvircrn.TankTrouble.android.GameMaster;

import java.io.IOException;

public class ClientThread extends Thread {
    private BluetoothSocket bluetoothSocket;
    private BluetoothDevice bluetoothDevice;

    public volatile boolean running = false;

    public ClientThread () {

    }

    public ClientThread (BluetoothDevice bluetoothDevice) {
        init(bluetoothDevice);
    }

    public void init (BluetoothDevice bluetoothDevice) {
        BluetoothSocket tmp;
        running = false;
        this.bluetoothDevice = bluetoothDevice;

        try {
            tmp = this.bluetoothDevice.createRfcommSocketToServiceRecord(BTManager.myUUID);
        }
        catch (IOException e) {
            Log.d("CONNECTTHREAD constr", e.getMessage());
            return;
        }

        this.bluetoothSocket = tmp;
        GameMaster.setMode(GameMaster.Mode.CLIENT);
    }

    @Override
    public void run() {
        running = true;
        BTManager.bluetoothAdapter.cancelDiscovery();

        try {
            Log.d("CONNECTTHREAD run", "connecting to server");
            bluetoothSocket.connect();
        }
        catch(IOException connectException) {
            Log.d("CONNECTTHREAD run", connectException.toString());
            try {
                bluetoothSocket.close();
            }
            catch(IOException closeException) {
                Log.d("CONNECTTHREAD run", closeException.toString());
                running = false;
                return;
            }

            running = false;
            return;
        }

        Log.d("CLEINT run", "Connection to server established");

        if (BTManager.handshake != null)
            BTManager.handshake.cancel();

        BTManager.handshake = new ManageConnectThread(bluetoothSocket);
        BTManager.handshake.start();

        FPSCounter.extraMessage = "HANDSHAKE";

        running = false;

        Log.d("ClientThread: ", "finished");
    }

    public boolean cancel() {
        running = false;
        if (BTManager.handshake != null)
            BTManager.handshake.cancel();
        return true;
    }
}
