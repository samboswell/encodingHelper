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
            sByteArray = sByte.split("\\\\x"); //Oh my god, why.
        } else if (sByte.startsWith("x")) {
            sByteArray = sByte.split("x");
        } else {
            sByteArray = new String[sByte.length()/2];
            for (int i = 0; i < sByteArray.length; i++) {
                sByteArray[i] = sByte.substring(2*i, 2*i+2);
            }
        }


        //Makes bytes out of the strings.
        byte[] bArray = new byte[sByteArray.length - 1];
        for (int i = 1; i < sByteArray.length; i++) { //gets rid of the empty first string.
            bArray[i-1] = (byte) ((Character.digit(sByteArray[i].charAt(0), 16) << 4) + Character.digit(sByteArray[i].charAt(1), 16));
        }

        //Populates the LinkedList with EncodingHelperChars of the bArray bytes.
        int j = 0;
        while (j < bArray.length) {
            byte[] tByte = null;
            if ((bArray[j] >> 7) == 0) {
                try {
                    tByte = Arrays.copyOfRange(bArray, j, j + 1);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Bytes are not a UTF-8 representation.");
                    useInformation();
                    System.exit(1);
                }
                temp.add(new EncodingHelperChar(tByte));
            } else if (((bArray[j] >> 5) ^ 0x6) == 0 && ((bArray[j + 1] >> 6) ^ 0x2) == 0) {
                try {
                    tByte = Arrays.copyOfRange(bArray, j, j + 2);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Bytes are not a UTF-8 representation.");
                    useInformation();
                    System.exit(1);
                }
                temp.add(new EncodingHelperChar(tByte));
            } else if (((bArray[j] >> 4) ^ 0xE) == 0 && ((bArray[j + 1] >> 6) ^ 0x2) == 0 && ((bArray[j + 2] >> 6) ^ 0x2) == 0) {
                try {
                    tByte = Arrays.copyOfRange(bArray, j, j + 3);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Bytes are not a UTF-8 representation.");
                    useInformation();
                    System.exit(1);
                }
                temp.add(new EncodingHelperChar(tByte));
            } else if (((bArray[j] >> 3) ^ 0x1E) == 0 && ((bArray[j + 1] >> 6) ^ 0x2) == 0 && ((bArray[j + 2] >> 6) ^ 0x2) == 0 && ((bArray[j + 3] >> 6) ^ 0x2) == 0) {
                try {
                    tByte = Arrays.copyOfRange(bArray, j, j + 4);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Bytes are not a UTF-8 representation.");
                    useInformation();
                    System.exit(1);
                }
                temp.add(new EncodingHelperChar(tByte));
            } else {
                System.out.println("Bytes are not a UTF-8 representation.");
                useInformation();
                System.exit(1);
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
        EncodingHelperChar[] cdpArray = new EncodingHelperChar[codepoints.length];
        String[] strArray = null;
        if (codepoints.length == 1) {
            strArray = codepoints[0].split(" ");
        } else {
            strArray = codepoints;
        }

        for (int i = 0; i < strArray.length; i++) {
            String temp = strArray[i].substring(strArray[i].length() - 4);
            cdpArray[i] = new EncodingHelperChar(Integer.parseInt(temp,16));

//            if (strArray[i].startsWith("U") || strArray[i].startsWith("\\")) {
//                strArray[i] = strArray[i].substring(2);
//                cdpArray[i].setCodePoint(Integer.parseInt(strArray[i],16));
//            }
//            else if (strArray[i].startsWith("u")) {
//                strArray[i] = strArray[i].substring(1);
//                cdpArray[i].setCodePoint(Integer.parseInt(strArray[i],16));
//            }
//            else {
//                cdpArray[i].setCodePoint(Integer.parseInt(strArray[i],16));
//            }
        }

        // This might take a little thinking about.
        // There's two ways to input the codepoints – as separate args, or as a
        // string. We could have two separate constructors, but it seems easier
        // to write a method that parses them into the same form.
        //
        // The optimal form is an array of strings, where each string is of the
        // form U+####. Then
        //
        // for (string in String[])
        //     string = string.substring(2) <= deletes the first 2 chars (U+) DONE
        //     turn it into an int somehow DONE
        //     call the constructor WHICH ONE?
        //     add it to the output UH OK
        // So... (4/12 @ 6:40)
        // cdpArray is an EncodingHelperChar array of parsed ints from strArray
        // returns cdpArray which has its codepoint set to the parsed ints
        // I think that's correct? it looks too good to be true

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
            output += Character.getName(codepoint.getCodePoint());
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
            output += " " + codepoint.toUtf8String();
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
        } else if (EH.inputType.toLowerCase().compareTo("codepoint") == 0) {
            EH.cpArray = EH.readFromCodepoints(inArgs);
        } else { //If the input is expressed differently, we assume it's string.
            EH.cpArray = EH.readFromString(inArgs[0]);
        }

        //By now we have EH.cpArray! So output...

        if (EH.outputType.toLowerCase().compareTo("string") == 0) {
            System.out.println(EH.writeToString(EH.cpArray));
        } else if (EH.outputType.toLowerCase().compareTo("utf8") == 0) {
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
                output += "Code point: " + EH.writeToUTF8(EH.cpArray) + "\n";
            } else {
                output += "Code points: " + EH.writeToUTF8(EH.cpArray) + "\n";
            }
            if (EH.cpArray.length == 1) {
                output += "Name: " + EH.cpArray[0].getCharacterName() + "\n";
            }
            output += "UTF-8: " + EH.writeToCodepoints(EH.cpArray);
            System.out.println(output);
        }
    }
}
