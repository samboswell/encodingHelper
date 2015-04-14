package edu.carleton.boswells;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by stonemanm and boswells for tests.
 */
public class EncodingHelperTest {

    /**
     * Tests reading from an input string.
     * @throws Exception if the string's codepoint doesn't match
     * the intended codepoint.
     */

    @Test
    public void testReadFromString() throws Exception {
        String input = "F";
        EncodingHelper T1 = new EncodingHelper();
        EncodingHelperChar[] t1 = T1.readFromString(input);
        assertEquals("Incorrect read.",t1[0].getCodePoint(), 0x0046);
    }

    /**
     *Tests reading from an input array of bytes.
     * @throws Exception if the UTF8 array generated does not correspond to the
     * array that is input.
     */

    @Test
     public void testReadFromUTF8() throws Exception {
        String utf8Bytes = "\\xC9\\xA4\\xCE\\x8E\\xCF\\xA6\\x4D";
        EncodingHelper T2 = new EncodingHelper();
        EncodingHelperChar[] t2 = T2.readFromUTF8(utf8Bytes);
        assertEquals("Incorrect read", t2[0].toUtf8String(), "\\xC9\\xA4");
    }

    /**
     *Tests reading from UTF8 with no backslashes.
     * @throws Exception if the UTF8 array generated does not correspond to the
     * array that is input.
     */

    @Test
    public void testReadFromUTF8JustX() throws Exception {
        String utf8Bytes = "xC9xA4xCEx8ExCFxA6x4D";
        EncodingHelper T2 = new EncodingHelper();
        EncodingHelperChar[] t2 = T2.readFromUTF8(utf8Bytes);
        assertEquals("Incorrect read", t2[0].toUtf8String(), "\\xC9\\xA4");
    }

    /**
     * Tests reading from UTF8 with just hex input.
     * @throws Exception if the output does not correspond to the input array.
     */

    @Test
    public void testReadFromUTF8NoStart() throws Exception {
        String utf8Bytes = "C9A4CE8ECFA64D";
        EncodingHelper T2 = new EncodingHelper();
        EncodingHelperChar[] t2 = T2.readFromUTF8(utf8Bytes);
        assertEquals("Incorrect read", t2[0].toUtf8String(), "\\xC9\\xA4");
    }

    /**
     *Tests reading from strings of codepoints that are each their own string.
     * @throws Exception if the output does not correspond to the input.
     */

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

    /**
     *Tests reading from one string of multiple codepoints.
     * @throws Exception if the input is read incorrectly.
     */

    @Test
    public void testReadFromCodepointsOneString() throws Exception {
        String[] codePointArray = {"0044 0065 0074 004D"};
        EncodingHelper T2 = new EncodingHelper();
        EncodingHelperChar[] t2 = T2.readFromCodepoints(codePointArray);
        assertEquals("Incorrect read.", t2[0].toCodePointString(), "U+0044");
        assertEquals("Incorrect read.", t2[1].toCodePointString(), "U+0065");
        assertEquals("Incorrect read.", t2[2].toCodePointString(), "U+0074");
        assertEquals("Incorrect read.", t2[3].toCodePointString(), "U+004D");
    }

    /**
     *Tests reading from codepoints with a slash u prefix.
     * @throws Exception if the codepoints are not read correctly.
     */

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

    /**
     * Tests reading from codepoints with a 'u' prefix.
     * @throws Exception if the codepoints are read incorrectly.
     */

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

    /**
     * Tests reading from codepoints with a 'U+' prefix.
     * @throws Exception if the codepoints are read incorrectly.
     */

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

    /**
     * Tests writing from codepoints to strings.
     * @throws Exception if the codepoints are not written correctly.
     */

    @Test
    public void testWriteToString() throws Exception {
        String[] codePointArray = {"0044", "0065", "0074","004D"};
        EncodingHelper T2 = new EncodingHelper();
        EncodingHelperChar[] t1 = T2.readFromCodepoints(codePointArray);
        String t2 = T2.writeToString(t1);
        assertEquals("Incorrect read.", t2, "DetM");
    }

    /**
     * Tests writing from codepoints to UTF8.
     * @throws Exception if the codepoints are not written correctly.
     */

    @Test
    public void testWriteToUTF8() throws Exception {
        String[] codePointArray = {"0264", "038E", "03E6","004D"};
        EncodingHelper T2 = new EncodingHelper();
        EncodingHelperChar[] t1 = T2.readFromCodepoints(codePointArray);
        String t2 = T2.writeToUTF8(t1);
        assertEquals("Incorrect read.", t2, "\\xC9\\xA4\\xCE\\x8E\\xCF\\xA6\\x4D");


    }

    /**
     * Tests writing from codepoints to codepoints.
     * (Should return codepoints)
     * @throws Exception if it does not return itself.
     */

    @Test
    public void testWriteToCodepoints() throws Exception {
        String[] codePointArray = {"0044", "0065", "0074","004D"};
        EncodingHelper T2 = new EncodingHelper();
        EncodingHelperChar[] t1 = T2.readFromCodepoints(codePointArray);
        String t2 = T2.writeToCodepoints(t1);
        assertEquals("Incorrect read.",t2,"U+0044 U+0065 U+0074 U+004D");
    }
}