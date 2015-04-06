package edu.carleton.boswells;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Created by Sam Boswell and Michael Stoneman, last edited on 4/5/15.
 */
public class EncodingHelperCharTest {

    @Test
    /**
     * Tests getting code points for EncodingHelperChar.
     * @exception if it gets the wrong code point.
     */
    public void testGetCodePoint() throws Exception {
        EncodingHelperChar test1 = new EncodingHelperChar(0xA4);
        assertEquals(test1.getCodePoint(), 0xA4);
    }

    @Test
    /**
     * Tests setting code points for EncodingHelperChar.
     * @exception if the code point is set to a new code point, but
     * returns the original code point.
     */
    public void testSetCodePoint() throws Exception {
        EncodingHelperChar test2 = new EncodingHelperChar(0xA4);
        test2.setCodePoint(0xA5);
        assertEquals(test2.getCodePoint(), 0xA5);
    }

    @Test
    /**
     * Tests converting the code point to UTF-8 bytes for EncodingHelperChar.
     * @exception if the code point does not convert correctly to UTF-8 bytes.
     */
    public void testToUtf8Bytes() throws Exception {
        EncodingHelperChar test3 = new EncodingHelperChar(0xA4);
        String codePoint = "C2 A4";
        byte[] utf8Bytes = codePoint.getBytes();
        assertEquals("Failure - incorrect UTF-8 bytes",test3.toUtf8Bytes(),utf8Bytes);
    }

    @Test
    /**
     * Tests outputting the code point in a string format for EncodingHelperChar.
     * @exception if the correct code point is not output as a string.
     */

    public void testToCodePointString() throws Exception {
        EncodingHelperChar test4 = new EncodingHelperChar(0xA4);
        assertEquals("Failure - incorrect code point string", test4.toCodePointString(), "U+00A4");
    }

    @Test
    /**
     * Tests printing a string of the UTF-8 encoding of the code point.
     * @exception if the string does not match the expected encoding string.
     */
    public void testToUtf8String() throws Exception {
        EncodingHelperChar test5 = new EncodingHelperChar(0xA4);
        assertEquals("Failure - incorrect Utf8 string",test5.toUtf8String(), "/xC2/xA5" );
    }

    @Test
    /**
     * Tests outputting the character name for a given code point in EncodingHelperChar.
     * @exception if the character name output is not the same as the given code point's
     * character name.
     */
    public void testGetCharacterName() throws Exception {
        EncodingHelperChar test6 = new EncodingHelperChar(0xA4);
        assertEquals("Failure - incorrect character name", test6.getCharacterName(), "YEN SIGN");
    }
}