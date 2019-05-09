package com.brad.latchConnect.serialization;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.brad.latchConnect.serialization.SerializationWriter.writeBytes;
import static com.brad.latchConnect.serialization.SerializationReader.*;

public class LCDatabase {

    private static final byte[] HEADER = "LCDB".getBytes();
    private static final byte CONTAINER_TYPE = ContainerType.DATABASE.getValue();
    private short nameLength;
    private byte[] name;
    private int size = HEADER.length + Type.BYTE.getSize() + Type.SHORT.getSize() +
                        Type.INTEGER.getSize() + Type.SHORT.getSize();
    private short objectCount;
    private List<LCObject> objects = new ArrayList<>();

    private LCDatabase() {}

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

    public String getName() {
        return new String(name, 0, nameLength);
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
        pointer = writeBytes(dest, pointer, HEADER);
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

    public static LCDatabase deserialize(byte[] data) {
        int pointer = 0;

        String header = readString(data, pointer, HEADER.length);
        assert(Arrays.equals(header.getBytes(), HEADER));
        pointer += HEADER.length;

        byte containerType = readByte(data, pointer);
        assert(containerType == CONTAINER_TYPE);
        pointer++;

        LCDatabase result = new LCDatabase();

        result.nameLength = readShort(data, pointer);
        pointer += Type.SHORT.getSize();
        result.name = readString(data, pointer, result.nameLength).getBytes();
        pointer += result.nameLength;

        result.size = readInt(data, pointer);
        pointer += Type.INTEGER.getSize();

        result.objectCount = readShort(data, pointer);
        pointer += Type.SHORT.getSize();


        return result;
    }

    public static LCDatabase deserializeFromFile(String path) {
        byte[] buffer = null;
        try {
            BufferedInputStream stream = new BufferedInputStream(new FileInputStream(path));
            buffer = new byte[stream.available()];
            stream.read(buffer);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return deserialize(buffer);
    }

}
