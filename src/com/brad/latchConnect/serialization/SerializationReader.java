package com.brad.latchConnect.serialization;

import com.brad.latchConnect.serialization.type.Type;

import java.nio.ByteBuffer;

/**
 * Contains static methods for reading common data types from
 * a byte array.
 */
public final class SerializationReader {

    /**
     * Don't let anyone instantiate this class.
     */
    private SerializationReader() {}

    public static byte readByte(byte[] src, int pointer) {
        return src[pointer];
    }

    public static void readBytes(byte[] src, int pointer, byte[] dest) {
        for (int i = 0; i < dest.length; i++) {
            dest[i] = src[pointer + i];
        }
    }

    public static void readShorts(byte[] src, int pointer, short[] dest) {
        for (int i = 0; i < dest.length; i++) {
            dest[i] = readShort(src, pointer);
            pointer += Type.SHORT.getSize();
        }
    }

    public static void readChars(byte[] src, int pointer, char[] dest) {
        for (int i = 0; i < dest.length; i++) {
            dest[i] = readChar(src, pointer);
            pointer += Type.CHAR.getSize();
        }
    }

    public static void readInts(byte[] src, int pointer, int[] dest) {
        for (int i = 0; i < dest.length; i++) {
            dest[i] = readInt(src, pointer);
            pointer += Type.INTEGER.getSize();
        }
    }

    public static void readLongs(byte[] src, int pointer, long[] dest) {
        for (int i = 0; i < dest.length; i++) {
            dest[i] = readLong(src, pointer);
            pointer += Type.LONG.getSize();
        }
    }

    public static void readFloats(byte[] src, int pointer, float[] dest) {
        for (int i = 0; i < dest.length; i++) {
            dest[i] = readFloat(src, pointer);
            pointer += Type.FLOAT.getSize();
        }
    }

    public static void readDoubles(byte[] src, int pointer, double[] dest) {
        for (int i = 0; i < dest.length; i++) {
            dest[i] = readDouble(src, pointer);
            pointer += Type.DOUBLE.getSize();
        }
    }

    public static void readBooleans(byte[] src, int pointer, boolean[] dest) {
        for (int i = 0; i < dest.length; i++) {
            dest[i] = readBoolean(src, pointer);
            pointer += Type.BOOLEAN.getSize();
        }
    }

    /**
     * Interprets the data at the given index (pointer) in
     * the byte array as a short, and returns that value.
     * @param src       A byte array
     * @param pointer   An index in the byte array
     * @return          A short representation of the next 2 bytes
     *                  after that index in the array (inclusive)
     */
    public static short readShort(byte[] src, int pointer) {
        return ByteBuffer.wrap(src, pointer, 2).getShort();
    }

    public static char readChar(byte[] src, int pointer) {
        return ByteBuffer.wrap(src, pointer, 2).getChar();
    }

    public static int readInt(byte[] src, int pointer) {
        return ByteBuffer.wrap(src, pointer, 4).getInt();
    }

    public static long readLong(byte[] src, int pointer) {
        return ByteBuffer.wrap(src, pointer, 8).getLong();
    }

    public static float readFloat(byte[] src, int pointer) {
        return Float.intBitsToFloat(readInt(src, pointer));
    }

    public static double readDouble(byte[] src, int pointer) {
        return Double.longBitsToDouble(readLong(src, pointer));
    }

    public static boolean readBoolean(byte[] src, int pointer) {
        assert(src[pointer] == 0 || src[pointer] == 1);
        return src[pointer] != 0;
    }

    public static String readString(byte[] src, int pointer, int length) {
        return new String(src, pointer, length);
    }
}
