package test;


import com.brad.latchConnect.serialization.Array;
import com.brad.latchConnect.serialization.Field;

import java.util.Random;

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
        int[] data = new int[50000];
        for (int i = 0; i < data.length; i++) {
            data[i] = new Random().nextInt();
        }

        Array array = Array.Integer("Test", data);

        byte[] stream = new byte[array.getSize()];
        array.getBytes(stream, 0);
        printBytes(stream);

    }

}
