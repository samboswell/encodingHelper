package edu.carleton.boswells;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by stonemanm and boswells on 4/12/15.
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