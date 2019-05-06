package com.brad.latchConnect.serialization;

import static com.brad.latchConnect.serialization.SerializationWriter.writeBytes;

public class Array {

    public static final byte CONTAINER_TYPE = ContainerType.ARRAY.getValue();
    public short nameLength;
    public byte[] name;
    public byte type;
    public int count;
    public byte[] data;

    private short[] shortData;
    private char[] charData;
    private int[] intData;
    private long[] longData;
    private float[] floatData;
    private double[] doubleData;
    private boolean[] booleanData;

    public void setName(String name) {
        assert(name.length() < Short.MAX_VALUE);
        nameLength = (short) name.length();
        this.name = name.getBytes();
    }

    @SuppressWarnings("Duplicates")
    public int getBytes(byte[] dest, int pointer) {
        pointer = writeBytes(dest, pointer, CONTAINER_TYPE);
        pointer = writeBytes(dest, pointer, nameLength);
        pointer = writeBytes(dest, pointer, name);
        pointer = writeBytes(dest, pointer, type);

        switch (type) {
            case 1:  // byte
                pointer = writeBytes(dest, pointer, data);
                break;
            case 2:  // short
                pointer = writeBytes(dest, pointer, shortData);
                break;
            case 3:  // char
                pointer = writeBytes(dest, pointer, charData;
                break;
            case 4:  // integer
                pointer = writeBytes(dest, pointer, intData);
                break;
            case 5:  // long
                pointer = writeBytes(dest, pointer, longData);
                break;
            case 6:  // float
                pointer = writeBytes(dest, pointer, floatData);
                break;
            case 7:  // double
                pointer = writeBytes(dest, pointer, doubleData);
                break;
            case 8:  // boolean
                pointer = writeBytes(dest, pointer, booleanData);
                break;
        }
        pointer = writeBytes(dest, pointer, data);
        return pointer;
    }

    public static Array Byte(String name, byte[] data) {
        Array array = new Array();
        array.setName(name);
        array.type = Type.BYTE.getValue();
        array.count = data.length;
        array.data = data;
        return array;
    }

    public static Array Short(String name, short[] data) {
        Array array = new Array();
        array.setName(name);
        array.type = Type.SHORT.getValue();
        array.count = data.length;
        array.shortData = data;
        return array;
    }

    public static Array Char(String name, char[] data) {
        Array array = new Array();
        array.setName(name);
        array.type = Type.CHAR.getValue();
        array.count = data.length;
        array.charData = data;
        return array;
    }

    public static Array Integer(String name, int[] data) {
        Array array = new Array();
        array.setName(name);
        array.type = Type.INTEGER.getValue();
        array.count = data.length;
        array.intData = data;
        return array;
    }

    public static Array Long(String name, long[] data) {
        Array array = new Array();
        array.setName(name);
        array.type = Type.LONG.getValue();
        array.count = data.length;
        array.longData = data;
        return array;
    }

    public static Array Float(String name, float[] data) {
        Array array = new Array();
        array.setName(name);
        array.type = Type.FLOAT.getValue();
        array.count = data.length;
        array.floatData = data;
        return array;
    }

    public static Array Double(String name, double[] data) {
        Array array = new Array();
        array.setName(name);
        array.type = Type.DOUBLE.getValue();
        array.count = data.length;
        array.doubleData = data;
        return array;
    }

    public static Array Boolean(String name, boolean[] data) {
        Array array = new Array();
        array.setName(name);
        array.type = Type.BOOLEAN.getValue();
        array.count = data.length;
        array.booleanData = data;
        return array;
    }

}