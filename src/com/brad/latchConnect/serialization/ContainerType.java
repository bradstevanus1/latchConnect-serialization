package com.brad.latchConnect.serialization;

public enum ContainerType {
    
    UNKNOWN ((byte) 0),
    FIELD   ((byte) 1),
    ARRAY   ((byte) 2),
    OBJECT  ((byte) 3);

    private byte value;

    public byte getValue() {
        return this.value;
    }

    ContainerType(byte value) {
        this.value = value;
    }
}
