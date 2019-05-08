package com.brad.latchConnect.serialization;

import java.util.ArrayList;
import java.util.List;

import static com.brad.latchConnect.serialization.SerializationWriter.writeBytes;

public class LCDatabase {

    public static final byte CONTAINER_TYPE = ContainerType.DATABASE.getValue();
    public short nameLength;
    public byte[] name;
    private int size = Type.BYTE.getSize() + Type.SHORT.getSize() +
                        Type.INTEGER.getSize() + Type.SHORT.getSize();
    private short objectCount;
    private List<LCObject> objects = new ArrayList<>();

    public LCDatabase(String name) {
        setName(name);
    }

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

    public void addObject(LCObject object) {
        objects.add(object);
        size += object.getSize();
        objectCount = (short) objects.size();
    }

    public int getSize() {
        return size;
    }

    @SuppressWarnings("Duplicates")
    public int setBytes(byte[] dest, int pointer) {
        pointer = writeBytes(dest, pointer, CONTAINER_TYPE);
        pointer = writeBytes(dest, pointer, nameLength);
        pointer = writeBytes(dest, pointer, name);
        pointer = writeBytes(dest, pointer, size);

        pointer = writeBytes(dest, pointer, objectCount);
        for (LCObject object : objects) {
            pointer = object.setBytes(dest, pointer);
        }

        return pointer;
    }

}
