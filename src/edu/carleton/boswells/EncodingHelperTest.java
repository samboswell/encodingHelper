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

    }

    @Test
    public void testReadFromCodepoints() throws Exception {
        String[] codePointArray = {"0044", "0065", "0074","004D"};
        EncodingHelper T2 = new EncodingHelper();
        EncodingHelperChar[] t2 = T2.readFromCodepoints(codePointArray);
        assertEquals("Incorrect read.", t2[0].toCodePointString(), "U+0044");
    }



    @Test
    public void testWriteToString() throws Exception {

    }

    @Test
    public void testWriteToUTF8() throws Exception {

    }

    @Test
    public void testWriteToCodepoints() throws Exception {

    }
}