package com.elvircrn.TankTrouble.android;

public class ClientManager {
    public static void writeTankLocation(ByteArrayList messageBuffer) {
		Tank tank = TankManager.get(CodeManager.ClientTankIndex);
        Serializer.serializeMessage(messageBuffer,
                                    CodeManager.TankClientPosition,
                                    (short)2,
                                    new short[] { (short)tank.worldLocation.x, (short)tank.worldLocation.y});
    }
}
