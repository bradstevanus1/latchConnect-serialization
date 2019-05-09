package test;


import com.brad.latchConnect.serialization.LCArray;
import com.brad.latchConnect.serialization.LCDatabase;
import com.brad.latchConnect.serialization.LCField;
import com.brad.latchConnect.serialization.LCObject;
import com.brad.latchConnect.serialization.LCString;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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

    public static void saveToFile(String path, byte[] data) {
        try {
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(path));
            stream.write(data);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void serializationTest() {
        int[] data = new int[50000];
        for (int i = 0; i < data.length; i++) {
            data[i] = new Random().nextInt();
        }

        LCDatabase database = new LCDatabase("Database");

        LCArray array = LCArray.Integer("RandomNumbers", data);
        LCField field = LCField.Integer("Integer", 8);
        LCField positionx = LCField.Short("xpos", (short) 2);
        LCField positiony = LCField.Short("ypos", (short) 42);

        LCObject object = new LCObject("Entity");
        object.addArray(array);
        object.addArray(LCArray.Char("stringVar", "Hello World!".toCharArray()));
        object.addField(field);
        object.addField(positionx);
        object.addField(positiony);
        object.addString(LCString.Create("exampleString", "Testing our LCString class!"));

        database.addObject(object);
        database.addObject(new LCObject("Brad"));
        database.addObject(new LCObject("Brad1"));
        LCObject c = new LCObject("Brad2");
        c.addField(LCField.Boolean("isTrue", false));
        database.addObject(c);
        database.addObject(new LCObject("Brad3"));

        byte[] stream = new byte[database.getSize()];
        database.setBytes(stream, 0);
        saveToFile("test.lcdb",  stream);
    }

    public static void deserializationTest() {
        LCDatabase database = LCDatabase.DeserializeFromFile("test.lcdb");
        System.out.println("Database: " + database.getName());
        for (int i = 0; i < database.objects.size(); i++) {
            System.out.println("\t" + database.objects.get(i).getName());
        }
    }

    public static void main(String[] args) {
        serializationTest();
        deserializationTest();
    }

}
