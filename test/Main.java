import com.brad.latchConnect.serialization.data.LCArray;
import com.brad.latchConnect.serialization.data.LCDatabase;
import com.brad.latchConnect.serialization.data.LCField;
import com.brad.latchConnect.serialization.data.LCObject;
import com.brad.latchConnect.serialization.data.LCString;

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

        database.serializeToFile("test.lcdb");

    }

    public static void deserializationTest() {
        LCDatabase database = LCDatabase.DeserializeFromFile("test.lcdb");
        System.out.println("Database: " + database.getName());
        for (LCObject object : database.objects) {
            System.out.println("\t" + object.getName());
            for (LCField field : object.fields) {
                System.out.println("\t\t" + field.getName());
            }
            System.out.println();
            for (LCString string : object.strings) {
                System.out.println("\t\t" + string.getName() + " = " + string.getString());
            }
            System.out.println();
            for (LCArray array : object.arrays) {
                System.out.println("\t\t" + array.getName());
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        //serializationTest();
        //deserializationTest();
        Sandbox sandbox = new Sandbox();
        sandbox.play();
    }

}
