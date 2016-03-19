package com.elvircrn.TankTrouble.android;

public class ClientManager {
    public static float locationFactor = 10.0f;
    public static float rotationFactor = 100.0f;

    public static void writeTankLocation(ByteArrayList messageBuffer) {
		Tank tank = TankManager.get(CodeManager.ClientTankIndex);

        int floatBits = Float.floatToIntBits(tank.rotation);

        short short0 = Serializer.getShort0(floatBits);
        short short1 = Serializer.getShort1(floatBits);

        Serializer.serializeMessage(messageBuffer,
                                    CodeManager.TankClientPosition,
                                    (short)4,
                                    new short[] {(short)(tank.worldLocation.x * locationFactor),
                                                 (short)(tank.worldLocation.y * locationFactor),
                                                 short0,
                                                 short1});
    }

    public static void shoot(ByteArrayList messageBuffer, float x, float y, float dirX, float dirY) {
        synchronized (ClientManager.class) {
            Serializer.serializeMessage(messageBuffer, CodeManager.ClientShotFired, x, y, dirX, dirY);
        }
    }
}
