package edu.carleton.boswells;
import static org.junit.Assert.assertEquals;
import java.util.Arrays;
import org.junit.Test;

/**
 * Created by boswells on 4/5/15.
 */
public class EncodingHelperCharTest {

    @Test
    public void testGetCodePoint() throws Exception {
        EncodingHelperChar test1 = new EncodingHelperChar(0xA4);
        assertEquals(test1.getCodePoint(), 0xA4);
    }

    @Test
    public void testSetCodePoint() throws Exception {
        EncodingHelperChar test2 = new EncodingHelperChar(0xA4);
        test2.setCodePoint(0xA5);
        assertEquals(test2.getCodePoint(), 0xA5);
    }

    @Test
    public void testToUtf8Bytes() throws Exception {
        byte[] testBytes = new byte["C2","A5"];
    }

    @Test
    public void testToCodePointString() throws Exception {
        EncodingHelperChar test4 = new EncodingHelperChar(0xA4);
        assertEquals("Failure - incorrect code point string", test4.toCodePointString(), "U+00A4");
    }

    @Test
    public void testToUtf8String() throws Exception {
        EncodingHelperChar test5 = new EncodingHelperChar(0xA4);
        assertEquals("Failure - incorrect Utf8 string",test5.toUtf8String(), "/xC2/xA5" );
    }

    @Test
    public void testGetCharacterName() throws Exception {
        EncodingHelperChar test6 = new EncodingHelperChar(0xA4);
        assertEquals("Failure - incorrect character name", test6.getCharacterName(), "YEN SIGN");
    }
}