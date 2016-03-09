package com.elvircrn.TankTrouble.android;

public class Serializer {
    public static void serializeMessage(ByteArrayList byteArrayList, byte code, short n, short[] shorts) {
        byteArrayList.clear();

        byteArrayList.add(code);
        byteArrayList.add((byte)((n >> 8) & 0xFF));
        byteArrayList.add((byte)(n & 0xFF));

        for (int i = 0; i < n; i++) {
            byteArrayList.add((byte)((shorts [i] >> 8) & 0xFF));
            byteArrayList.add((byte)(shorts [i] & 0xFF));
        }
    }

    public static short[] deserializeMessage(ByteArrayList byteArrayList) throws Exception {
        byte code = byteArrayList.get(0);
        int n = byteArrayList.getShort(1);

        if (n <= 0)
            throw new Exception("Serializer retrieved (n <= 0)");

        short[] shorts = new short[n];

        for (int i = 0; i < n; i++) {
            byte byte0 = byteArrayList.get(3 + 2 * i);
            byte byte1 = byteArrayList.get(4 + 2 * i);

            shorts [i] = (short) ((byte0 << 8) + (byte1 & 0xFF));
        }

        return shorts;
    }
}
