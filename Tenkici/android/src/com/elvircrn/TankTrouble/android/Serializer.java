package com.elvircrn.TankTrouble.android;

public class Serializer {
    public static short getShort(byte byte0, byte byte1) {
        return (short) ((byte0 << 8) + (byte1 & 0xFF));
    }

    public static byte getByte0(short number) {
        return ((byte)((number >> 8) & 0xFF));
    }

    public static byte getByte1(short number) {
        return ((byte)(number & 0xFF));
    }

    public static void serializeMessage(ByteArrayList byteArrayList, byte code, short n, short[] shorts) {
        byteArrayList.clear();

        byteArrayList.add((byte)(3 + 2 * n));
        byteArrayList.add(code);
        byteArrayList.add(getByte0(n));
        byteArrayList.add(getByte1(n));

        for (int i = 0; i < n; i++) {
            byteArrayList.add(getByte0(shorts [i]));
            byteArrayList.add(getByte1(shorts [i]));
        }
    }

    public static void serializeMessage(ByteArrayList byteArrayList, byte code) {
        byteArrayList.clear();
        byteArrayList.add((byte)3);
        byteArrayList.add(code);
        byteArrayList.addShort((short)0);
    }

    public static void serializeMessage(byte[] bytes, int n, short[] shorts) {
        bytes [0] = (byte)n;
        for (int i = 0; i < n; i++) {
            bytes [2 * i]     = getByte0(shorts [i]);
            bytes [2 * i + 1] = getByte1(shorts [i]);
        }
    }

    public static void deserializeMessage(short[] shorts, int n, byte[] bytes) throws Exception {
        if (n <= 0)
            throw new Exception("Serializer retrieved (n <= 0)");

        shorts [0] = (short)bytes[1];

        for (int i = 2; i < n; i += 2)
            shorts [i / 2] = getShort(bytes [i], bytes [i + 1]);
    }

    public static short[] deserializeMessage(ByteArrayList byteArrayList) throws Exception {
        byte code = byteArrayList.get(0);
        int n = byteArrayList.getShort(1);

        if (n <= 0)
            throw new Exception("Serializer retrieved (n <= 0)");

        short[] shorts = new short[n + 1];

        shorts [0] = (short)n;

        for (int i = 1; i < n; i++) {
            byte byte0 = byteArrayList.get(3 + 2 * (i - 1));
            byte byte1 = byteArrayList.get(4 + 2 * (i - 1));

            shorts [i] = (short) ((byte0 << 8) + (byte1 & 0xFF));
        }

        return shorts;
    }
}
