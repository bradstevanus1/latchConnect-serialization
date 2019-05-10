package com.brad.latchConnect.serialization.data;

import com.brad.latchConnect.serialization.type.ContainerType;
import com.brad.latchConnect.serialization.type.Type;

import java.util.ArrayList;
import java.util.List;

import static com.brad.latchConnect.serialization.SerializationReader.*;
import static com.brad.latchConnect.serialization.SerializationWriter.writeBytes;

public class LCObject extends LCData {

    private static final byte CONTAINER_TYPE = ContainerType.OBJECT.getValue();

    private short fieldCount;
    public List<LCField> fields = new ArrayList<>();

    private short stringCount;
    public List<LCString> strings = new ArrayList<>();

    private short arrayCount;
    public List<LCArray> arrays = new ArrayList<>();

    private LCObject() {}

    public LCObject(String name) {
        setName(name);
        size += Type.BYTE.getSize() + Type.SHORT.getSize() + Type.SHORT.getSize() + Type.SHORT.getSize();
    }

    public void addField(LCField field) {
        fields.add(field);
        size += field.getSize();
        fieldCount = (short) fields.size();
    }

    public void addString(LCString string) {
        strings.add(string);
        size += string.getSize();
        stringCount = (short) strings.size();
    }

    public void addArray(LCArray array) {
        arrays.add(array);
        size += array.getSize();
        arrayCount = (short) arrays.size();
    }

    public LCField findField(String name) {
        for (LCField field : fields) {
            if (field.getName().equals(name)) {
                return field;
            }
        }
        return null;
    }

    public LCString findString(String name) {
        for (LCString string : strings) {
            if (string.getName().equals(name)) {
                return string;
            }
        }
        return null;
    }

    public LCArray findArray(String name) {
        for (LCArray array : arrays) {
            if (array.getName().equals(name)) {
                return array;
            }
        }
        return null;
    }

    public int getSize() {
        return size;
    }

    @SuppressWarnings("Duplicates")
    int setBytes(byte[] dest, int pointer) {
        pointer = writeBytes(dest, pointer, CONTAINER_TYPE);
        pointer = writeBytes(dest, pointer, nameLength);
        pointer = writeBytes(dest, pointer, name);
        pointer = writeBytes(dest, pointer, size);

        pointer = writeBytes(dest, pointer, fieldCount);
        for (LCField field : fields) {
            pointer = field.setBytes(dest, pointer);
        }

        pointer = writeBytes(dest, pointer, stringCount);
        for (LCString string : strings) {
            pointer = string.setBytes(dest, pointer);
        }

        pointer = writeBytes(dest, pointer, arrayCount);
        for (LCArray array : arrays) {
            pointer = array.setBytes(dest, pointer);
        }
        return pointer;
    }

    /**
     * Deserializes an LCObject type, where the pointer must
     * point to the first byte in the object, being the container type.
     * @param data      A serialized byte stream that contains objects
     * @param pointer   A pointer to the first byte in a LCObject
     * @return          An LCObject populated with usable data that was
     *                  read from the byte stream
     */
    @SuppressWarnings("Duplicates")
    public static LCObject Deserialize(byte[] data, int pointer) {
        byte containerType = readByte(data, pointer);
        assert(containerType == CONTAINER_TYPE);
        pointer += Type.BYTE.getSize();

        LCObject result = new LCObject();

        result.nameLength = readShort(data, pointer);
        pointer += Type.SHORT.getSize();

        result.name = readString(data, pointer, result.nameLength).getBytes();
        pointer += result.nameLength;

        result.size = readInt(data, pointer);
        pointer += Type.INTEGER.getSize();

        // Early-out: pointer += result.size - sizeOffset - result.nameLength;

        result.fieldCount = readShort(data, pointer);
        pointer += Type.SHORT.getSize();

        for (int i = 0; i < result.fieldCount; i++) {
            LCField field = LCField.Deserialize(data, pointer);
            result.fields.add(field);
            pointer += field.getSize();
        }

        result.stringCount = readShort(data, pointer);
        pointer += Type.SHORT.getSize();

        for (int i = 0; i < result.stringCount; i++) {
            LCString string = LCString.Deserialize(data, pointer);
            result.strings.add(string);
            pointer += string.getSize();
        }

        result.arrayCount = readShort(data, pointer);
        pointer += Type.SHORT.getSize();

        for (int i = 0; i < result.arrayCount; i++) {
            LCArray array = LCArray.Deserialize(data, pointer);
            result.arrays.add(array);
            pointer += array.getSize();
        }

        return result;
    }
}
