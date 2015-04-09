package edu.carleton.boswells;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 * Created by Sam Boswell and Michael Stoneman, last edited on 4/5/15.
 */
public class EncodingHelperCharTest {
    @Test(expected = IllegalArgumentException.class)
    public void EncodingHelperCharChar() throws Exception {
        EncodingHelperChar T1 = new EncodingHelperChar('?');

    }

    @Test (expected = IllegalArgumentException.class)
    public void EncodingHelperCharShouldReturnInvalid() throws Exception {


    }
    @Test(expected = IllegalArgumentException.class)
    public void EncodingHelperCharInt() throws Exception {
        EncodingHelperChar T1 = new EncodingHelperChar(0xA5);
        assertEquals(T1.getCodePoint(), 0xA5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void EncodingHelperCharInvalidInt() throws Exception {
        EncodingHelperChar T2 = new EncodingHelperChar(0x10FFFF);
        assertEquals("No message","Code point is out of bounds (either too high or too low)", "Code point is out of bounds (either too high or too low)");
    }

    @Test(expected = IllegalArgumentException.class)
    public void EncodingHelperCharNegativeInt() throws Exception {
        EncodingHelperChar T3 = new EncodingHelperChar(-0x80);
        assertEquals("No message","Code point is out of bounds (either too high or too low)", "Code point is out of bounds (either too high or too low)");
    }

    @Test(expected = IllegalArgumentException.class)
    public void EncodingHelperCharInvalidByteTypeShouldReturnInvalid() throws Exception {
        EncodingHelperChar T4 = new EncodingHelperChar(0x10FFFF);

    }

    @Test(expected = IllegalArgumentException.class)
    public void EncodingHelperCharByte() throws Exception {
        EncodingHelperChar T5 = new EncodingHelperChar(0xA5);
        assertEquals("No message", "Byte array doesn't correspond to a valid Unicode character.", "Byte array doesn't correspond to a valid Unicode character.");
    }

    @Test
    /**
     * Tests getting code points for EncodingHelperChar.
     * @exception it gets the wrong code point.
     */
    public void testGetCodePoint() throws Exception {
        EncodingHelperChar test1 = new EncodingHelperChar(0xA5);
        assertEquals(test1.getCodePoint(), 0xA5);
    }

    @Test
    /**
     * Tests setting code points for EncodingHelperChar.
     * @exception the code point is set to a new code point, but
     * returns the original code point.
     */
    public void testSetCodePoint() throws Exception {
        EncodingHelperChar test2 = new EncodingHelperChar(0xA5);
        test2.setCodePoint(0xA6);
        assertEquals(test2.getCodePoint(), 0xA6);
    }

    @Test
    /**
     * Tests converting the code point to UTF-8 bytes for EncodingHelperChar.
     * @exception the code point does not convert correctly to UTF-8 bytes.
     */
    public void testToUtf8Bytes() throws Exception {
        EncodingHelperChar test3 = new EncodingHelperChar(0xA5);
        byte[] utf8Bytes = new byte[]{(byte) 0xc2, (byte) 0xa5};
        assertArrayEquals("Failure - incorrect UTF-8 bytes", utf8Bytes, test3.toUtf8Bytes());
    }
    @Test
    public void testToUtf8BytesLength3() throws Exception {
        EncodingHelperChar test3 = new EncodingHelperChar(0xFBA);
        byte[] utf8Bytes = new byte[]{(byte) 0xe0, (byte)0xbe, (byte)0xba};
        assertArrayEquals("Failure - incorrect UTF-8 bytes", utf8Bytes, test3.toUtf8Bytes());
    }
    /**
     @Test
     public void testToUtf8BytesLength2) throws Exception {
     EncodingHelperChar test3 = new EncodingHelperChar(0x800);
     byte[] utf8Bytes = [11000010 10100100];
     assertArrayEquals("Failure - incorrect UTF-8 bytes", utf8Bytes, test3.toUtf8Bytes());
     }
     @Test
     public void testToUtf8BytesLength4() throws Exception {
     EncodingHelperChar test3 = new EncodingHelperChar(0x10000);
     byte[] utf8Bytes = [11000010 10100100];
     assertArrayEquals("Failure - incorrect UTF-8 bytes", utf8Bytes, test3.toUtf8Bytes());
     }
     @Test
     public void testToUtf8BytesOutsideOfBounds() throws Exception {
     EncodingHelperChar test3 = new EncodingHelperChar(0x11000000);
     byte[] utf8Bytes = [11000010 10100100];
     assertArrayEquals("Failure - incorrect UTF-8 bytes", utf8Bytes, test3.toUtf8Bytes());
     }
     */
    @Test
    /**
     * Tests outputting the code point in a string format for EncodingHelperChar.
     * @exception the correct code point is not output as a string.
     */

    public void testToCodePointString() throws Exception {
        EncodingHelperChar test4 = new EncodingHelperChar(0xA5);
        assertEquals("Failure - incorrect code point string", "U+00A5", test4.toCodePointString());
    }

    @Test
    /**
     * Tests printing a string of the UTF-8 encoding of the code point.
     * @exception the string does not match the expected encoding string.
     */
    public void testToUtf8String() throws Exception {
        EncodingHelperChar test5 = new EncodingHelperChar(0xA5);
        assertEquals("Failure - incorrect Utf8 string", "\\xC2\\xA5", test5.toUtf8String() );
    }
    @Test
    public void testToUtf8String3() throws Exception {
        EncodingHelperChar test5 = new EncodingHelperChar(0xA5);
        assertEquals("Failure - incorrect Utf8 string", "\\xC2\\xA5", test5.toUtf8String() );
    }

    @Test
    /**
     * Tests outputting the character name for a given code point in EncodingHelperChar.
     * @exception if the character name output is not the same as the given code point's
     * character name.
     */
    public void testGetCharacterName() throws Exception {
        EncodingHelperChar test6 = new EncodingHelperChar(0xA5);
        assertEquals("Failure - incorrect character name", "YEN SIGN", test6.getCharacterName());
    }

    @Test
    public void testGetCharacterNameForControlCharacters() throws Exception {
        EncodingHelperChar test6 = new EncodingHelperChar(0x1A);
        assertEquals("Failure - incorrect character name", "<control> SUBSTITUTE", test6.getCharacterName());
    }

    @Test
    public void testGetCharacterNameForHigherRangeControlCharacters() throws Exception {
        EncodingHelperChar test6 = new EncodingHelperChar(0x9F);
        assertEquals("Failure - incorrect character name", "<control> APPLICATION CONTROL COMMAND", test6.getCharacterName());
    }

    @Test
    public void testGetCharacterNameForUnknownCharacters() throws Exception {
        EncodingHelperChar test6 = new EncodingHelperChar(0x3FFF);
        assertEquals("Failure - incorrect character name", "<unknown> U+3FFF", test6.getCharacterName());
    }
}