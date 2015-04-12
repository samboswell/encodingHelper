package edu.carleton.boswells;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 * Created by Sam Boswell and Michael Stoneman, last edited on 4/5/15.
 */
public class EncodingHelperCharTest {

    @Test
    /**
     * Tests calling EncodingHelperChar with a char.
     * @exception it can't do anything with the char (which should be impossible?).
     */
    public void EncodingHelperCharChar() throws Exception {
        EncodingHelperChar T1 = new EncodingHelperChar('?');
        assertEquals("Wrong code point output.", 0x3F, T1.getCodePoint());
    }

    @Test
    /**
     * Tests calling EncodingHelperChar with an int.
     * @exception the codePoint given does not match the output of getCodePut.
     */
    public void EncodingHelperCharInt() throws Exception {
        EncodingHelperChar T1 = new EncodingHelperChar(0xA5);
        assertEquals(T1.getCodePoint(), 0xA5);
    }

    @Test
    /**
     * Tests calling EncodingHelperChar with an invalid high int.
     * @exception should throw an IllegalArgumentException.
     */
    public void EncodingHelperCharInvalidInt() throws Exception {
        try {
            EncodingHelperChar T2 = new EncodingHelperChar(0x10FFFF);
        } catch (IllegalArgumentException expectedException) {
        }
    }

    @Test
    /**
     * Tests calling EncodingHelperChar with a char.
     * @exception should throw an IllegalArgumentException.
     **/
    public void EncodingHelperCharNegativeInt() throws Exception {
        try {
            EncodingHelperChar T3 = new EncodingHelperChar(-0x80);
        } catch (IllegalArgumentException expectedException) {
        }
    }

    @Test
    /**
     * Tests calling EncodingHelperChar with an invalid byte string(one that is too high).
     * @exception should throw an IllegalArgumentException.
     */
    public void EncodingHelperCharInvalidByteTypeShouldReturnInvalid() throws Exception {

        try {
            EncodingHelperChar T4 = new EncodingHelperChar(0x10FFFF);
        } catch (IllegalArgumentException expectedException) {
        }
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
    /**
     * Tests converting longer code points to UTF-8 bytes for EncodingHelperChar.
     * @exception the code point does not convert correctly to UTF-8 bytes.
     */
    public void testToUtf8BytesLength3() throws Exception {
        EncodingHelperChar test3 = new EncodingHelperChar(0xFBA);
        byte[] utf8Bytes = new byte[]{(byte) 0xe0, (byte)0xbe, (byte)0xba};
        assertArrayEquals("Failure - incorrect UTF-8 bytes", utf8Bytes, test3.toUtf8Bytes());
    }

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
    /**
     * Tests outputting the character name for a given code point in the lower control range in EncodingHelperChar.
     * @exception if the character name output is not the same as the given code point's
     * character name.
     */
    public void testGetCharacterNameForControlCharacters() throws Exception {
        EncodingHelperChar test6 = new EncodingHelperChar(0x1A);
        assertEquals("Failure - incorrect character name", "<control> SUBSTITUTE", test6.getCharacterName());
    }

    @Test
    /**
     * Tests outputting the character name for a given code point in the higher control range in EncodingHelperChar.
     * @exception if the character name output is not the same as the given code point's
     * character name.
     */
    public void testGetCharacterNameForHigherRangeControlCharacters() throws Exception {
        EncodingHelperChar test6 = new EncodingHelperChar(0x9F);
        assertEquals("Failure - incorrect character name", "<control> APPLICATION PROGRAM COMMAND", test6.getCharacterName());
    }

    @Test
    /**
     * Tests outputting the character name for a given code point that is unknown in EncodingHelperChar.
     * @exception if the character name output is not the same as the given code point's
     * character name.
     */
    public void testGetCharacterNameForUnknownCharacters() throws Exception {
        EncodingHelperChar test6 = new EncodingHelperChar(0x3FFF);
        assertEquals("Failure - incorrect character name", "<unknown> U+3FFF", test6.getCharacterName());
    }
}