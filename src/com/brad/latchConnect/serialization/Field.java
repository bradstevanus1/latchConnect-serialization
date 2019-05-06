package com.brad.latchConnect.serialization;

import static com.brad.latchConnect.serialization.SerializationWriter.*;


/**
 * The stream of bytes that are contained within
 * this class are the actual bytes that get
 * serialized for a field of an object.
 */
public class Field {

    public static final byte CONTAINER_TYPE = ContainerType.FIELD.getValue();
    public short nameLength;
    public byte[] name;
    public byte type;
    public byte[] data;

    private Field() {}

    public void setName(String name) {
        assert(name.length() < Short.MAX_VALUE);
        nameLength = (short) name.length();
        this.name = name.getBytes();
    }

    public int getBytes(byte[] dest, int pointer) {
        pointer = writeBytes(dest, pointer, CONTAINER_TYPE);
        pointer = writeBytes(dest, pointer, nameLength);
        pointer = writeBytes(dest, pointer, name);
        pointer = writeBytes(dest, pointer, type);
        pointer = writeBytes(dest, pointer, data);
        return pointer;
    }

    public int getSize() {
        assert(data.length == Type.getSize(type));
        return Type.BYTE.getSize() + Type.SHORT.getSize() + name.length + Type.BYTE.getSize() + data.length;
    }


    public static Field Byte(String name, byte value) {
        Field field = new Field();
        field.setName(name);
        field.type = Type.BYTE.getValue();
        field.data = new byte[Type.BYTE.getSize()];
        writeBytes(field.data, 0, value);
        return field;
    }

    public static Field Short(String name, short value) {
        Field field = new Field();
        field.setName(name);
        field.type = Type.SHORT.getValue();
        field.data = new byte[Type.SHORT.getSize()];
        writeBytes(field.data, 0, value);
        return field;
    }

    public static Field Char(String name, char value) {
        Field field = new Field();
        field.setName(name);
        field.type = Type.CHAR.getValue();
        field.data = new byte[Type.CHAR.getSize()];
        writeBytes(field.data, 0, value);
        return field;
    }

    public static Field Integer(String name, int value) {
        Field field = new Field();
        field.setName(name);
        field.type = Type.INTEGER.getValue();
        field.data = new byte[Type.INTEGER.getSize()];
        writeBytes(field.data, 0, value);
        return field;
    }

    public static Field Long(String name, long value) {
        Field field = new Field();
        field.setName(name);
        field.type = Type.LONG.getValue();
        field.data = new byte[Type.LONG.getSize()];
        writeBytes(field.data, 0, value);
        return field;
    }

    public static Field Float(String name, float value) {
        Field field = new Field();
        field.setName(name);
        field.type = Type.FLOAT.getValue();
        field.data = new byte[Type.FLOAT.getSize()];
        writeBytes(field.data, 0, value);
        return field;
    }

    public static Field Double(String name, double value) {
        Field field = new Field();
        field.setName(name);
        field.type = Type.DOUBLE.getValue();
        field.data = new byte[Type.DOUBLE.getSize()];
        writeBytes(field.data, 0, value);
        return field;
    }

    public static Field Boolean(String name, boolean value) {
        Field field = new Field();
        field.setName(name);
        field.type = Type.BOOLEAN.getValue();
        field.data = new byte[Type.BOOLEAN.getSize()];
        writeBytes(field.data, 0, value);
        return field;
    }

}
