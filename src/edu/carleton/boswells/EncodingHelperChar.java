package edu.carleton.boswells;
import java.util.*;
import java.io.*;

/**
 * The main model class for the EncodingHelper project in
 * CS 257, Spring 2015, Carleton College. Each object of this class
 * represents a single Unicode character. The class's methods
 * generate various representations of the character.
 */
public class EncodingHelperChar {
    private int codePoint;
    private String hexString;

    public EncodingHelperChar(int codePoint) {
        if (0 < codePoint && codePoint < (0x10FFFF)) {
            this.codePoint = codePoint;
        }
        else {
            throw new IllegalArgumentException("Code point is out of bounds (either too high or too low)");
        }
    }

    public EncodingHelperChar(byte[] utf8Bytes) {
        if (utf8Bytes.length == 1) {
            this.codePoint = utf8Bytes[0];
        } else if (utf8Bytes.length == 2) {
            this.codePoint = ((utf8Bytes[0] & 0x1F) << 6) + (utf8Bytes[1] & 0x3F);
            //Bytes have format 110xxxxx 10xxxxxx
        } else if (utf8Bytes.length == 3) {
            this.codePoint = ((utf8Bytes[0] & 0xF) << 12) + ((utf8Bytes[1] & 0x3F) << 6) + (utf8Bytes[2] & 0x3F);
            //Bytes have format 1110xxxx 10xxxxxx 10xxxxxx
        } else if (utf8Bytes.length == 4) {
            this.codePoint = ((utf8Bytes[0] & 0x7) << 18) + ((utf8Bytes[1] & 0x3F) << 12) + ((utf8Bytes[2] & 0x3F) << 6) + (utf8Bytes[3] & 0x3F);
            //Bytes have format 11110xxx 10xxxxxx 10xxxxxx 10xxxxx
        } else {
            this.codePoint = 0x03C0; //I wanted an error case and I like pi.
        }
    }


    public EncodingHelperChar(char ch) {
        this.codePoint = Character.getNumericValue(ch);
        if (0 > codePoint || codePoint > (0x10FFFF)) {
            throw new IllegalArgumentException("Code point is out of bounds (either too high or too low)");
        }

        // I BELIEVE that you can literally just convert a char to an int to get
        //the code point. We'll see!
    }

    public int getCodePoint() {
        return this.codePoint;
    }

    public void setCodePoint(int codePoint) {
        this.codePoint = codePoint;
    }

    /**
     * Converts this character into an array of the bytes in its UTF-8
     * representation.
     *   For example, if this character is a lower-case letter e with an acute
     * accent, whose UTF-8 form consists of the two-byte sequence C3 A9, then
     * this method returns a byte[] of length 2 containing C3 and A9.
     *
     * @return the UTF-8 byte array for this character
     */
    public byte[] toUtf8Bytes() {
        byte[] arrayOfBytes = null;
        if (codePoint < 0x80) { //if codepoint < 128 { [0xxxxxxx] }
            arrayOfBytes = new byte[1];
            arrayOfBytes[0] = (byte) codePoint;
        } else if (codePoint < 0x800) { //[110xxxxx, 10xxxxxx]
            arrayOfBytes = new byte[2];
            int firstInt = ((codePoint & 0x7C0) >> 6) + 0xC0;
            arrayOfBytes[0] = (byte) firstInt;
            int secondInt = (codePoint & 0x3F) + 0x80;
            arrayOfBytes[1] = (byte) secondInt;
        } else if (codePoint < 0x10000) { //[1110xxxx, 10xxxxxx, 10xxxxxx]
            arrayOfBytes = new byte[3];
            int firstInt = ((codePoint & 0xF000) >> 12) + 0xE0;
            arrayOfBytes[0] = (byte) firstInt;
            int secondInt = ((codePoint & 0xFC0) >> 6) + 0x80;
            arrayOfBytes[1] = (byte) secondInt;
            int thirdInt = (codePoint & 0x3F) + 0x80;
            arrayOfBytes[2] = (byte) thirdInt;
        } else if (codePoint < 0x200000) { //[11110xxx, 10xxxxxx, 10xxxxxx, 10xxxxxx]
            arrayOfBytes = new byte[4];
            int firstInt = ((codePoint & 0x1C0000) >> 18) + 0xF0;
            arrayOfBytes[0] = (byte) firstInt;
            int secondInt = ((codePoint & 0x3F000) >> 12) + 0x80;
            arrayOfBytes[1] = (byte) secondInt;
            int thirdInt = ((codePoint & 0xFC0) >> 6) + 0x80;
            arrayOfBytes[2] = (byte) thirdInt;
            int fourthInt = (codePoint & 0x3F) + 0x80;
            arrayOfBytes[3] = (byte) fourthInt;
        }
        return arrayOfBytes;
    }

    /**
     * Generates the conventional 4-digit hexadecimal code point notation for
     * this character.
     *   For example, if this character is a lower-case letter e with an acute
     * accent, then this method returns the string U+00E9 (no quotation marks in
     * the returned String).
     *
     * @return the U+ string for this character
     */
    public String toCodePointString() {
        hexString = String.format("U+%04X", codePoint);
        // There's a method for Integer called toHexString(). It takes an
        // Integer object and prints the hex string for it. We can probably use
        // that, then just add U+ to the front.
        return hexString;
    }

    /**
     * Generates a hexadecimal representation of this character suitable for
     * pasting into a string literal in languages that support hexadecimal byte
     * escape sequences in string literals (e.g. C, C++, and Python).
     *   For example, if this character is a lower-case letter e with an acute
     * accent (U+00E9), then this method returns the string \xC3\xA9. Note that
     * quotation marks should not be included in the returned String.
     *
     * @return the escaped hexadecimal byte string
     */
    public String toUtf8String() {
        byte[] encodingArray = toUtf8Bytes();
        String encodingString = "";
        for (int i = 0; i < encodingArray.length; i++) {
            String encodedByte = String.format("\\x%02X", encodingArray[i]);
            encodingString = encodingString.concat(encodedByte);
        }
        // This one's iffier for me. We could use toUtf8Bytes, then somehow turn
        // the byte into an int, then use the toHexString() method, then parse
        // it to add the slashes and xes and such?
        return encodingString;
    }

    /**
     * Generates the official Unicode name for this character.
     *   For example, if this character is a lower-case letter e with an acute
     * accent, then this method returns the string "LATIN SMALL LETTER E WITH
     * ACUTE" (without quotation marks).
     *
     * @return this character's Unicode name
     */
    public String getCharacterName() {
        // We're going to need to load UnicodeData.txt as a file and use a
        // scanner to look through it.
        // I feel like we should just call toCodePointString(), then search for
        // that (though it doesn't have a U+ in the document so we'll have to
        // parse that out.) The document goes <codepoint>;<name>;(other crap),
        // so we want to split the string on semicolons and grab the second
        // thing. Then return it.
        hexString = String.format("%04X", codePoint);
        String charName = null;
        //String finalCharName = null;
        String[] charNameArray = null;

        try{
            Scanner unicodeData = new Scanner(new FileReader("src/edu/carleton/boswells/UnicodeData.txt"));
            unicodeData.useDelimiter(";");
            while (unicodeData.hasNextLine()) {
                charName = unicodeData.findInLine(hexString);
                if (charName != null) {
                    break;
                }
                unicodeData.nextLine();
            }
            if (charName == null) { return "Code point is unassigned. No character name generated";
            }
        }
        catch (FileNotFoundException e){
            System.err.println("File name not found");
        }

        return charName;
    }
}