package com.brad.latchConnect.serialization;

import static com.brad.latchConnect.serialization.SerializationWriter.writeBytes;

@SuppressWarnings("Duplicates")
public class LCString {

    private static final byte CONTAINER_TYPE = ContainerType.STRING.getValue();
    private short nameLength;
    private byte[] name;
    private int size = Type.BYTE.getSize() + Type.SHORT.getSize() + Type.INTEGER.getSize() +
                        Type.INTEGER.getSize();
    private int count;
    private char[] characters;

    private LCString() {}

    @SuppressWarnings("Duplicates")
    private void setName(String name) {
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
    int setBytes(byte[] dest, int pointer) {
        pointer = writeBytes(dest, pointer, CONTAINER_TYPE);
        pointer = writeBytes(dest, pointer, nameLength);
        pointer = writeBytes(dest, pointer, name);
        pointer = writeBytes(dest, pointer, size);
        pointer = writeBytes(dest, pointer, count);
        pointer = writeBytes(dest, pointer, characters);
        return pointer;
    }

    int getSize() {
        return size;
    }

    private int getDataSize() {
        return characters.length * Type.getSize(Type.CHAR.getValue());
    }

    public static LCString Create(String name, String data) {
        LCString string = new LCString();
        string.setName(name);
        string.count = data.length();
        string.characters = data.toCharArray();
        string.updateSize();
        return string;
    }

}
