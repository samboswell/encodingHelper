package edu.carleton.boswells;
import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;

/**
 * Created by stonemanm and boswells to help people who need encoding help.
 */
public class EncodingHelper {
    private String inputType = "string";
    private String outputType = "summary";
    private int startingPoint = 0;
    private EncodingHelperChar[] cpArray = null;

    public EncodingHelper() {}

    /**
     * Prints the help info.
     */
    public void useInformation() {
        String info = "java EncodingHelper [-i type] [-o type] <data>\n";
        info += "    [--help/-h] displays usage options\n";
        info += "   [--input/-i] types: string, utf8, codepoint\n";
        info += "                default: string\n";
        info += "  [--output/-o] types: summary, string, utf8, codepoint\n";
        info += "                default: summary";
        System.out.println(info);
    }

    /**
     * FOR STRING INPUT
     * Takes a given string and creates an EncodingHelperChar from each of the
     * chars in the string.
     *
     * @return An array of EncodingHelperChar objects.
     */
    public EncodingHelperChar[] readFromString(String s) {
        EncodingHelperChar[] codepoints = new EncodingHelperChar[s.length()];
        for (int i = 0; i < s.length(); i++) {
            codepoints[i] = new EncodingHelperChar(s.charAt(i));
        }
        return codepoints;
    }

    /**
     * FOR BYTE INPUT
     * Takes a string of bytes, sorts and splits them appropriately, and creates
     * an EncodingHelperChar from each of the byte arrays.
     *
     * @return An array of EncodingHelperChar objects.
     */
    public EncodingHelperChar[] readFromUTF8(String sByte) {
        List<EncodingHelperChar> temp = new LinkedList<>();

        //Splits the string.
        String[] sByteArray = null;
        if (sByte.startsWith("\\x")) {
            sByte = sByte.substring(2);
            sByteArray = sByte.split("\\\\x");
        } else if (sByte.startsWith("x")) {
            sByte = sByte.substring(1);
            sByteArray = sByte.split("x");
        } else {
            sByteArray = new String[sByte.length()/2];
            for (int i = 0; i < sByteArray.length; i++) {
                sByteArray[i] = sByte.substring(2*i, 2*i+2);
            }
        }

        //Makes bytes out of the strings.
        byte[] bArray = new byte[sByteArray.length];
        for (int i = 0; i < sByteArray.length; i++) { //gets rid of the empty first string.
            bArray[i] = (byte) ((Character.digit(sByteArray[i].charAt(0), 16) << 4) + Character.digit(sByteArray[i].charAt(1), 16));
        }

        //Populates the LinkedList with EncodingHelperChars of the bArray bytes.
        int j = 0;
        while (j < bArray.length) {
            byte[] tByte = null;
            if ((bArray[j] >>> 7) == 0) {
                try {
                    tByte = Arrays.copyOfRange(bArray, j, j + 1);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Bytes are not a UTF-8 representation.");
                    useInformation();
                    System.exit(1);
                }
                temp.add(new EncodingHelperChar(tByte));
                j += 1;
            } else if (((bArray[j] >>> 5) & 0x6) == 6 && ((bArray[j + 1] >>> 6) & 0x2) == 2) {
                try {
                    tByte = Arrays.copyOfRange(bArray, j, j + 2);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Bytes are not a UTF-8 representation.");
                    useInformation();
                    System.exit(1);
                }
                temp.add(new EncodingHelperChar(tByte));
                j += 2;
            } else if (((bArray[j] >>> 4) & 0xE) == 0xE && ((bArray[j + 1] >>> 6) & 0x2) == 2 && ((bArray[j + 2] >>> 6) & 0x2) == 2) {
                try {
                    tByte = Arrays.copyOfRange(bArray, j, j + 3);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Bytes are not a UTF-8 representation.");
                    useInformation();
                    System.exit(1);
                }
                temp.add(new EncodingHelperChar(tByte));
                j += 3;
            } else if (((bArray[j] >>> 3) & 0x1E) == 0x1E && ((bArray[j + 1] >>> 6) & 0x2) == 2 && ((bArray[j + 2] >>> 6) & 0x2) == 2 && ((bArray[j + 3] >>> 6) & 0x2) == 2) {
                try {
                    tByte = Arrays.copyOfRange(bArray, j, j + 4);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Bytes are not a UTF-8 representation.");
                    useInformation();
                    System.exit(1);
                }
                temp.add(new EncodingHelperChar(tByte));
                j += 4;
            } else {
                useInformation();
                throw new IllegalArgumentException("Byte are not a UTF-8 representation.");
            }
        }

        //Turns the list into an array and returns.
        EncodingHelperChar[] codepoints = new EncodingHelperChar[temp.size()];
        for (int i = 0; i < temp.size(); i++) {
            codepoints[i] = temp.get(i);
        }
        return codepoints;
    }

    /**
     * FOR CODEPOINT INPUT
     * Takes a string of bytes, sorts and splits them appropriately, and creates
     * an EncodingHelperChar from each of the byte arrays.
     *
     * @return An array of EncodingHelperChar objects.
     */
    public EncodingHelperChar[] readFromCodepoints(String[] codepoints) {
        String[] strArray = null;

        if (codepoints.length == 1) {
            strArray = codepoints[0].split(" ");
        } else {
            strArray = codepoints;
        }

        EncodingHelperChar[] cdpArray = new EncodingHelperChar[strArray.length];

        for (int i = 0; i < strArray.length; i++) {
            String temp = strArray[i].substring(strArray[i].length() - 4);
            cdpArray[i] = new EncodingHelperChar(Integer.parseInt(temp,16));
        }

        return cdpArray;
    }

    /**
     * FOR STRING OUTPUT
     * Takes an array of EncodingHelperChar objects and constructs a
     * corresponding string, then returns it.
     *
     * @return A string corresponding to the EncodingHelperChar[].
     */
    public String writeToString(EncodingHelperChar[] codepoints) {
        String output = "";
        for (EncodingHelperChar codepoint : codepoints) {
            output += Character.toString((char)codepoint.getCodePoint());
        }
        return output;
    }

    /**
     * FOR BYTE OUTPUT
     * Takes an array of EncodingHelperChar objects and constructs a
     * corresponding string of UTF-8 byte representations, then returns it.
     *
     * @return A string of bytes corresponding to the EncodingHelperChar[].
     */
    public String writeToUTF8(EncodingHelperChar[] codepoints) {
        String output = "";
        for (EncodingHelperChar codepoint : codepoints) {
            output += codepoint.toUtf8String();
        }
        return output;
    }

    /**
     * FOR CODEPOINT OUTPUT
     * Takes an array of EncodingHelperChar objects and constructs a
     * corresponding string of codepoints, then returns it.
     *
     * @return A string of codepoints corresponding to the EncodingHelperChar[].
     */
    public String writeToCodepoints(EncodingHelperChar[] codepoints) {
        String output = "";
        for (EncodingHelperChar codepoint : codepoints) {
            output += " " + codepoint.toCodePointString();
        }
        return output.substring(1); //cuts the first space
    }

    /**
     * Our main function. It needs to determine the type of input based on the
     * tags and call functions as a result.
     * The default input is a string. The default output is a summary.
     *
     * For convenience, methods have been named readFromX() or writeToX(), where
     * X is "String", "UTF8", or "Codepoints".
     * All writeToX() methods take an array of EncodingHelperChar objects.
     * readFromString() and readFromUTF8() take a string as an argument.
     * readFromCodepoints() takes a String[], and is unique. See its comments.
     *
     * I didn't write a method for Summary. It can be compiled from the others.
     */
    public static void main(String[] args) {
        EncodingHelper EH = new EncodingHelper();
        if (args.length == 0 || args[0].compareTo("-h") == 0 || args[0].compareTo("--help") == 0) {
            EH.useInformation();
            System.exit(1);
        }
        int i = 0;
        while (i < args.length) {
            if (args[i].compareTo("-i") == 0 || args[i].compareTo("--input") == 0) {
                i++;
                EH.inputType = args[i];
                i++;
                EH.startingPoint += 2;
            } else if (args[i].compareTo("-o") == 0 || args[i].compareTo("--output") == 0) {
                i++;
                EH.outputType = args[i];
                i++;
                EH.startingPoint += 2;
            } else {
                i++;
            }
        }
        String[] inArgs = null;
        try {
            inArgs = Arrays.copyOfRange(args, EH.startingPoint, args.length);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("There was an error with your input.");
            EH.useInformation();
            System.exit(1);
        }

        //Run an input function!
        if (EH.inputType.toLowerCase().compareTo("utf8") == 0) {
            EH.cpArray = EH.readFromUTF8(inArgs[0]);
        } else if (EH.inputType.toLowerCase().compareTo("utf-8") == 0) {
            EH.cpArray = EH.readFromUTF8(inArgs[0]);
        } else if (EH.inputType.toLowerCase().compareTo("codepoint") == 0) {
            EH.cpArray = EH.readFromCodepoints(inArgs);
        } else { //If the input is expressed differently, we assume it's string.
            EH.cpArray = EH.readFromString(inArgs[0]);
        }

        //Now we have populated cpString, so now we can write to cpArray.

        if (EH.outputType.toLowerCase().compareTo("string") == 0) {
            System.out.println(EH.writeToString(EH.cpArray));
        } else if (EH.outputType.toLowerCase().compareTo("utf8") == 0) {
            System.out.println(EH.writeToUTF8(EH.cpArray));
        } else if (EH.outputType.toLowerCase().compareTo("utf-8") == 0) {
            System.out.println(EH.writeToUTF8(EH.cpArray));
        } else if (EH.outputType.toLowerCase().compareTo("codepoint") == 0) {
            System.out.println(EH.writeToCodepoints(EH.cpArray));
        } else {
            String output = "";
            if (EH.cpArray.length == 1) {
                output += "Character: " + EH.writeToString(EH.cpArray) + "\n";
            } else {
                output += "String: " + EH.writeToString(EH.cpArray) + "\n";
            }
            if (EH.cpArray.length == 1) {
                output += "Code point: " + EH.writeToCodepoints(EH.cpArray) + "\n";
            } else {
                output += "Code points: " + EH.writeToCodepoints(EH.cpArray) + "\n";
            }
            if (EH.cpArray.length == 1) {
                output += "Name: " + EH.cpArray[0].getCharacterName() + "\n";
            }
            output += "UTF-8: " + EH.writeToUTF8(EH.cpArray);
            System.out.println(output);
        }
    }
}
