package com.brad.latchConnect.serialization.data;

import com.brad.latchConnect.serialization.type.ContainerType;
import com.brad.latchConnect.serialization.type.Type;

import static com.brad.latchConnect.serialization.SerializationReader.*;
import static com.brad.latchConnect.serialization.SerializationWriter.writeBytes;

@SuppressWarnings("Duplicates")
public class LCString extends LCData {

    private static final byte CONTAINER_TYPE = ContainerType.STRING.getValue();
    private int count;
    private char[] characters;

    private LCString() {
        size += Type.BYTE.getSize() + Type.INTEGER.getSize();
    }

    public String getString() {
        return new String(characters);
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

    public int getSize() {
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

    @SuppressWarnings("Duplicates")
    public static LCString Deserialize(byte[] data, int pointer) {
        byte containerType = readByte(data, pointer);
        assert(containerType == CONTAINER_TYPE);
        pointer += Type.BYTE.getSize();

        LCString result = new LCString();

        result.nameLength = readShort(data, pointer);
        pointer += Type.SHORT.getSize();

        result.name = readString(data, pointer, result.nameLength).getBytes();
        pointer += result.nameLength;

        result.size = readInt(data, pointer);
        pointer += Type.INTEGER.getSize();

        result.count = readInt(data, pointer);
        pointer += Type.INTEGER.getSize();

        result.characters = new char[result.count];
        readChars(data, pointer, result.characters);
        pointer += result.count * Type.CHAR.getSize();

        return result;
    }

}
