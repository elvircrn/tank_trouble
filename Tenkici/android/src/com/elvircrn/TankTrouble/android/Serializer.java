package com.elvircrn.TankTrouble.android;

import android.util.Log;

import com.badlogic.gdx.utils.Array;

public class Serializer {
    //region short
    public static short getShort(byte byte0, byte byte1) {
        return (short) ((byte0 << 8) + (byte1 & 0xFF));
    }

    public static short getShort0(int number) {
        return (short)((number >> 16) & 0xFFFF);
    }

    public static short getShort1(int number) {
        return (short)(number & 0xFFFF);
    }

    public static byte getByte0(short number) {
        return ((byte)((number >> 8) & 0xFF));
    }

    public static byte getByte1(short number) {
        return ((byte)(number & 0xFF));
    }
    //endregion

    //region int
    public static int getInt(short short0, short short1) {
        return (int) ((short0 << 16) + (short1 & 0xFFFF));
    }

    public static int getInt(byte byte0, byte byte1, byte byte2, byte byte3) {
        return getInt(getShort(byte0, byte1), getShort(byte2, byte3));
    }

    public static byte getByte0(int number) {
        return (byte)(number >> 24);
    }

    public static byte getByte1(int number) {
        return (byte)(number >> 16);
    }

    public static byte getByte2(int number) {
        return (byte)(number >> 8);
    }

    public static byte getByte3(int number) {
        return (byte)number;
    }
    //endregion

    public static float getFloat(int number) {
        return Float.intBitsToFloat(number);
    }

    public static void serializeMessage(ByteArrayList byteArrayList, byte code, short n, short[] shorts) {
        //TODO: izvidi ovo
        byteArrayList.clear();

        byteArrayList.add((byte) (1 + 2 * n));
        byteArrayList.add(code);

        for (int i = 0; i < n; i++) {
            byteArrayList.add(getByte0(shorts[i]));
            byteArrayList.add(getByte1(shorts[i]));
        }
    }

    public static void serializeMessage(ByteArrayList byteArrayList,
                                        byte code,
                                        float x,
                                        float y,
                                        float dirX,
                                        float dirY) {
        byteArrayList.add((byte)26);
        byteArrayList.add(code);
        byteArrayList.addFloat(x);
        byteArrayList.addFloat(y);
        byteArrayList.addFloat(dirX);
        byteArrayList.addFloat(dirY);
    }

    public static void deserializeShot(float[] floats, byte[] data) {
        for (int i = 2; i < 26; i += 4) {
            floats [i / 4] = getFloat(getInt(data[i], data[i + 1], data[i + 2], data[i + 3]));
        }
    }

    public static void serializeMessage(ByteArrayList byteArrayList, byte code, int n, float[] floats) {
        byteArrayList.clear();

        byteArrayList.add((byte)(4 * n + 1));
        byteArrayList.add(code);

        for (int i = 0; i < n; i++) {
            int floatBits = Float.floatToIntBits(floats [i]);

            byteArrayList.add(getByte0(floatBits));
            byteArrayList.add(getByte1(floatBits));
            byteArrayList.add(getByte2(floatBits));
            byteArrayList.add(getByte3(floatBits));
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

    public static void serializeMessage(ByteArrayList byteArrayList, byte code, Array<Bullet> bullets) {
        byteArrayList.add((byte)(8 * BulletManager.count() + 2));
        byteArrayList.add(code);

        for (Bullet bullet : bullets) {
            Log.d("serializing: ", Float.toString(bullet.worldLocation.x) + " " + Float.toString(bullet.worldLocation.y));
            byteArrayList.addFloat(bullet.worldLocation.x);
            byteArrayList.addFloat(bullet.worldLocation.y);
        }
    }

    public static void deserializeMessage(float[] floats, int n, byte[] bytes) {
        floats [0] = bytes [0] / 4;
        for (int i = 2; i < bytes [0]; i += 4) {
            floats [i / 4 + 1] = Float.intBitsToFloat(getInt(bytes [i], bytes [i + 1], bytes [i + 2], bytes [i + 3]));
        }
    }

    public static void deserializeMessage(short[] shorts, int n, byte[] bytes) throws Exception {
        if (n <= 0)
            throw new Exception("Serializer retrieved (n <= 0)");

        short nShorts = 0;
        shorts [0] = bytes [1]; //Code

        for (int i = 2; i < bytes [0]; i += 2) {
            nShorts++;
            shorts[i / 2 + 1] = getShort(bytes[i], bytes[i + 1]);
        }

        shorts [1] = nShorts;
    }

    public static void deserializeMessage(ByteArrayList byteArrayList, short[] shorts) throws Exception {
        byte code = byteArrayList.get(0);
        int n = byteArrayList.getShort(1);

        if (n <= 0)
            throw new Exception("Serializer retrieved (n <= 0)");

        shorts [0] = (short)n;

        for (int i = 1; i < n; i++) {
            byte byte0 = byteArrayList.get(3 + 2 * (i - 1));
            byte byte1 = byteArrayList.get(4 + 2 * (i - 1));

            shorts [i] = (short) ((byte0 << 8) + (byte1 & 0xFF));
        }
    }
}
