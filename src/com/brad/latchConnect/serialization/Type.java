package com.brad.latchConnect.serialization;

public enum Type {

    UNKNOWN ((byte) 0),
    BYTE    ((byte) 1),
    SHORT   ((byte) 2),
    CHAR    ((byte) 3),
    INT     ((byte) 4),
    LONG    ((byte) 5),
    FLOAT   ((byte) 6),
    DOUBLE  ((byte) 7),
    BOOLEAN ((byte) 8);


    private byte value;

    public byte getValue() {
        return this.value;
    }

    public int getSize() {
        switch (this) {
            case UNKNOWN:   assert(false);
            case BYTE:      return 1;
            case SHORT:     return 2;
            case CHAR:      return 2;
            case INT:       return 4;
            case LONG:      return 8;
            case FLOAT:     return 4;
            case DOUBLE:    return 8;
            case BOOLEAN:   return 1;
        }
        assert(false);
        return 0;
    }

    public static int getSize(Byte type) {
        switch (type) {
            case 0: assert(false);
            case 1: return 1;
            case 2: return 2;
            case 3: return 2;
            case 4: return 4;
            case 5: return 8;
            case 6: return 4;
            case 7: return 8;
            case 8: return 1;
        }
        assert(false);
        return 0;
    }

    Type(byte value) {
        this.value = value;
    }

}
