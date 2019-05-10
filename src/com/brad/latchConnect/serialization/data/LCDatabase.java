package com.brad.latchConnect.serialization.data;

import com.brad.latchConnect.serialization.type.ContainerType;
import com.brad.latchConnect.serialization.type.Type;

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

public class LCDatabase extends LCData {

    private static final byte[] HEADER = "LCDB".getBytes();
    private static final short VERSION = 0x0100;
    private static final byte CONTAINER_TYPE = ContainerType.DATABASE.getValue();

    private short objectCount;
    public List<LCObject> objects = new ArrayList<>();

    private LCDatabase() {}

    public LCDatabase(String name) {
        setName(name);
        size += HEADER.length + Type.SHORT.getSize() + Type.BYTE.getSize() + Type.SHORT.getSize();
    }

    public void addObject(LCObject object) {
        objects.add(object);
        size += object.getSize();
        objectCount = (short) objects.size();
    }

    public LCObject findObject(String name) {
        for (LCObject object : objects) {
            if (object.getName().equals(name)) {
                return object;
            }
        }
        return null;
    }

    public int getSize() {
        return size;
    }

    @SuppressWarnings("Duplicates")
    public int setBytes(byte[] dest, int pointer) {
        pointer = writeBytes(dest, pointer, HEADER);
        pointer = writeBytes(dest, pointer, VERSION);
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

    /**
     * Deserializes the data in the given byte stream
     * based on the LCDB format.
     * @param data  Byte stream
     * @return      A database object filled with properly
     *              deserialized fields and objects.
     */
    @SuppressWarnings("Duplicates")
    public static LCDatabase Deserialize(byte[] data) {
        int pointer = 0;

        String header = readString(data, pointer, HEADER.length);
        assert(Arrays.equals(header.getBytes(), HEADER));
        pointer += HEADER.length;

        short version = readShort(data, pointer);
        if (version != VERSION) {
            System.err.println("Invalid LCDB version!");
            return null;
        }
        pointer += Type.SHORT.getSize();

        byte containerType = readByte(data, pointer);
        assert(containerType == CONTAINER_TYPE);
        pointer += Type.BYTE.getSize();

        LCDatabase result = new LCDatabase();

        result.nameLength = readShort(data, pointer);
        pointer += Type.SHORT.getSize();

        result.name = readString(data, pointer, result.nameLength).getBytes();
        pointer += result.nameLength;

        result.size = readInt(data, pointer);
        pointer += Type.INTEGER.getSize();

        result.objectCount = readShort(data, pointer);
        pointer += Type.SHORT.getSize();

        for (int i = 0; i < result.objectCount; i++) {
            LCObject object = LCObject.Deserialize(data, pointer);
            result.objects.add(object);
            pointer += object.getSize();
        }

        return result;
    }

    /**
     * Deserializes the data in the given byte stream
     * (by passing a path to file that contains said
     * byte stream) based on the LCDB format.
     * @param path
     * @return
     */
    public static LCDatabase DeserializeFromFile(String path) {
        byte[] buffer = null;
        try {
            BufferedInputStream stream = new BufferedInputStream(new FileInputStream(path));
            buffer = new byte[stream.available()];
            stream.read(buffer);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Deserialize(buffer);
    }

    /**
     * Serializes all data in the LCDatabase object.
     * @return  Serialized byte stream
     */
    public byte[] serialize() {
        byte[] data = new byte[getSize()];
        setBytes(data, 0);
        return data;
    }

    /**
     * Serializes all data in the LCDatabase object
     * and writes the byte stream to the specified file.
     * @param path  File that the serialized byte stream
     *              is sent to
     */
    public void serializeToFile(String path) {
        byte[] data = serialize();
        try {
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(path));
            stream.write(data);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
