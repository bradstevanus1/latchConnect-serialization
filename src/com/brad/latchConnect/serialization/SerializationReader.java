package com.brad.latchConnect.serialization;

import java.nio.ByteBuffer;

/**
 * Contains static methods for reading common data types from
 * a byte array.
 */
public class SerializationReader {

    /**
     * Don't let anyone instantiate this class.
     */
    private SerializationReader() {}

    static byte readByte(byte[] src, int pointer) {
        return src[pointer];
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
        return (short) ((src[pointer] << 8) | src[pointer + 1]);
    }

    public static char readChar(byte[] src, int pointer) {
        return (char) ((src[pointer] << 8) | src[pointer + 1]);
    }


    public static int readInt(byte[] src, int pointer) {
        return ByteBuffer.wrap(src, pointer, 4).getInt();
        //return ((src[pointer] << 24) | (src[pointer + 1] << 16) | (src[pointer + 2] << 8) | src[pointer + 3]);
    }

    public static long readLong(byte[] src, int pointer) {
        return (long) ((src[pointer] << 56) | (src[pointer + 1] << 48) | (src[pointer + 2] << 40) | (src[pointer + 3] << 32) |
                        (src[pointer + 4] << 24) | (src[pointer + 5] << 16) | (src[pointer + 6] << 8) | src[pointer + 7]);
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
