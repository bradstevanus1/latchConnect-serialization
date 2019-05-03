package test;


import com.brad.latchConnect.serialization.Field;

public class Main {

    public static void printHex(int value) {
        System.out.printf("0x%x ", value);
    }

    public static void printBin(byte value) {
        System.out.println(String.format("%8s", Integer.toBinaryString(value & 0xFF)).replace(' ', '0'));
    }

    public static void colorChannels(int color) {

        int r = (color & 0xFF0000) >> 16;
        int g = (color & 0xFF00) >> 8;
        int b = color & 0xFF;

        r -= 50;
        g += 28;
        b -= 50;

        int result = r << 16 | g << 8 | b;

        printHex(result);
        printHex(0xdf0000 & 0xcc0000);
    }

    public static void printBytes(byte[] bytes) {
        for (byte data : bytes) {
            printHex(data);
        }
        System.out.println();
    }

    public static void main(String[] args) {

        Field field = Field.Long("Test", 10);

        byte[] data = new byte[100];
        field.getBytes(data, 0);
        printBytes(data);

        // 0x1 0x0 0x4 0x54 0x65 0x73 0x74 0x0 0x0 0x0 0x0 0x8

    }

}
