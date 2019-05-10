package com.brad.latchConnect.serialization.type;

public enum ContainerType {
    
    UNKNOWN ((byte) 0),
    FIELD   ((byte) 1),
    ARRAY   ((byte) 2),
    STRING  ((byte) 3),
    OBJECT  ((byte) 4),
    DATABASE((byte) 5);

    private byte value;

    public byte getValue() {
        return this.value;
    }

    ContainerType(byte value) {
        this.value = value;
    }
}
