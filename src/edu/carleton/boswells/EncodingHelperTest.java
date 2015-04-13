package edu.carleton.boswells;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by stonemanm and boswells for tests.
 */
public class EncodingHelperTest {

    @Test
    public void testReadFromString() throws Exception {
        String input = "F";
        EncodingHelper T1 = new EncodingHelper();
        EncodingHelperChar[] t1 = T1.readFromString(input);
        assertEquals("Incorrect read.",t1[0].getCodePoint(), 0x0046);
    }
    @Test
     public void testReadFromUTF8() throws Exception {
        String utf8Bytes = "\\xC9\\xA4\\xCE\\x8E\\xCF\\xA6\\x4D";
        EncodingHelper T2 = new EncodingHelper();
        EncodingHelperChar[] t2 = T2.readFromUTF8(utf8Bytes);
        assertEquals("Incorrect read", t2[0].toUtf8String(), "\\xC9\\xA4");
    }

    @Test
    public void testReadFromUTF8JustX() throws Exception {
        String utf8Bytes = "xC9xA4xCEx8ExCFxA6x4D";
        EncodingHelper T2 = new EncodingHelper();
        EncodingHelperChar[] t2 = T2.readFromUTF8(utf8Bytes);
        assertEquals("Incorrect read", t2[0].toUtf8String(), "\\xC9\\xA4");
    }

    @Test
    public void testReadFromUTF8NoStart() throws Exception {
        String utf8Bytes = "C9A4CE8ECFA64D";
        EncodingHelper T2 = new EncodingHelper();
        EncodingHelperChar[] t2 = T2.readFromUTF8(utf8Bytes);
        assertEquals("Incorrect read", t2[0].toUtf8String(), "\\xC9\\xA4");
    }

    @Test
    public void testReadFromCodepoints() throws Exception {
        String[] codePointArray = {"0044", "0065", "0074","004D"};
        EncodingHelper T2 = new EncodingHelper();
        EncodingHelperChar[] t2 = T2.readFromCodepoints(codePointArray);
        assertEquals("Incorrect read.", t2[0].toCodePointString(), "U+0044");
        assertEquals("Incorrect read.", t2[1].toCodePointString(), "U+0065");
        assertEquals("Incorrect read.", t2[2].toCodePointString(), "U+0074");
        assertEquals("Incorrect read.", t2[3].toCodePointString(), "U+004D");
    }

    @Test
    public void testReadFromCodepointsSlashU() throws Exception {
        String[] codePointArray = {"\\u0044", "\\u0065", "\\u0074","\\u004D"};
        EncodingHelper T2 = new EncodingHelper();
        EncodingHelperChar[] t2 = T2.readFromCodepoints(codePointArray);
        assertEquals("Incorrect read.", t2[0].toCodePointString(), "U+0044");
        assertEquals("Incorrect read.", t2[1].toCodePointString(), "U+0065");
        assertEquals("Incorrect read.", t2[2].toCodePointString(), "U+0074");
        assertEquals("Incorrect read.", t2[3].toCodePointString(), "U+004D");
    }

    @Test
    public void testReadFromCodepointsJustU() throws Exception {
        String[] codePointArray = {"u0044", "u0065", "u0074","u004D"};
        EncodingHelper T2 = new EncodingHelper();
        EncodingHelperChar[] t2 = T2.readFromCodepoints(codePointArray);
        assertEquals("Incorrect read.", t2[0].toCodePointString(), "U+0044");
        assertEquals("Incorrect read.", t2[1].toCodePointString(), "U+0065");
        assertEquals("Incorrect read.", t2[2].toCodePointString(), "U+0074");
        assertEquals("Incorrect read.", t2[3].toCodePointString(), "U+004D");
    }

    @Test
    public void testReadFromCodepointsUPlus() throws Exception {
        String[] codePointArray = {"U+0044", "U+0065", "U+0074","U+004D"};
        EncodingHelper T2 = new EncodingHelper();
        EncodingHelperChar[] t2 = T2.readFromCodepoints(codePointArray);
        assertEquals("Incorrect read.", t2[0].toCodePointString(), "U+0044");
        assertEquals("Incorrect read.", t2[1].toCodePointString(), "U+0065");
        assertEquals("Incorrect read.", t2[2].toCodePointString(), "U+0074");
        assertEquals("Incorrect read.", t2[3].toCodePointString(), "U+004D");
    }


    @Test
    public void testWriteToString() throws Exception {
        String[] codePointArray = {"0044", "0065", "0074","004D"};
        EncodingHelper T2 = new EncodingHelper();
        EncodingHelperChar[] t1 = T2.readFromCodepoints(codePointArray);
        String t2 = T2.writeToString(t1);
        assertEquals("Incorrect read.", t2, "DetM");
    }

    @Test
    public void testWriteToUTF8() throws Exception {
        String[] codePointArray = {"0264", "038E", "03E6","004D"};
        EncodingHelper T2 = new EncodingHelper();
        EncodingHelperChar[] t1 = T2.readFromCodepoints(codePointArray);
        String t2 = T2.writeToUTF8(t1);
        assertEquals("Incorrect read.", t2, "\\xC9\\xA4\\xCE\\x8E\\xCF\\xA6\\x4D");


    }

    @Test
    public void testWriteToCodepoints() throws Exception {
        String[] codePointArray = {"0044", "0065", "0074","004D"};
        EncodingHelper T2 = new EncodingHelper();
        EncodingHelperChar[] t1 = T2.readFromCodepoints(codePointArray);
        String t2 = T2.writeToCodepoints(t1);
        assertEquals("Incorrect read.",t2,"U+0044 U+0065 U+0074 U+004D");
    }
}