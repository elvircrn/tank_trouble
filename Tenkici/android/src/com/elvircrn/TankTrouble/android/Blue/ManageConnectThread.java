package com.elvircrn.TankTrouble.android.Blue;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ManageConnectThread extends Thread {
    private BluetoothSocket bluetoothSocket;
    private InputStream inputStream;
    private OutputStream outputStream;

    public ManageConnectThread() {

    }

    public ManageConnectThread(BluetoothSocket bluetoothSocket) {
        this.bluetoothSocket = bluetoothSocket;

        InputStream tmpIn;
        OutputStream tmpOut;

        try {
            tmpIn  = bluetoothSocket.getInputStream();
            tmpOut = bluetoothSocket.getOutputStream();
        }
        catch (IOException e) {
            Log.d("MANAGECONNECT constr", e.getMessage());
            return;
        }

        inputStream = tmpIn;
        outputStream = tmpOut;
    }

    public void run() {
        byte[] buffer = new byte[1024];
        int bytes;

        Log.d("MANAGECONNECT", "Establishing handshake");

        while (true) {
            try {
                bytes = inputStream.read(buffer);
                BTManager.receiveData(bytes, buffer);
            }
            catch (IOException e) {
                Log.d("MANAGECONNECT run", e.getMessage());
                break;
            }
        }
    }

    public void sendData(byte[] data) throws IOException {
        Log.d("MANAGE CONNECT", "senData called");
        outputStream.write(data);
        outputStream.flush();
    }

    public void cancel() {
        try {
            bluetoothSocket.close();
        }
        catch (IOException e) {
            Log.d("MANAGECONNECT cancel", e.getMessage());
        }
    }
}
