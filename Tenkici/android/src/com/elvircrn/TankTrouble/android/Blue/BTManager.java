package com.elvircrn.TankTrouble.android.Blue;

import android.bluetooth.BluetoothAdapter;
import android.util.Log;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.elvircrn.TankTrouble.android.Button;
import com.elvircrn.TankTrouble.android.ByteArrayList;
import com.elvircrn.TankTrouble.android.ClientManager;
import com.elvircrn.TankTrouble.android.CodeManager;
import com.elvircrn.TankTrouble.android.GameMaster;
import com.elvircrn.TankTrouble.android.Input;
import com.elvircrn.TankTrouble.android.Serializer;
import com.elvircrn.TankTrouble.android.TankManager;

import java.io.IOException;
import java.util.UUID;

public class BTManager {
    //region data stuff
    public static final int buffSize = 1024;
    public static ByteArrayList byteArray;
    public static byte[] bytes;
    public static short[] shorts;

    public static ByteArrayList messageBuffer;
    //endregion

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
        bytes = new byte[buffSize];
        shorts = new short[buffSize];
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
        int nBytes = data [0];
        String debugMessage = "";
        //Log.d("sendData", "SEND DATA CALLED");
        for (int i = 0; i < nBytes; i++)
            debugMessage += (Integer.toString((int)data [i]) + " ");
        //Gdx.app.log("sending: ", debugMessage);
        handshake.sendData(data);
    }

    public static void receiveData(int arg, byte[] data) throws IOException {
        byte code, nBytes = data[0];

        //Log.d("recevie", "RECEIVE DATA CALLED");

        try {
            Serializer.deserializeMessage(shorts, nBytes, data);
        //    Log.d("received: ", "code: " + Byte.toString(data[0]));
            code = data[1];
        }
        catch (Exception e) {
          //  Log.d("RECEIVEDT: ", e.getMessage());
            return;
        }

        if (code == CodeManager.NewGameResponse) {
            short seed = shorts[1];
            //Log.d("RECEIVE", "NEW GAME RESPONSE " + Short.toString(seed));
            GameMaster.initNewRound(seed);
        }
        else if (code == CodeManager.RequestNewGame) {
            short seed = (short) (System.currentTimeMillis() / 1000);
            GameMaster.initNewRound(seed);

            try {
              //  Log.d("PROCESSING", "REQUEST NEW GAME CALL");
                bytes[0] = 3;
                bytes[1] = CodeManager.NewGameResponse;

                bytes[2] = Serializer.getByte0(seed);
                bytes[3] = Serializer.getByte1(seed);

                sendData(bytes);

                String debugMessage = "";
                for (int i = 0; i < nBytes; i++)
                    debugMessage += (Integer.toString((int)data [i]) + " ");
                //Log.d("sending: ", debugMessage);
            }
            catch (IOException e) {
                //Log.d("B TRECEIVEDAT", e.getMessage());
                return;
            }
        }
        else if (code == CodeManager.FrameClient) {

        }
        else if (code == CodeManager.FramerServer) {

        }
        else if (code == CodeManager.TankClientPosition) {
            try {
                Serializer.deserializeMessage(shorts, (int) data[0], data);
                /*Log.d("sth", "setting tank positionsL " + Float.toString((float) shorts[2]) + " " +
                        Float.toString((float) shorts[3]));*/
                TankManager.tanks [CodeManager.ClientTankIndex]
                            .worldLocation.set((shorts [2] / ClientManager.factor),
                                               (shorts [3] / ClientManager.factor));
            }
            catch (Exception e) {
                Log.d("BTMANAGER", "deserialization failed");
            }
        }
        else {
            Log.d("INVALID CODE: ", "received: " + Byte.toString(code));
        }
    }
}
