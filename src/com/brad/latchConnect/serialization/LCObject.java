package com.brad.latchConnect.serialization;

import java.util.ArrayList;
import java.util.List;

import static com.brad.latchConnect.serialization.SerializationWriter.writeBytes;

public class LCObject {

    private static final byte CONTAINER_TYPE = ContainerType.OBJECT.getValue();
    private short nameLength;
    private byte[] name;
    private int size = Type.BYTE.getSize() + Type.SHORT.getSize() + Type.INTEGER.getSize() +
                        Type.SHORT.getSize() + Type.SHORT.getSize() + Type.SHORT.getSize();

    private short fieldCount;
    private List<LCField> fields = new ArrayList<>();

    private short stringCount;
    private List<LCString> strings = new ArrayList<>();

    private short arrayCount;
    private List<LCArray> arrays = new ArrayList<>();


    public LCObject(String name) {
        setName(name);
    }

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

    int getSize() {
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
}
