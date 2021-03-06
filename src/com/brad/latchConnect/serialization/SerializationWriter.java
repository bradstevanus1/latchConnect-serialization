package com.brad.latchConnect.serialization;

import com.brad.latchConnect.serialization.type.Type;

/**
 * Contains static methods for writing common data
 * types to a byte array.
 */
public final class SerializationWriter {

    /**
     * Do not let anyone instantiate this class.
     */
    private SerializationWriter() {}

    public static int writeBytes(byte[] dest, int pointer, byte[] src) {
        assert(dest.length > pointer + src.length);
        for (byte value : src) {
            dest[pointer++] = value;
        }
        return pointer;
    }

    public static int writeBytes(byte[] dest, int pointer, short[] src) {
        assert(dest.length > pointer + src.length);
        for (short value : src) {
            pointer = writeBytes(dest, pointer, value);
        }
        return pointer;
    }

    public static int writeBytes(byte[] dest, int pointer, char[] src) {
        assert(dest.length > pointer + src.length);
        for (char value : src) {
            pointer = writeBytes(dest, pointer, value);
        }
        return pointer;
    }


    public static int writeBytes(byte[] dest, int pointer, int[] src) {
        assert(dest.length > pointer + src.length);
        for (int value : src) {
            pointer = writeBytes(dest, pointer, value);
        }
        return pointer;
    }

    public static int writeBytes(byte[] dest, int pointer, long[] src) {
        assert(dest.length > pointer + src.length);
        for (long value : src) {
            pointer = writeBytes(dest, pointer, value);
        }
        return pointer;
    }

    public static int writeBytes(byte[] dest, int pointer, float[] src) {
        assert(dest.length > pointer + src.length);
        for (float value : src) {
            pointer = writeBytes(dest, pointer, value);
        }
        return pointer;
    }

    public static int writeBytes(byte[] dest, int pointer, double[] src) {
        assert(dest.length > pointer + src.length);
        for (double value : src) {
            pointer = writeBytes(dest, pointer, value);
        }
        return pointer;
    }

    public static int writeBytes(byte[] dest, int pointer, boolean[] src) {
        assert(dest.length > pointer + src.length);
        for (boolean value : src) {
            pointer = writeBytes(dest, pointer, value);
        }
        return pointer;
    }


    /**
     * Writes a byte to an index in a byte array depending directly
     * with the value given. Useful when the byte array represents
     * a file or other such data structure.
     * @param dest      A byte array (usually associated with a data
     *                  structure, such as a file)
     * @param pointer   A pointer to which byte in the array should be
     *                  written to
     * @param value     The byte value that you want to insert at that
     *                  index
     * @return          The pointer incremented by 1
     */
    public static int writeBytes(byte[] dest, int pointer, byte value) {
        assert(dest.length > pointer + Type.BYTE.getSize());
        dest[pointer++] = value;
        return pointer;
    }

    public static int writeBytes(byte[] dest, int pointer, short value) {
        assert(dest.length > pointer + Type.SHORT.getSize());
        dest[pointer++] = (byte) ((value >> 8) & 0xff);
        dest[pointer++] = (byte) (value & 0xff);
        return pointer;
    }

    public static int writeBytes(byte[] dest, int pointer, char value) {
        assert(dest.length > pointer + Type.CHAR.getSize());
        dest[pointer++] = (byte) ((value >> 8) & 0xff);
        dest[pointer++] = (byte) (value & 0xff);
        return pointer;
    }

    @SuppressWarnings("Duplicates")
    public static int writeBytes(byte[] dest, int pointer, int value) {
        assert(dest.length > pointer + Type.INTEGER.getSize());
        dest[pointer++] = (byte) ((value >> 24) & 0xff);
        dest[pointer++] = (byte) ((value >> 16) & 0xff);
        dest[pointer++] = (byte) ((value >> 8) & 0xff);
        dest[pointer++] = (byte) (value & 0xff);
        return pointer;
    }

    @SuppressWarnings("Duplicates")
    public static int writeBytes(byte[] dest, int pointer, long value) {
        assert(dest.length > pointer + Type.LONG.getSize());
        dest[pointer++] = (byte) ((value >> 56) & 0xff);
        dest[pointer++] = (byte) ((value >> 48) & 0xff);
        dest[pointer++] = (byte) ((value >> 40) & 0xff);
        dest[pointer++] = (byte) ((value >> 32) & 0xff);
        dest[pointer++] = (byte) ((value >> 24) & 0xff);
        dest[pointer++] = (byte) ((value >> 16) & 0xff);
        dest[pointer++] = (byte) ((value >> 8) & 0xff);
        dest[pointer++] = (byte) (value & 0xff);
        return pointer;
    }

    public static int writeBytes(byte[] dest, int pointer, float value) {
        assert(dest.length > pointer + Type.FLOAT.getSize());
        int data = Float.floatToIntBits(value);
        return writeBytes(dest, pointer, data);
    }

    public static int writeBytes(byte[] dest, int pointer, double value) {
        assert(dest.length > pointer + Type.DOUBLE.getSize());
        long data = Double.doubleToLongBits(value);
        return writeBytes(dest, pointer, data);
    }

    public static int writeBytes(byte[] dest, int pointer, boolean value) {
        assert(dest.length > pointer + Type.BOOLEAN.getSize());
        dest[pointer++] = (byte) (value ? 1 : 0);
        return pointer;
    }

    public static int writeBytes(byte[] dest, int pointer, String string) {
        assert(dest.length > pointer + string.length());
        pointer = writeBytes(dest, pointer, (short) string.length());
        return writeBytes(dest, pointer, string.getBytes());
    }

}
