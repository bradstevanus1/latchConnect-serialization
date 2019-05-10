package com.brad.latchConnect.serialization.data;

import com.brad.latchConnect.serialization.type.Type;

public abstract class LCData {

    protected short nameLength;
    protected byte[] name;
    protected int size = Type.SHORT.getSize() + Type.INTEGER.getSize();

    public String getName() {
        return new String(name, 0, nameLength);
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

    public abstract int getSize();

}
