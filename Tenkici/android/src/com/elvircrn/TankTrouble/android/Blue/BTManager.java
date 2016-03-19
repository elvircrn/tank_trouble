package com.elvircrn.TankTrouble.android.Blue;

import android.bluetooth.BluetoothAdapter;
import android.util.Log;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.elvircrn.TankTrouble.android.BulletManager;
import com.elvircrn.TankTrouble.android.Button;
import com.elvircrn.TankTrouble.android.ByteArrayList;
import com.elvircrn.TankTrouble.android.ClientManager;
import com.elvircrn.TankTrouble.android.CodeManager;
import com.elvircrn.TankTrouble.android.GameMaster;
import com.elvircrn.TankTrouble.android.Graphics;
import com.elvircrn.TankTrouble.android.Input;
import com.elvircrn.TankTrouble.android.OtherGraphics;
import com.elvircrn.TankTrouble.android.Serializer;
import com.elvircrn.TankTrouble.android.TankManager;

import java.io.IOException;
import java.util.UUID;

//hej momci u plavom...
public class BTManager {
    //region data stuff
    public static final int buffSize = 1024;
    public static byte[] bytes;
    public static short[] shorts;
    public static float[] floats;

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
        floats = new float[buffSize];
        messageBuffer = new ByteArrayList(buffSize);
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
        if (handshake != null)
            handshake.sendData(data);
    }

    public static synchronized void receiveData(int arg, byte[] data) throws IOException {
        byte code = data [1], nBytes = data[0];

        //Log.d("recevie", "RECEIVE DATA CALLED");

        if (data [1] == CodeManager.ClientShotFired) {
            Serializer.deserializeShot(floats, data);
        }
        else if (data [1] != CodeManager.BulletLocations) {
            try {
                Serializer.deserializeMessage(shorts, nBytes, data);
                //    Log.d("received: ", "code: " + Byte.toString(data[0]));
            }
            catch (Exception e) {
                //  Log.d("RECEIVEDT: ", e.getMessage());
                return;
            }
        }
        else {
            Serializer.deserializeMessage(floats, nBytes, data);
        }

        if (code == CodeManager.NewGameResponse) {
            short seed = shorts[2];
            //Log.d("RECEIVE", "NEW GAME RESPONSE " + Short.toString(seed));
            GameMaster.initNewRound(seed);
            Log.d("GRAPHICS: ", "Other Height: " + Float.toString(OtherGraphics.prefferedHeight) + " " + " This Height: " + Float.toString(Graphics.prefferedHeight));
        }
        else if (code == CodeManager.RequestNewGame) {
            short seed = (short) (System.currentTimeMillis() / 1000);
            GameMaster.initNewRound(seed);

            OtherGraphics.prefferedHeight = shorts [2] / 10.f;

            Log.d("GRAPHICS: ", "Other Height: " + Float.toString (OtherGraphics.prefferedHeight) + " " + " This Height: " + Float.toString(Graphics.prefferedHeight));

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
                /*Log.d("sth", "setting tank positionsL " + Float.toString((float) shorts[2]) + " " +
                        Float.toString((float) shorts[3]));*/
                TankManager.tanks [CodeManager.ClientTankIndex]
                            .worldLocation.set((shorts [2] / ClientManager.locationFactor),
                                               OtherGraphics.getY(shorts [3] / ClientManager.locationFactor));

                TankManager.tanks [CodeManager.ClientTankIndex]
                        .rotation = Float.intBitsToFloat(Serializer.getInt(shorts [4], shorts [5]));
            }
            catch (Exception e) {
                Log.d("BTMANAGER", "deserialization failed");
            }
        }
        else if (code == CodeManager.TankServerPosition) {
            try {
                /*Log.d("sth", "setting tank positionsL " + Float.toString((float) shorts[2]) + " " +
                        Float.toString((float) shorts[3]));*/
                TankManager.tanks [CodeManager.ServerTankIndex]
                        .worldLocation.set((shorts [2] / ClientManager.locationFactor),
                        OtherGraphics.getY(shorts [3] / ClientManager.locationFactor));

                TankManager.tanks [CodeManager.ServerTankIndex].rotation =
                        Float.intBitsToFloat(Serializer.getInt(shorts [4], shorts [5]));
            }
            catch (Exception e) {
                Log.d("BTMANAGER", "deserialization failed");
            }
        }
        else if (GameMaster.getMode() == GameMaster.Mode.CLIENT && code == CodeManager.BulletLocations) {
            BulletManager.clearBullets();
            for (int i = 1; i < (int)floats[0]; i += 2) {
                //Log.d("(x, y): ", Float.toString(floats [i]) + " " + Float.toString(floats [i + 1]));
                BulletManager.addBullet(floats[i], floats[i + 1]);
            }
        }
        else if (code == CodeManager.ClientShotFired) {
            BulletManager.addBullet(floats [0], floats [1], floats [2], floats [3], 1);
        }
        else {
            Log.d("INVALID CODE: ", "received: " + Byte.toString(code));
        }
    }
}
//http://pokit.org/get/?4aedfa4926e2ed8ed7802415c085f28a.jpg