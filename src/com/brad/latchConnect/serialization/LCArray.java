package com.brad.latchConnect.serialization;


import static com.brad.latchConnect.serialization.SerializationWriter.writeBytes;

public class LCArray {

    private static final byte CONTAINER_TYPE = ContainerType.ARRAY.getValue();
    private short nameLength;
    private byte[] name;
    private int size = Type.BYTE.getSize() + Type.SHORT.getSize() +
                        Type.INTEGER.getSize()  + Type.INTEGER.getSize() + Type.BYTE.getSize();
    private byte type;
    private int count;

    private byte[] data;
    private short[] shortData;
    private char[] charData;
    private int[] intData;
    private long[] longData;
    private float[] floatData;
    private double[] doubleData;
    private boolean[] booleanData;

    private LCArray() {}

    @SuppressWarnings("Duplicates")
    public void setName(String name) {
        assert(name.length() < Short.MAX_VALUE);

        if (this.name != null) {
            size -= this.name.length;
        }

        nameLength = (short) name.length();
        this.name = name.getBytes();
        size += nameLength;
    }

    private void updateSize() {
        size += getDataSize();
    }

    @SuppressWarnings("Duplicates")
    public int setBytes(byte[] dest, int pointer) {
        pointer = writeBytes(dest, pointer, CONTAINER_TYPE);
        pointer = writeBytes(dest, pointer, nameLength);
        pointer = writeBytes(dest, pointer, name);
        pointer = writeBytes(dest, pointer, size);
        pointer = writeBytes(dest, pointer, type);
        pointer = writeBytes(dest, pointer, count);

        switch (type) {
            case 1:  // byte
                pointer = writeBytes(dest, pointer, data);
                break;
            case 2:  // short
                pointer = writeBytes(dest, pointer, shortData);
                break;
            case 3:  // char
                pointer = writeBytes(dest, pointer, charData);
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
        return pointer;
    }

    public int getSize() {
        return size;
    }

    private int getDataSize() {
        switch (type) {
            case 1: return data.length * Type.getSize(Type.BYTE.getValue());  // byte
            case 2: return shortData.length * Type.getSize(Type.SHORT.getValue());  // short
            case 3: return charData.length * Type.getSize(Type.CHAR.getValue());  // char
            case 4: return intData.length * Type.getSize(Type.INTEGER.getValue());  // integer
            case 5: return longData.length * Type.getSize(Type.LONG.getValue());  // long
            case 6: return floatData.length * Type.getSize(Type.FLOAT.getValue());  // float
            case 7: return doubleData.length * Type.getSize(Type.DOUBLE.getValue());  // double
            case 8: return booleanData.length * Type.getSize(Type.BOOLEAN.getValue());  // boolean
        }
        return 0;
    }

    public static LCArray Byte(String name, byte[] data) {
        LCArray array = new LCArray();
        array.setName(name);
        array.type = Type.BYTE.getValue();
        array.count = data.length;
        array.data = data;
        array.updateSize();
        return array;
    }

    public static LCArray Short(String name, short[] data) {
        LCArray array = new LCArray();
        array.setName(name);
        array.type = Type.SHORT.getValue();
        array.count = data.length;
        array.shortData = data;
        array.updateSize();
        return array;
    }

    public static LCArray Char(String name, char[] data) {
        LCArray array = new LCArray();
        array.setName(name);
        array.type = Type.CHAR.getValue();
        array.count = data.length;
        array.charData = data;
        array.updateSize();
        return array;
    }

    public static LCArray Integer(String name, int[] data) {
        LCArray array = new LCArray();
        array.setName(name);
        array.type = Type.INTEGER.getValue();
        array.count = data.length;
        array.intData = data;
        array.updateSize();
        return array;
    }

    public static LCArray Long(String name, long[] data) {
        LCArray array = new LCArray();
        array.setName(name);
        array.type = Type.LONG.getValue();
        array.count = data.length;
        array.longData = data;
        array.updateSize();
        return array;
    }

    public static LCArray Float(String name, float[] data) {
        LCArray array = new LCArray();
        array.setName(name);
        array.type = Type.FLOAT.getValue();
        array.count = data.length;
        array.floatData = data;
        array.updateSize();
        return array;
    }

    public static LCArray Double(String name, double[] data) {
        LCArray array = new LCArray();
        array.setName(name);
        array.type = Type.DOUBLE.getValue();
        array.count = data.length;
        array.doubleData = data;
        array.updateSize();
        return array;
    }

    public static LCArray Boolean(String name, boolean[] data) {
        LCArray array = new LCArray();
        array.setName(name);
        array.type = Type.BOOLEAN.getValue();
        array.count = data.length;
        array.booleanData = data;
        array.updateSize();
        return array;
    }

}
