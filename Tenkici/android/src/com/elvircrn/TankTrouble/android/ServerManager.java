package com.elvircrn.TankTrouble.android;

public class ServerManager {
    public static float[] floatDbg = new float[100];

    public static float locationFactor = 10.0f;
    public static float rotationFactor = 100.0f;
    public static void writeTankLocation(ByteArrayList messageBuffer) {
        Tank tank = TankManager.get(CodeManager.ServerTankIndex);

        int floatBits = Float.floatToIntBits(tank.rotation);

        short short0 = Serializer.getShort0(floatBits);
        short short1 = Serializer.getShort1(floatBits);

        Serializer.serializeMessage(messageBuffer,
                                    CodeManager.TankServerPosition,
                                    (short)4,
                                    new short[] {(short)(tank.worldLocation.x * locationFactor),
                                                 (short)(tank.worldLocation.y * locationFactor),
                                                 short0,
                                                 short1});
    }

    public static void writeBullets(ByteArrayList messageBuffer) {
        Serializer.serializeMessage(messageBuffer,
                CodeManager.BulletLocations,
                BulletManager.bullets);

        Serializer.deserializeMessage(floatDbg, (int) messageBuffer.getContents()[0], messageBuffer.getContents());

        FPSCounter.extraMessage = "";

        for (int i = 1; i < (int)floatDbg [0]; i++)
            FPSCounter.extraMessage += Float.toString(floatDbg [i]) + " ";
    }
}
